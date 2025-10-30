package projetoLivraria.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        
    @FXML
    private HBox produtoSelecionadoArea;    
    @FXML
    private Text lblProdutoSelecionado;
    
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
    private TableColumn<LivroModel, Integer> prodQtdePag;
    @FXML
    private TableColumn<LivroModel, String> prodIdioma;
    @FXML
    private TableColumn<LivroModel, LocalDate> prodDtLancamento;
    @FXML
    private TableColumn<LivroModel, String> prodDisponibilidade;    
    
    //Botões do CRUD Produto
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
        prodQtdePag.setCellValueFactory(new PropertyValueFactory<>("qtdePag"));   
        prodIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));     
        prodDisponibilidade.setCellValueFactory(new PropertyValueFactory<>("disponibilidade")); 
        //
        
        //inicializando campos da lista de Vendas
        listaVenda.setItems(vendaDAO.listarTodos());
        //VINCULAR AS COLUNAS DA LISTAVENDA COM OS ATRIBUTOS DO VendaDAO
        
    }

    private void atualizarEstadoAba(int aba) {
        //Caso não tenha nenhum item selecionado, permita somenta a adição de um registro
        switch (aba) {
            case 1: //Aba de Produtos
                if (estadoAtual == EstadoLista.NAVEGANDO) {
                    //Habilita somente o botão de adicionar
                    btnAdicionarProd.setDisable(false);
                    
                    btnVisualizarProd.setDisable(true);
                    btnEditarProd.setDisable(true);
                    btnExcluirProd.setDisable(true);
                    
                    //Desabilite as opções de pesquisa, somente se a lista estiver vazia
                    if(listaProduto.getItems().isEmpty()){
                        edtPesquisaProduto.setDisable(true);
                        produtoSelecionadoArea.setVisible(true);
                    }
                } else { // ITEM_SELECIONADO;
                    //Caso haja um item selecionado, habilita todas as funções
                    btnAdicionarProd.setDisable(false);
                    btnVisualizarProd.setDisable(false);
                    btnEditarProd.setDisable(false);
                    btnExcluirProd.setDisable(false);

                    edtPesquisaProduto.setDisable(false);
                    lblProdutoSelecionado.setDisable(false);
                }                            
            break;
            case 2: //Aba de Vendas
                //Desabilita funções, caso estiver no modo navegação ou se a uma das duas listas estiver vazia
                if (estadoAtual == EstadoLista.NAVEGANDO || (listaVenda.getItems().isEmpty()|| listaProduto.getItems().isEmpty())) {
                                        
                    //Se não existir a lista de produto, desabilitar botão de adicionar
                    if (listaProduto.getItems().isEmpty()){
                        btnAdicionarVenda.setDisable(true);                                
                    } else {
                        btnAdicionarVenda.setDisable(false);                                
                    }
                    
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
                };
                
            break;
        }
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
        //listaProduto.getItems().add(0, "Olá"); // Retirar essa linha depois
        //listaVenda.getItems().add(0, "Olá"); // Retirar essa linha depois
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
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de exclusão");
        alertaExclusao.setHeaderText("Deseja realizar a exclusão do produto "+""+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
                    
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
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de cancelamento");
        alertaExclusao.setHeaderText("Deseja realizar o cancelamento da venda "+""+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
                    
        }
        
        return;
    }
        
}