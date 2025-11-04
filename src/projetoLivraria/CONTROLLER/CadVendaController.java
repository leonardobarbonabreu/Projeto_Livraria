package projetoLivraria.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import projetoLivraria.MODEL.ItemVendaModel;
import projetoLivraria.MODEL.LivroModel;
import projetoLivraria.MODEL.VendaModel;
import projetoLivraria.CONTROLLER.ListasController;

public class CadVendaController implements Initializable{
    
    //Botões
    @FXML
    private Button btnGravarItem;
    @FXML
    private Button btnExcluirItem;
    @FXML
    private Button btnGravarVenda;
    @FXML
    private Button btnRetornar;
    @FXML
    private Button btnVisualizarItem;

    //Campos Venda
    @FXML
    private TextField edtNomeComprador;
    @FXML
    private TextField edtDescontoVenda;
    @FXML
    private TextField edtSubtotal;
    @FXML
    private TextField edtTotal;
    @FXML
    private ComboBox cmbFormaPgto;

    //Campos item
    @FXML
    private HBox camposItemArea;
    @FXML
    private TextField edtCodigoItem;
    @FXML
    private TextField edtQtdeItem;    
    @FXML
    private TextField edtValorUnitario;    
    @FXML
    private TextField edtValorTotalItem;    
    @FXML
    private TextField edtPesquisaItem;            
    
    //Campos de visualização do item
    @FXML
    private TextField edtVisuISBN;
    @FXML
    private TextField edtVisuTitulo;
    
    //Colunas das tabelas
    @FXML
    private TableView<ItemVendaModel> listaItemVenda;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemCodProduto;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemISBN;
    @FXML
    private TableColumn<ItemVendaModel, Double> valorUnitario;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemCodigo;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemQtde;
    @FXML
    private TableColumn<ItemVendaModel, Double> itemSubtotal;
    @FXML
    private TableColumn<ItemVendaModel, String> itemTitulo;
    @FXML
    private TableColumn<ItemVendaModel, Double> itemValorUn;
    
    @FXML
    private HBox itemSelecionadoArea;
    
    //labels e textos
    @FXML
    private Text lblCodVenda;

    @FXML
    private Text lblDtEmissao;

    @FXML
    private Text lblItemSelecionado;

    @FXML
    private Text lblMensagemValidacao;

    @FXML
    private Text txtCodVenda;

    @FXML
    private Text txtDtEmissao;

    @FXML
    private Text txtTipoOperacao;

    public int TIPO_OPERACAO;

