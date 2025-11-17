package projetoLivraria.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projetoLivraria.DAO.LivroDAO;
import projetoLivraria.DAO.LivroInterfaceDAO;
import projetoLivraria.DAO.VendaDAO;
import projetoLivraria.DAO.VendaInterfaceDAO;
import projetoLivraria.MODEL.ItemVendaModel;
import projetoLivraria.MODEL.LivroModel;
import projetoLivraria.MODEL.VendaModel;

/**
 *
 * @author Laboratorio
 */
public class ListasController implements Initializable {

    @FXML
    private Tab tabProduto;
    @FXML
    private Tab tabVenda;
    @FXML
    private HBox endaSelecionadaArea;

    //Enum para gerenciar o estado da tela de lista
    private enum EstadoLista { NAVEGANDO, ITEM_SELECIONADO }
    private EstadoLista estadoAtual;

    //Botões do CRUD Produto
    @FXML
    private Button btnAdicionarProd;
    @FXML
    private Button btnVisualizarProd;
    @FXML
    private Button btnEditarProd;
    @FXML
    private Button btnExcluirProd;

    //Componentes de pesquisa
    @FXML
    private TextField edtPesquisaProduto;
    @FXML
    private TextField edtPesquisaVenda;
        
//    @FXML
//    private HBox produtoSelecionadoArea;    
//    @FXML
//    private Text lblProdutoSelecionado;
    
    private HBox vendaSelecionadaArea;    
    @FXML
    private Text lblVendaSelecionada;

    //Lista de Produtos
    @FXML 
    public TableView<LivroModel> listaProduto;

    //Colunas da Lista de Produtos
    @FXML
    private TableColumn<LivroModel, Integer> prodCodLivro;        
    @FXML
    private TableColumn<LivroModel, String> prodTitulo;    
    @FXML
    private TableColumn<LivroModel, Integer> prodISBN;
    @FXML
    private TableColumn<LivroModel, String> prodAutor;
    @FXML
    private TableColumn<LivroModel, String> prodPubAlvo;
    @FXML
    private TableColumn<LivroModel, Double> prodValor;
    @FXML
    private TableColumn<LivroModel, Integer> prodQtdeEstoque;
    @FXML
    private TableColumn<LivroModel, String> prodIdioma;
    @FXML
    private TableColumn<LivroModel, LocalDate> prodDtLancamento;
    @FXML
    private TableColumn<LivroModel, String> prodDisponibilidade;    
    
    //Botões do CRUD Venda
    @FXML
    private Button btnAdicionarVenda;
    @FXML
    private Button btnVisualizarVenda;
    @FXML
    private Button btnEditarVenda;
    @FXML
    private Button btnCancelarVenda;

    //Lista de Produtos
    @FXML 
    public TableView<VendaModel> listaVenda;
    
    //Colunas da Lista de vendas
    @FXML
    private TableColumn<VendaModel, Integer> vendaCodigo;
    @FXML
    private TableColumn<VendaModel, String> vendaNomeComprador;
    @FXML
    private TableColumn<VendaModel, Double> vendaTotal;
    @FXML
    private TableColumn<VendaModel, LocalDate> vendaEmissao;
    @FXML
    private TableColumn<VendaModel, Boolean> vendaStatus;
    
    public static LivroInterfaceDAO livroDAO = new LivroDAO(); // Instância da DAO de produtos          
    public static VendaInterfaceDAO vendaDAO = new VendaDAO(); // Instância da DAO de Venda                      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa a tela no estado NAVEGANDO
        estadoAtual = EstadoLista.NAVEGANDO;
        atualizarEstadoAba(1);
        atualizarEstadoAba(2);
        
