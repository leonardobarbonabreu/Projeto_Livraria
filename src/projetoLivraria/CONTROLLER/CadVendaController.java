package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import projetoLivraria.DAO.LivroDAO;
import projetoLivraria.DAO.VendaDAO;
import projetoLivraria.MODEL.ItemVendaModel;
import projetoLivraria.MODEL.LivroModel;
import projetoLivraria.MODEL.VendaModel;

public class CadVendaController implements Initializable{
    
    //Botões
    @FXML
    private Button btnAdicionarProd;
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
    private TextField edtISBN;
    @FXML
    private TextField edtQtdeItem;    
    @FXML
    private TextField edtDescontoItem;    
    @FXML
    private TextField edtPesquisaItem;            
    
    //Colunas das tabelas
    @FXML
    private TableView<ItemVendaModel> listaItemVenda;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemCodProduto;
    @FXML
    private TableColumn<ItemVendaModel, Double> itemDesconto;
    @FXML
    private TableColumn<ItemVendaModel, Integer> itemISBN;
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
                habilitarBotoesItem();
                carregarItemVenda(itemSelecionado);
            } else {
                
            }            
        });
    }    
    public void configurarTela(int TipoOperacao){
        //Grava para que possamos utilizar ela em outros trechos
        TIPO_OPERACAO = TipoOperacao;        
        
        //Define valores para os campos e inicializa a lista de itens da venda
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
            codVenda = venda.getUltimoCodigoVenda()+ 1;
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
        
        //lista de Itens;
        listaItens = FXCollections.observableArrayList();; 
        
        return;
    }
    
    private boolean validarCamposItem(){
        //Validações dos campos p/ adição do item
        //A FAZER
        return true;
    }
    
    @FXML    
    private void adicionarItem(ActionEvent event){
        if (validarCamposItem() == false){
            return;
        }

        ItemVendaModel item;                   
        int codLivro;
        //codLivro = ListasController.livroDAO.buscarPorCod(codLivro);
        //Double valor = (edtQtdeItem.getText() * edt)
        //item = new ItemVendaModel(codlivro, codVenda, valor, 0, Double.valueOf((edtQtdeItem.getText()));
            
        //limparCamposItem();
    }    
    
    @FXML
    private void excluirItem(ActionEvent event){
        //Caso o item não esteja selecionado, por segurança, retornar
        if (itemSelecionado == null) {
            return;
        }
                
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de exclusão");
        alertaExclusao.setHeaderText("Deseja realizar a exclusão do item "+""+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
           //remove o item da lista                      
           listaItens.remove(itemSelecionado);
           //Limpar itemSelecionado
           //itemSelecionado = null;
           habilitarBotoesItem();
        }
        
        return;
    }
    
    @FXML
    private void visualizarItem(ActionEvent event){
        //Caso o item não esteja selecionado, por segurança, retornar
        if (itemSelecionado == null) {
            return;
        }
        
        return;
    }
    
    @FXML
    private void gravarVenda(ActionEvent event){
        if (validarCamposItem()== false){
            return;
        }
        VendaModel venda;
        
        //MANIPULAR ESTOQUE DO LIVRO, USANDO A LISTA DE ITENS EM MEMÓRIA                    
        
        
//        if(TIPO_OPERACAO == 1){                        
//            venda = new VendaModel(edtTitulo.getText(), Integer.valueOf(edtISBN.getText()), edtAutor.getText(),
//                String.valueOf(cmbGenero.getValue()), edtDtLancamento.getValue(), String.valueOf(cmbIdioma.getValue()),
//                Integer.valueOf(edtQtdePag.getText()), Double.valueOf(edtValor.getText()),
//                Integer.valueOf(edtQtdeEstoque.getText()), chkDisponibilidade.isSelected());
//        
//            
//         
//            ListasController.vendaDAO.adicionar(venda);
//            
//            //comando para setar (alterar) os dados do livro ja cadastrado
//        } else if (TIPO_OPERACAO == 2) {
//            venda.setAutor(edtAutor.getText());
//            venda.setDisponibilidade(chkDisponibilidade.isSelected());
//            venda.setDtLancamento(edtDtLancamento.getValue());
//            venda.setIdioma((String) cmbIdioma.getValue());
//            venda.setIsbn(Integer.valueOf(edtISBN.getText()));
//            venda.setQtdeEstoque(Integer.valueOf(edtQtdeEstoque.getText()));
//            venda.setTitulo(edtTitulo.getText());
//            venda.setQtdePag(Integer.valueOf(edtQtdePag.getText()));
//            venda.setValor(Double.valueOf(edtValor.getText()));
//            venda.setGenero(String.valueOf(cmbGenero.getValue()));
//                        
//            ListasController.vendaDAO.atualizar(venda);        
//        }
//        
////        limparCampos();
//        fecharJanela(event);

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
        //desabilita campos
        edtDescontoVenda.setDisable(true);
        
        //DADOS VENDA
        edtNomeComprador.setDisable(true);
        edtSubtotal.setDisable(true);
        edtTotal.setDisable(true);        
        cmbFormaPgto.setDisable(true);
        
        //DADOS ITEM    
        edtISBN.setDisable(true);
        edtQtdeItem.setDisable(true);
        edtPesquisaItem.setDisable(true);
        
        //botões
        btnAdicionarProd.setVisible(false);              
        btnExcluirItem.setVisible(false);              
        btnGravarVenda.setVisible(false);              
    }   
    
    //Carrega dados da venda
    public void carregarVenda(VendaModel venda){
        edtNomeComprador.setText(venda.getNomeComprador());        
        edtTotal.setText(String.valueOf(venda.getValorTotal()));
        edtSubtotal.setText(String.valueOf(venda.getValorSubtotal()));        
        //cmbFormaPgto.getSelectionModel().select(venda.getMetodoPagamento());
        //O conteúdo instanciado da lista Itens 
        listaItens = venda.getItens();               
    }    
    
    //Carrega os dados do item informado nos campos do item
    public void carregarItemVenda(ItemVendaModel item){
        edtQtdeItem.setText(String.valueOf(item.getQtde()));
        //edtISBN.setText(String.valueOf());        
        //A FAZER
    }     
    
    //Habilita e desabilita os botões, conforme haja algum item selecionado
    public void habilitarBotoesItem(){
        Boolean resultado = false;        

        if (itemSelecionado != null){
            resultado = true;    
        }

        btnAdicionarProd.setDisable(resultado);              
        btnExcluirItem.setDisable(resultado);              
        btnVisualizarItem.setDisable(resultado);                
    }
}