    private int codVenda;    
    public VendaModel venda; //variavel local de venda usada para receber venda p/ alteração/consulta
    public ObservableList<ItemVendaModel> listaItens; // Lista de itens para venda     
    public ItemVendaModel itemSelecionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // Listener para seleção de item na tabela
        listaItemVenda.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {                
                itemSelecionado = newItem;                
            }
            else {
                itemSelecionado = null; // Limpa a variável
                limparCamposItem(); // (Recomendado) Limpa os campos de texto do item
            }            
            // deve ser atualizado se o item for nulo OU não-nulo.
            habilitarBotoesItem();
        });
        
        //lista de Itens;
        listaItens = FXCollections.observableArrayList();         

        listaItemVenda.setItems(listaItens);
                
        itemCodProduto.setCellValueFactory(new PropertyValueFactory<>("codLivro"));
        itemQtde.setCellValueFactory(new PropertyValueFactory<>("qtde"));
        itemValorUn.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));        
        itemSubtotal.setCellValueFactory(new PropertyValueFactory<>("valor"));        
        
        itemTitulo.setCellValueFactory(cellData -> {
            // cellData.getValue() retorna o ItemVendaModel da linha específica
            int codLivro = cellData.getValue().getCodLivro();
            
            // Usamos o DAO estático do ListasController para buscar o livro
            LivroModel livro = ListasController.livroDAO.buscarPorCod(codLivro);
            
            if (livro != null) {
                // Retorna a propriedade Título do Livro encontrado
                return new SimpleStringProperty(livro.getTitulo());
            } else {
                return new SimpleStringProperty("Livro não encontrado");
            }            
        });

        itemISBN.setCellValueFactory(cellData -> {
            // cellData.getValue() retorna o ItemVendaModel da linha específica
            int codLivro = cellData.getValue().getCodLivro();
            
            // Usamos o DAO estático do ListasController para buscar o livro
            LivroModel livro = ListasController.livroDAO.buscarPorCod(codLivro);
            
            if (livro != null) {
                // Retorna a propriedade Título do Livro encontrado
                return new SimpleIntegerProperty(livro.getIsbn()).asObject();
            } else {
                return new SimpleIntegerProperty(0).asObject();
            }            
        });

        itemSubtotal.setCellValueFactory(cellData -> {
            // cellData.getValue() retorna o ItemVendaModel da linha específica
            int qtde = cellData.getValue().getQtde();
            double  valor = cellData.getValue().getValorVenda();
            double total = qtde * valor;
            
            return new SimpleDoubleProperty(total).asObject();
        });
                
    }  
    
    //limpa os campos do item
    private void limparCamposItem() {
        edtCodigoItem.clear(); 
        edtQtdeItem.clear();
        edtValorUnitario.clear(); 
        edtValorTotalItem.clear();
        edtVisuISBN.clear();
        edtVisuTitulo.clear();
    }
    
    //Configura a tela, de acordo com o tipo de operação
    public void configurarTela(int TipoOperacao){
        //Grava para que possamos utilizar ela em outros trechos
        TIPO_OPERACAO = TipoOperacao;        
        
        habilitarBotoesItem();
        
        //Define valores para os campos
        inicializarCampos();
        
        switch (TipoOperacao) {            
        case 1://ADIÇÃO
            txtTipoOperacao.setText("Cadastro de Venda");
            //escondendo componentes
            lblCodVenda.setVisible(false);
            txtCodVenda.setVisible(false);            
            
            lblDtEmissao.setVisible(false);
            txtDtEmissao.setVisible(false);

            //Define o código da venda a ser feita para salvar nos itens
            codVenda = VendaModel.getUltimoCodigoVenda()+ 1;
            break;
        case 2://2-EDIÇÃO
            txtTipoOperacao.setText("Alteração de Venda");                        
            //Define o código para os itens
            codVenda = venda.getCodVenda();
            carregarVenda(venda); 
            break;                
        case 3://3-CONSULTA
            txtTipoOperacao.setText("Consulta de Venda");            
            desabilitaCampos();            
            carregarVenda(venda);
            break;
        default:
            throw new AssertionError();
        }
    }
    
    //preenche os dados padrões dos campos
    private void inicializarCampos(){
        //popula os campos combo Forma de Pagamento
        cmbFormaPgto.getItems().add("PIX");
        cmbFormaPgto.getItems().add("Crédito");
        cmbFormaPgto.getItems().add("Débito");
        cmbFormaPgto.getItems().add("Dinheiro");
        cmbFormaPgto.getItems().add("Boleto");
                
    }
    
    private boolean validarCamposVenda(){
        //CASO A LISTA ESTEJA VAZIA
        if (listaItens.isEmpty()) {
            lblMensagemValidacao.setText("A venda deve conter ao menos um item.");
            lblMensagemValidacao.setVisible(true);
            return false;
        }
        
        //NOS ITENS
        for (ItemVendaModel item : listaItens) {
            // Busca o livro correspondente no DAO principal
            LivroModel livro = ListasController.livroDAO.buscarPorCod(item.getCodLivro());
            //CASO O ITEM NÃO EXISTA
            if (livro == null) {
                lblMensagemValidacao.setText("Erro: Produto " + item.getCodLivro() + " não encontrado.");
                lblMensagemValidacao.setVisible(true);
                return false;
            }
            
            //CASO A QTDE DE COMPRA FOR MENOR QUE A DE ESTOQUE
            if (livro.getQtdeEstoque() < item.getQtde()) {
                // (Opcional: você pode buscar o título do livro para uma msg melhor)
                lblMensagemValidacao.setText("Estoque insuficiente para o item " + livro.getTitulo());
                lblMensagemValidacao.setVisible(true);
                return false;
            }
        }    
        
        //DEMAIS CAMPOS
        //A FAZER
        return true;
    }
        
    //Valida os campos do item, antes de inserí-lo a lista
    private boolean validarCamposItem(){
        //Validações dos campos p/ adição do item
        //A FAZER
        return true;
    }
        
    @FXML  
    //RENOMEAR PARA GRAVAR ITEM
    //FARA TANTO A ADIÇÃO, COMO ALTERAÇÃO
    private void gravarItem(ActionEvent event){
        if (validarCamposItem() == false){
            return;
        }

        ItemVendaModel item;                   
        int codLivro = Integer.valueOf(edtCodigoItem.getText());
                
        Boolean itemExiste = false;
        for(ItemVendaModel item1 : listaItens){
            if(item1.getCodLivro() == codLivro){
                itemExiste = true;                
            }
        }
                        
        if ((itemSelecionado == null)&&(!itemExiste)) { // Novo item
            // Criar novo item
            item = new ItemVendaModel(
                    codLivro, codVenda, Double.valueOf(edtValorUnitario.getText()),
                    Integer.valueOf(edtQtdeItem.getText()));            
                                    
            listaItens.add(item);
            
        } else { // Edição de Item
            item = itemSelecionado;
            item.setCodVenda(codVenda);
            item.setCodLivro(codLivro);
            item.setValorVenda(Double.valueOf(edtValorUnitario.getText()));
            item.setQtde(Integer.valueOf(edtQtdeItem.getText()));
                        
            listaItens.set(listaItens.indexOf(item), item);
        }
        atualizaValoresTotais();
        limparCamposItem();
    }    
    
    @FXML
    //Exclui o item da lista
    private void excluirItem(ActionEvent event){
        //Caso o item não esteja selecionado, por segurança, retornar
        if (itemSelecionado == null) {
            return;
        }
                
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de exclusão");
        alertaExclusao.setHeaderText("Deseja realizar a exclusão do item "+itemSelecionado.getCodLivro()+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
           //remove o item da lista                      
           listaItens.remove(itemSelecionado);
           
           listaItemVenda.getSelectionModel().clearSelection();
        }
        
        return;
    }
    
    @FXML
    private void visualizarItem(ActionEvent event){
        //Caso o item não esteja selecionado, por segurança, retornar
        if (itemSelecionado == null) {
            return;
        }
        consultarItemProd();        
    }
    
    @FXML
    //REALIZA A GRAVAÇÃO DA VENDA
    private void gravarVenda(ActionEvent event){
        
        //VALICAÇÕES
        if (validarCamposVenda() == false){
            return;
        }

        //DAR BAIXA NO ESTOQUE (PERCORRER A LISTA DE NOVO)
        for (ItemVendaModel item : listaItens) {
            LivroModel livro = ListasController.livroDAO.buscarPorCod(item.getCodLivro());
            
            // Calcula novo estoque
            int novoEstoque = livro.getQtdeEstoque() - item.getQtde();
            
            if (novoEstoque == 0) {
                livro.setDisponibilidade(false);
            }
            
            livro.setQtdeEstoque(novoEstoque);
            
            // Atualiza o livro no DAO
            ListasController.livroDAO.atualizar(livro);
        }
        
        //ATUALIZA OS VALORES
        atualizaValoresTotais();
        
        //DEFINE O TIPO DE GRAVAÇÃO
        if (TIPO_OPERACAO == 1) { // Nova Venda
            // Criar a nova venda
            venda = new VendaModel(
                edtNomeComprador.getText(),
                java.time.LocalDate.now(),    // Pega a data atual
                String.valueOf(cmbFormaPgto.getValue()),
                listaItens,    // Passa a lista de itens que já foi validada
                Double.parseDouble(edtSubtotal.getText()),
                Double.parseDouble(edtSubtotal.getText()),                    
                Double.parseDouble(edtDescontoVenda.getText())                                        
            );
            
            venda.setItens(listaItens); // Atribui a lista de itens ao modelo
            
            ListasController.vendaDAO.adicionar(venda);
            
        } else if (TIPO_OPERACAO == 2) { // Edição de Venda
            //NÃO É PRECISO CRIAR VENDA, POIS FOI PASSADA COMO PARÂMETRO
            
            venda.setNomeComprador(edtNomeComprador.getText());
            //venda.setMetodoPagamento((String) cmbFormaPgto.getValue());
            venda.setMetodoPagamento((String) cmbFormaPgto.getValue());
            venda.setItens(listaItens);

            venda.setValorSubtotal(Double.parseDouble(edtSubtotal.getText()));
            venda.setValorTotal(Double.parseDouble(edtTotal.getText()));            
            venda.setValorDesconto(Double.parseDouble(edtDescontoVenda.getText()));
                        
            ListasController.vendaDAO.atualizar(venda);
        }

        // 5. FECHAR JANELA
        fecharJanela(event);
    }        
    
    //FUNÇÃO RESPONSÁVEL POR CALCULAR OS VALORES E EXIBIR NOS CAMPOS DE SUBTOTAL E TOTAL
    @FXML
    private void atualizaValoresTotais(){
       double total = 0;
       double subtotal = 0;
       double desconto;
       
       try{
         desconto = Double.valueOf(edtDescontoVenda.getText());
       } catch (NumberFormatException e) {
          desconto = 0; 
          edtDescontoVenda.setText(String.valueOf(desconto));  
       }
                            
       for (ItemVendaModel item : listaItens) {
           subtotal += (item.getValorVenda() * item.getQtde());
       } 
       
       if (desconto > subtotal) {
           lblMensagemValidacao.setText("Valor de desconto superior ao valor de venda.");
           return;
       }
       
       total = subtotal - desconto;
       
       //Preenchendo campos visuais 
       edtSubtotal.setText(String.valueOf(subtotal));
       edtTotal.setText(String.valueOf(total));       
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }   
    
    //Desabilita campos para a consulta
    private void desabilitaCampos(){                
        //DADOS VENDA
//        edtNomeComprador.setDisable(true);
//        edtSubtotal.setDisable(true);
//        edtTotal.setDisable(true);        
//        edtDescontoVenda.setDisable(true);
//        cmbFormaPgto.setDisable(true);

        edtNomeComprador.setEditable(false);
        edtSubtotal.setEditable(false);
        edtTotal.setEditable(false);
        edtDescontoVenda.setEditable(false);
        cmbFormaPgto.setDisable(true);
        
        //DADOS ITEM                   
//        edtCodigoItem.setDisable(true);
//        edtQtdeItem.setDisable(true);
//        edtValorUnitario.setDisable(true);
//        edtValorTotalItem.setDisable(true);
        
        camposItemArea.setVisible(false);

//        edtCodigoItem.setVisible(false);
//        edtQtdeItem.setVisible(false);
//        edtValorUnitario.setVisible(false);
//        edtValorTotalItem.setVisible(false);
        
        //edtPesquisaItem.setDisable(true);
        
        //botões
        btnGravarItem.setVisible(false);              
        btnExcluirItem.setVisible(false);              
        btnVisualizarItem.setVisible(false);
        btnGravarVenda.setVisible(false);                      
    }   
    
    //Carrega dados da venda
    public void carregarVenda(VendaModel venda){
        edtNomeComprador.setText(venda.getNomeComprador());        
        edtTotal.setText(String.valueOf(venda.getValorTotal()));
        edtSubtotal.setText(String.valueOf(venda.getValorSubtotal()));        
        edtDescontoVenda.setText(String.valueOf(venda.getValorDesconto()));  
        cmbFormaPgto.getSelectionModel().select(venda.getMetodoPagamento());
        
        //O conteúdo instanciado da lista Itens 
        listaItens = venda.getItens();
        listaItemVenda.setItems(listaItens);
    }    
                
    @FXML
    //Carrega os dados padrões do livro nos campos do item
    private void carregarDadosProduto(ActionEvent event){
        try {
            // 1. Limpa mensagens de erro anteriores (se houver)
            // lblMensagemValidacao.setVisible(false);

            // 2. Pega o código do livro digitado
            int codProduto = Integer.valueOf(edtCodigoItem.getText());
            
            // 3. Busca o livro no DAO
            LivroModel livro = ListasController.livroDAO.buscarPorCod(codProduto);
            
            // 4. Se o livro for encontrado, preenche os campos
            if (livro != null){
                edtVisuTitulo.setText(livro.getTitulo());
                edtVisuISBN.setText(String.valueOf(livro.getIsbn()));
                edtQtdeItem.setText("1");
                edtValorUnitario.setText(String.valueOf(livro.getValor()));
                
                double total = 1 * Double.parseDouble(edtValorUnitario.getText());
                
                edtValorTotalItem.setText(String.valueOf(total));

                // Opcional: Mover o foco para o campo de quantidade
                edtQtdeItem.requestFocus();
                
            } else {
                // 5. Se o livro não for encontrado, limpa os campos
                edtQtdeItem.clear();
                edtValorUnitario.clear();
                edtValorTotalItem.clear();
                // Opcional: Avisar o usuário
                 lblMensagemValidacao.setText("Produto não encontrado.");
                 lblMensagemValidacao.setVisible(true);
            }

        } catch (NumberFormatException e) {
            //Se o usuário digitar algo que não é um número
            limparCamposItem();
            //Avisar o usuário
            lblMensagemValidacao.setText("Código do produto inválido.");
            lblMensagemValidacao.setVisible(true);
        }
    }
    
    //Calcula o total do item
    @FXML
    private void calcularTotalItem(ActionEvent event){
        try{
            double total = Integer.valueOf(edtQtdeItem.getText()) * Double.parseDouble(edtValorUnitario.getText());
            edtValorTotalItem.setText(String.valueOf(total));
        } catch (NumberFormatException e){
            
        }
    }
      
    //Habilita e desabilita os botões, conforme haja algum item selecionado
    public void habilitarBotoesItem(){
        Boolean resultado = true;        

        if (itemSelecionado != null){
            resultado = false;    
        }

        btnExcluirItem.setDisable(resultado);              
        btnVisualizarItem.setDisable(resultado);                
    }
    
    //VINCULAR ESSA FUNÇÃO COM O BOTÃO DE CONSULTAR
    private void consultarItemProd(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/CadProdutoView.fxml"));
            //Cria instância do Controller
            Parent root = loader.load();
                        
            //Cria variável para manipular controller
            CadProdutoController controller = loader.getController();
                        
            LivroModel livro = ListasController.livroDAO.buscarPorCod(itemSelecionado.getCodLivro());
            controller.livro = livro;
                        
            //Define o tipo de operação do formulário para consulta
            controller.configurarTela(3);                     
                      
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
    
}