        // Listener para seleção de item na tabela
        listaProduto.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                estadoAtual = EstadoLista.ITEM_SELECIONADO;
            } else {
                estadoAtual = EstadoLista.NAVEGANDO;
            }
            atualizarEstadoAba(1);
        });
        
        // Listener para seleção de item na tabela
        listaVenda.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                estadoAtual = EstadoLista.ITEM_SELECIONADO;
            } else {
                estadoAtual = EstadoLista.NAVEGANDO;
            }
            atualizarEstadoAba(2);
        });
        
        //listaProduto.setItems((ObservableList<LivroModel>) livroDAO.listarTodos());
        //Vincula a lista do DAO com a tabela do Visual(RIMOU)      
        listaProduto.setItems(livroDAO.listarTodos());
        
        //Inicializando campos da lista de Produtos            
        prodCodLivro.setCellValueFactory(new PropertyValueFactory<>("codLivro"));
        prodTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        prodISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));        
        prodAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        prodValor.setCellValueFactory(new PropertyValueFactory<>("valor"));               
        prodDtLancamento.setCellValueFactory(new PropertyValueFactory<>("dtLancamento")); 
        prodQtdeEstoque.setCellValueFactory(new PropertyValueFactory<>("qtdeEstoque"));   
        prodIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));     
        prodDisponibilidade.setCellValueFactory(new PropertyValueFactory<>("disponibilidade")); 
        //
        
        carregarLivros();
        
        //inicializando campos da lista de Vendas
        listaVenda.setItems(vendaDAO.listarTodos());
        //VINCULAR AS COLUNAS DA LISTAVENDA COM OS ATRIBUTOS DO VendaDAO
        vendaCodigo.setCellValueFactory(new PropertyValueFactory<>("codVenda"));
        vendaNomeComprador.setCellValueFactory(new PropertyValueFactory<>("nomeComprador"));
        vendaEmissao.setCellValueFactory(new PropertyValueFactory<>("emissao"));        
        vendaTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));        
        vendaStatus.setCellValueFactory(new PropertyValueFactory<>("cancelado"));                
    }

    private void atualizarEstadoAba(int aba) {
        //Caso não tenha nenhum item selecionado, permita somenta a adição de um registro
        switch (aba) {
            case 1: //Aba de Produtos
                LivroModel livro = listaProduto.getSelectionModel().getSelectedItem();
                if (estadoAtual == EstadoLista.NAVEGANDO) {
                    //Habilita somente o botão de adicionar
                    btnAdicionarProd.setDisable(false);
                    
                    btnVisualizarProd.setDisable(true);
                    btnEditarProd.setDisable(true);
                    btnExcluirProd.setDisable(true);
                    
                    //Desabilite as opções de pesquisa, somente se a lista estiver vazia
                    if(listaProduto.getItems().isEmpty()){
//                        edtPesquisaProduto.setDisable(true);
//                        produtoSelecionadoArea.setVisible(true);
                    }
                } else { // ITEM_SELECIONADO;
                    //Caso haja um item selecionado, habilita todas as funções
                    btnAdicionarProd.setDisable(false);
                    btnVisualizarProd.setDisable(false);
                    btnEditarProd.setDisable(false);
                    btnExcluirProd.setDisable(false);
                    
                    if (livro.getDisponibilidade() == false){
                        btnExcluirProd.setDisable(true);
                    }
                    //edtPesquisaProduto.setDisable(false);
                    //lblProdutoSelecionado.setDisable(false);
                }                            
            break;
            case 2: //Aba de Vendas
                VendaModel venda = listaVenda.getSelectionModel().getSelectedItem();
                //Desabilita funções, caso estiver no modo navegação ou se a uma das duas listas estiver vazia
                if (estadoAtual == EstadoLista.NAVEGANDO || (listaVenda.getItems().isEmpty()|| listaProduto.getItems().isEmpty())) {
                                                            
                    btnVisualizarVenda.setDisable(true);                                                    
                    btnEditarVenda.setDisable(true);
                    btnCancelarVenda.setDisable(true);
        
                    if (listaVenda.getItems() == null){
                        edtPesquisaVenda.setDisable(true);
                        vendaSelecionadaArea.setDisable(true);                
                    }

                } else { // ITEM_SELECIONADO;;
                    //Caso o usuário clique em um item na lista, permita que ele visualize, edite e exclua o registro    
                    //Aba Venda
                    btnAdicionarVenda.setDisable(false);
                    btnVisualizarVenda.setDisable(false);
                    btnEditarVenda.setDisable(false);
                    btnCancelarVenda.setDisable(false);
        
                    edtPesquisaVenda.setDisable(false);
                    lblVendaSelecionada.setDisable(false);
                    
                    if (venda.getCancelado() == true){
                        btnCancelarVenda.setDisable(true);
                    }
                    
                };
                
            break;
        }
    }

    /**
     * Carrega dados de exemplo no DAO.
     */
    private void carregarLivros(){
        // Registros originais
        LivroModel novoLivro = new LivroModel(
            "A Arte da Guerra",             // String titulo
            978857684,                      // int isbn
            "Sun Tzu",                      // String autor
            "Acadêmico",                    // String genero
            LocalDate.of(2007, 5, 15),      // LocalDate dtLancamento
            "Português",                    // String idioma
            160,                            // int qtdePag
            29.90,                          // double valor
            100,                            // int qtdeEstoque
            true                            // boolean disponibilidade
        );
        
        LivroModel novoLivro2 = new LivroModel(
            "VOEI",                         // String titulo
            123,                            // int isbn
            "Sun Tzu",                      // String autor
            "Acadêmico",                    // String genero
            LocalDate.of(2007, 5, 15),      // LocalDate dtLancamento
            "Português",                    // String idioma
            160,                            // int qtdePag
            29.90,                          // double valor
            80,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );
        
        LivroModel novoLivro3 = new LivroModel(
            "Duna",                         // String titulo
            1001,                           // int isbn
            "Frank Herbert",                // String autor
            "Ficção Científica",            // String genero
            LocalDate.of(1965, 8, 1),       // LocalDate dtLancamento
            "Português",                    // String idioma
            688,                            // int qtdePag
            89.90,                          // double valor
            50,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro4 = new LivroModel(
            "O Senhor dos Anéis: A Sociedade do Anel", // String titulo
            1002,                           // int isbn
            "J.R.R. Tolkien",               // String autor
            "Fantasia",                     // String genero
            LocalDate.of(1954, 7, 29),      // LocalDate dtLancamento
            "Português",                    // String idioma
            576,                            // int qtdePag
            79.90,                          // double valor
            75,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro5 = new LivroModel(
            "1984",                         // String titulo
            1003,                           // int isbn
            "George Orwell",                // String autor
            "Distopia",                     // String genero
            LocalDate.of(1949, 6, 8),       // LocalDate dtLancamento
            "Português",                    // String idioma
            416,                            // int qtdePag
            49.90,                          // double valor
            120,                            // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro6 = new LivroModel(
            "Código Limpo",                 // String titulo
            1004,                           // int isbn
            "Robert C. Martin",             // String autor
            "Tecnologia",                   // String genero
            LocalDate.of(2008, 8, 1),       // LocalDate dtLancamento
            "Português",                    // String idioma
            464,                            // int qtdePag
            115.50,                         // double valor
            30,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro7 = new LivroModel(
            "Dom Casmurro",                 // String titulo
            1005,                           // int isbn
            "Machado de Assis",             // String autor
            "Clássico",                     // String genero
            LocalDate.of(1899, 3, 15),      // LocalDate dtLancamento
            "Português",                    // String idioma
            256,                            // int qtdePag
            25.00,                          // double valor
            200,                            // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro8 = new LivroModel(
            "Sapiens: Uma Breve História da Humanidade", // String titulo
            1006,                           // int isbn
            "Yuval Noah Harari",            // String autor
            "Não-Ficção",                   // String genero
            LocalDate.of(2011, 7, 10),      // LocalDate dtLancamento
            "Português",                    // String idioma
            472,                            // int qtdePag
            69.90,                          // double valor
            85,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro9 = new LivroModel(
            "Harry Potter e a Pedra Filosofal", // String titulo
            1007,                           // int isbn
            "J.K. Rowling",                 // String autor
            "Fantasia",                     // String genero
            LocalDate.of(1997, 6, 26),      // LocalDate dtLancamento
            "Português",                    // String idioma
            264,                            // int qtdePag
            54.90,                          // double valor
            150,                            // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro10 = new LivroModel(
            "O Poder do Hábito",            // String titulo
            1008,                           // int isbn
            "Charles Duhigg",               // String autor
            "Autoajuda",                    // String genero
            LocalDate.of(2012, 2, 28),      // LocalDate dtLancamento
            "Português",                    // String idioma
            400,                            // int qtdePag
            47.90,                          // double valor
            90,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro11 = new LivroModel(
            "A Garota no Trem",             // String titulo
            1009,                           // int isbn
            "Paula Hawkins",                // String autor
            "Suspense",                     // String genero
            LocalDate.of(2015, 1, 13),      // LocalDate dtLancamento
            "Português",                    // String idioma
            378,                            // int qtdePag
            42.00,                          // double valor
            45,                             // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        LivroModel novoLivro12 = new LivroModel(
            "O Alquimista",                 // String titulo
            1010,                           // int isbn
            "Paulo Coelho",                 // String autor
            "Ficção",                       // String genero
            LocalDate.of(1988, 4, 20),      // LocalDate dtLancamento
            "Português",                    // String idioma
            208,                            // int qtdePag
            34.90,                          // double valor
            110,                            // int qtdeEstoque
            true                            // boolean disponibilidade
        );

        // Adicionando todos os livros ao DAO
        livroDAO.adicionar(novoLivro);
        livroDAO.adicionar(novoLivro2);
        livroDAO.adicionar(novoLivro3);
        livroDAO.adicionar(novoLivro4);
        livroDAO.adicionar(novoLivro5);
        livroDAO.adicionar(novoLivro6);
        livroDAO.adicionar(novoLivro7);
        livroDAO.adicionar(novoLivro8);
        livroDAO.adicionar(novoLivro9);
        livroDAO.adicionar(novoLivro10);
        livroDAO.adicionar(novoLivro11);
        livroDAO.adicionar(novoLivro12);
        
    } 
    //ABRIR FORMULÁRIO DE CADASTRO DE PRODUTO
    //Cria o formulário de cad. de produto
    //Manipula componentes com base no tipo de operacao:
    // 1 - ADICAO: 
    //      - Deixar invisível o codigo interno do Produto, pois ainda não há item;
    // 2 - EDICAO: Habilitar tudo;
    // 3 - CONSULTAR: Desabilitar todos campos e funções de manipulação do registro;    
    //Por fim, apresenta formulário, em uma nova aba
    
    private void abrirCadProd(int TipoOperacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/CadProdutoView.fxml"));
            //Cria instância do Controller
            Parent root = loader.load();
                        
            //Cria variável para manipular controller
            CadProdutoController controller = loader.getController();
            
            if(TipoOperacao == 2 || TipoOperacao == 3) {
                LivroModel livro = listaProduto.getSelectionModel().getSelectedItem();
                controller.livro = livro;
            }
            
            //Define o tipo de operação do formulário
            controller.configurarTela(TipoOperacao);                     
                      
            // Cria um NOVO Stage (uma nova janela)
            Stage stage = new Stage();
            stage.setTitle("Formulário de Cadastro de Produto");
            stage.setScene(new Scene(root));

            // (Opcional) Bloqueia a interação com a janela de listas até que esta seja fechada
            // stage.initModality(Modality.APPLICATION_MODAL); 
            //mostra o formulário
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //ADICIONAR ITEM
    //Habilita campos do form de cad. Produto
    //Cria o formulário de cad. de Produto
    @FXML     
    private void adicionarProd(ActionEvent event){        
        abrirCadProd(1);
    }

    //EDITAR ITEM
    //Habilita campos do form de cad. Produto
    //Cria o formulário de cad. de Produto
    @FXML
    private void editarProd(ActionEvent event){
        abrirCadProd(2);
    }
        
    //VISUALIZAR ITEM
    //Desabilita os campos do form de cad. Produto
    //deixa invisível o botão de gravar produto
    //Cria o formulário de cad. de Produto    
    @FXML
    private void visualizarProd(ActionEvent event){        
        abrirCadProd(3);
    }
    
    //EXCLUIR ITEM
    //Mantido vazio, pois a exclusão não faz parte da tarefa
    @FXML
    private void excluirProd(ActionEvent event){
        boolean produtoItem = false;
        LivroModel livro = listaProduto.getSelectionModel().getSelectedItem();        
        
        //Valida se o produto existe em alguma venda
        //caso existir, não permitir a exclusão, mas sim a alteração do seu status de disponibilidade para false ("Indisponível")
        for (VendaModel venda : vendaDAO.listarTodos()){
            for(ItemVendaModel item : venda.getItens()){
                if (livro.getCodLivro() == item.getCodLivro()){
                    produtoItem = true;
                    break;
                }
            }            
        }                
        
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de exclusão");
        
        if (!produtoItem) {
            alertaExclusao.setHeaderText("Deseja realizar a exclusão do produto abaixo: ?");            
        } else {
            alertaExclusao.setHeaderText("O produto abaixo não pode ser exclído, pois está associado a um item de uma venda. Deseja torná-lo indisponível?");            
        }
        
        alertaExclusao.setContentText(livro.getTitulo());            
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        
        //EXCLUSÃO
        if (!produtoItem && (botaoClicado.get() == ButtonType.OK)) {
            livroDAO.deletar(livro.getCodLivro());
        } 
        //ALTERAÇÃO
        else if (produtoItem && (botaoClicado.get() == ButtonType.OK)) {
            livro.setDisponibilidade(false);
            livroDAO.atualizar(livro);
        }
        
        return;
    }
    
    //ABRIR FORMULÁRIO DE CADASTRO DE VENDA
    //Cria o formulário de cad. de venda
    //Manipula componentes com base no tipo de operacao:
    // 1 - ADICAO: 
    //      - Deixar invisível o codigo interno do Produto, pois ainda não há item;
    // 2 - EDICAO: Habilitar tudo;
    // 3 - CONSULTAR: Desabilitar todos campos e funções de manipulação do registro;
    //Por fim, apresenta formulário, em uma nova aba;
    private void abrirCadVenda(int TipoOperacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/CadVendaView.fxml"));
            //Cria instância do Controller
            Parent root = loader.load();
                        
            //Cria variável para manipular controller
            CadVendaController controller = loader.getController();
            
            if(TipoOperacao == 2 || TipoOperacao == 3) {
                VendaModel venda = listaVenda.getSelectionModel().getSelectedItem();
                controller.venda = venda;
            }

            //Define o tipo de operação do formulário
            controller.configurarTela(TipoOperacao);                     
            
            // Cria um NOVO Stage (uma nova janela)
            Stage stage = new Stage();
            stage.setTitle("Formulário de Cadastro de Venda");
            stage.setScene(new Scene(root));

            // (Opcional) Bloqueia a interação com a janela de listas até que esta seja fechada
            // stage.initModality(Modality.APPLICATION_MODAL); 
            //mostra o formulário
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //ADICIONAR ITEM
    //Habilita campos do form de cad. Produto
    //Cria o formulário de cad. de Produto
    @FXML     
    private void adicionarVenda(ActionEvent event){        
        abrirCadVenda(1);
    }

    //EDITAR ITEM
    //Habilita campos do form de cad. Produto
    //Cria o formulário de cad. de Produto
    @FXML
    private void editarVenda(ActionEvent event){
        abrirCadVenda(2);
    }
        
    //VISUALIZAR ITEM
    //Desabilita os campos do form de cad. Produto
    //deixa invisível o botão de gravar produto
    //Cria o formulário de cad. de Produto    
    @FXML
    private void visualizarVenda(ActionEvent event){        
        abrirCadVenda(3);
    }
    
    //CANCELAR VENDA
    //Cancela a venda, deixando-a com status de cancelada
    @FXML
    private void cancelarVenda(ActionEvent event){
        VendaModel venda = listaVenda.getSelectionModel().getSelectedItem();        
                
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de cancelamento");
        alertaExclusao.setHeaderText("Deseja realizar o cancelamento da venda do cliente abaixo? Vendas canceladas tem os itens estornados ao estoque e não podem ser válidas novamente.");
        alertaExclusao.setContentText(venda.getNomeComprador());
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
            
            //devolvendo o estoque
            for(ItemVendaModel item : venda.getItens()){
                LivroModel livro = livroDAO.buscarPorCod(item.getCodLivro());
                int estoque = livro.getQtdeEstoque() + item.getQtde();
                livro.setQtdeEstoque(estoque);
            }
            
            vendaDAO.cancelar(venda.getCodVenda());
        }
        
        return;
    }
        
}