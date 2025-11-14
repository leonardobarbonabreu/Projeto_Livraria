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
    @FXML
    private Button btnSelecionarProd;

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
    private Text lblMensagemValidacaoItem;
    @FXML
    private Text lblMensagemValidacaoVenda;
    
    @FXML
    private Text txtCodVenda;

    @FXML
    private Text txtDtEmissao;

    @FXML
    private Text txtTipoOperacao;

    public int TIPO_OPERACAO;

    private int codVenda;    
    public VendaModel venda; //Variável local de venda usada para receber venda p/ alteração/consulta
    public ObservableList<ItemVendaModel> listaItens; // Lista de itens para venda     
    //Variável utilizada para manipular o item selecionado na tabela
    public ItemVendaModel itemSelecionado;
    //Livro selecionado no formulário de seleção
    public LivroModel produtoSelecionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // Listener para seleção de item na tabela
        listaItemVenda.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {                
                itemSelecionado = newItem;
                produtoSelecionado = ListasController.livroDAO.buscarPorCod(itemSelecionado.getCodLivro());
                carregarDadosProduto(produtoSelecionado);
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
        lblMensagemValidacaoItem.setVisible(false);
        lblMensagemValidacaoVenda.setVisible(false);        
        
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
        //popula os campos como Forma de Pagamento
        cmbFormaPgto.getItems().add("PIX");
        cmbFormaPgto.getItems().add("Crédito");
        cmbFormaPgto.getItems().add("Débito");
        cmbFormaPgto.getItems().add("Dinheiro");
        cmbFormaPgto.getItems().add("Boleto");
                
    }
    
    //EXIBE POP UP DE ERRO
    private void exibirAlertaErro(String titulo, String cabecalho){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro de validação.");
        alert.setHeaderText(titulo);
        alert.setContentText(cabecalho);
        alert.showAndWait();
    }

    private boolean validarCamposVenda(){
        lblMensagemValidacaoVenda.setVisible(false);
        lblMensagemValidacaoItem.setVisible(false);        
        String msg;
        
        //CASO A LISTA ESTEJA VAZIA
        if (listaItens.isEmpty()) {
            msg = "A venda deve conter ao menos um item.";
            exibirAlertaErro("Lista Vazia", msg);
            lblMensagemValidacaoVenda.setText(msg);
            lblMensagemValidacaoVenda.setVisible(true);
            return false;
        }
        
        //NOS ITENS
        for (ItemVendaModel item : listaItens) {
            // Busca o livro correspondente no DAO principal
            LivroModel livro = ListasController.livroDAO.buscarPorCod(item.getCodLivro());
            //CASO O ITEM NÃO EXISTA
            if (livro == null) {
                msg = "Erro: Produto " + livro.getTitulo() + " não encontrado.";
                exibirAlertaErro("Livro não associado corretamente", msg);
                lblMensagemValidacaoItem.setText(msg);
                lblMensagemValidacaoItem.setVisible(true);
                return false;
            }
            
            //CASO A QTDE DE COMPRA FOR MENOR QUE A DE ESTOQUE
            if (livro.getQtdeEstoque() < item.getQtde()) {
                msg = "Estoque insuficiente para o item"+ item.getCodItemVenda() + ":" + livro.getTitulo();
                exibirAlertaErro("Sem estoque", msg);
                lblMensagemValidacaoItem.setText(msg);
                lblMensagemValidacaoItem.setVisible(true);
                return false;
            }
        }    
        
        //DESCONTO
        try {
            Double valor = Double.valueOf(edtDescontoVenda.getText());
            if(valor < 0){
                msg = "O campo Desconto deve ser zero ou um número positivo .";
                exibirAlertaErro("Erro de Formato: Desconto", msg);                        
                lblMensagemValidacaoVenda.setText(msg);
                lblMensagemValidacaoVenda.setVisible(true);  
                return false;
            }            
        } catch (NumberFormatException e) {
            msg = "O campo Desconto deve conter apenas números.";
            exibirAlertaErro("Erro de Formato: Desconto", msg);
            lblMensagemValidacaoVenda.setText(msg);
            lblMensagemValidacaoVenda.setVisible(true);            
            return false;
        }

        //FORMA DE PGTO
        if (cmbFormaPgto.getValue() == null) {
            msg = "Forma de Pagamento deve ser selecionada.";
            exibirAlertaErro("Campo obrigatório: Forma de Pagamento", msg);
            lblMensagemValidacaoVenda.setText(msg);
            lblMensagemValidacaoVenda.setVisible(true);            
            return false;
        }
        
        return true;
    }
        
    //Valida os campos do item, antes de inserí-lo a lista
    private boolean validarCamposItem(){
        lblMensagemValidacaoItem.setVisible(false);                    
        String msg;
        //Validações dos campos p/ adição/alteração do item
        //COD PROD
        try {
            Integer.valueOf(edtCodigoItem.getText());
        } catch (NumberFormatException e) {
            msg = "O campo Código deve apresentar um valor válido. Selecione-o novamente.";
            exibirAlertaErro("Erro de Formato: Código do item", msg);
            lblMensagemValidacaoVenda.setText(msg);
            lblMensagemValidacaoVenda.setVisible(true);            
            return false;
        }        
        
        // QTDE        
        try {
            int qtde = Integer.valueOf(edtQtdeItem.getText());
            if(qtde <= 0){
                msg = "O campo Quantidade deve conter apenas números positivos";
                exibirAlertaErro("Erro de Formato: Quantidade", msg);
                lblMensagemValidacaoItem.setText(msg);
                lblMensagemValidacaoItem.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            msg = "O campo Quantidade deve conter apenas números inteiros.";
            exibirAlertaErro("Erro de Formato: Quantidade", msg);
            lblMensagemValidacaoItem.setText(msg);
            lblMensagemValidacaoItem.setVisible(true);            
            return false;
        }
        
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
        int codLivro = Integer.parseInt(edtCodigoItem.getText());
                
        Boolean itemExiste = false;
        for(ItemVendaModel item1 : listaItens){
            if(item1.getCodLivro() == codLivro){
                itemExiste = true;                
            }
        }
                        
        if ((itemSelecionado == null)&&(!itemExiste)) { // Novo item
            // Criar novo item
            item = new ItemVendaModel(
                    codLivro, codVenda, Double.parseDouble(edtValorUnitario.getText()),
                    Integer.parseInt(edtQtdeItem.getText()));            
                                    
            listaItens.add(item);
            
        } else { // Edição de Item
            item = itemSelecionado;
            item.setCodVenda(codVenda);
            item.setCodLivro(codLivro);
            item.setValorVenda(Double.parseDouble(edtValorUnitario.getText()));
            item.setQtde(Integer.parseInt(edtQtdeItem.getText()));
                        
            listaItens.set(listaItens.indexOf(item), item);
        }
        
        //Limpa o produto selecionado e o item, respectivamente
        produtoSelecionado = null;
        itemSelecionado = null;
        
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
        String nome = ListasController.livroDAO.buscarPorCod(itemSelecionado.getCodLivro()).getTitulo();
        alertaExclusao.setHeaderText("Deseja realizar a exclusão do item "+nome+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
           //remove o item da lista                      
           listaItens.remove(itemSelecionado);
           
           listaItemVenda.getSelectionModel().clearSelection();
           
           itemSelecionado = null;
           produtoSelecionado = null;
           
           atualizaValoresTotais();
           limparCamposItem();
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
        
        //VALIDAÇÕES
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
        
        //CAMPOS
        if("".equals(edtNomeComprador.getText())){
            edtNomeComprador.setText("Consumidor não Identificado");
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
            venda.setMetodoPagamento((String) cmbFormaPgto.getValue());
            venda.setItens(listaItens);

            venda.setValorSubtotal(Double.parseDouble(edtSubtotal.getText()));
            venda.setValorTotal(Double.parseDouble(edtTotal.getText()));            
            venda.setValorDesconto(Double.parseDouble(edtDescontoVenda.getText()));
                        
            ListasController.vendaDAO.atualizar(venda);
        }

        // FECHAR JANELA
        fecharJanela(event);
    }        
    
    //FUNÇÃO RESPONSÁVEL POR CALCULAR OS VALORES E EXIBIR NOS CAMPOS DE SUBTOTAL E TOTAL
    @FXML
    private void atualizaValoresTotais(){
       double total = 0;
       double subtotal = 0;
       double desconto;
       
       try{
         desconto = Double.parseDouble(edtDescontoVenda.getText());
       } catch (NumberFormatException e) {
          desconto = 0; 
          edtDescontoVenda.setText(String.valueOf(desconto));  
       }
                            
       for (ItemVendaModel item : listaItens) {
           subtotal += (item.getValorVenda() * item.getQtde());
       } 
       
       if (desconto > subtotal) {
           lblMensagemValidacaoVenda.setText("Valor de desconto superior ao valor de venda.");
           lblMensagemValidacaoVenda.setVisible(true);
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
        edtNomeComprador.setEditable(false);
        edtSubtotal.setEditable(false);
        edtTotal.setEditable(false);
        edtDescontoVenda.setEditable(false);
        cmbFormaPgto.setDisable(true);
        
        //DADOS ITEM                           
        camposItemArea.setVisible(false);

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
                    
    //Carrega os dados padrões do livro selecionado nos campos do item    
    private void carregarDadosProduto(LivroModel livro){                

        if (livro != null){
            //CODLIVRO  
            edtCodigoItem.setText(String.valueOf(livro.getCodLivro()));
            //TITULO
            edtVisuTitulo.setText(livro.getTitulo());
            //ISBN
            edtVisuISBN.setText(String.valueOf(livro.getIsbn()));
            //VL UN
            edtValorUnitario.setText(String.valueOf(livro.getValor()));
            
            //POR PADRÃO, RECEBE 1
            int qtde = 1;                       
            
            //PROCURA PELA QUANTIDADE DO ITEM JÁ EXISTENTE
            for(ItemVendaModel item : listaItens){
                if (item.getCodLivro() == livro.getCodLivro()){
                    qtde = item.getQtde();                    
                    break;
                }                
            }                           
            
            //QTDE
            edtQtdeItem.setText(String.valueOf(qtde));            
            
            //TOTAL
            double total = qtde * Double.parseDouble(edtValorUnitario.getText());                                                                                          
            edtValorTotalItem.setText(String.valueOf(total));
            
            edtQtdeItem.requestFocus();

        } else {            
            edtQtdeItem.clear();
            edtValorUnitario.clear();
            edtValorTotalItem.clear();
            // Opcional: Avisar o usuário
            lblMensagemValidacaoItem.setText("Produto não encontrado.");
            lblMensagemValidacaoItem.setVisible(true);
        }
    }
    
    //Calcula o total do item
    @FXML
    private void calcularTotalItem(ActionEvent event){
        try{
            double total = Integer.valueOf(edtQtdeItem.getText()) * Double.valueOf(edtValorUnitario.getText());
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
            stage.setTitle("Formulário de Visualização de Produto");
            stage.setScene(new Scene(root));

            // (Opcional) Bloqueia a interação com a janela de listas até que esta seja fechada
            // stage.initModality(Modality.APPLICATION_MODAL); 
            //mostra o formulário
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }        
    @FXML
    private void selecionarProduto(ActionEvent event){
       itemSelecionado = null;
       try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/SelecionarItemView.fxml"));    
            //Cria instância do Controller
            Parent root = loader.load();

            //Cria variável para manipular controller
            SelecionarItemController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Seleção de Produto para Venda");
            stage.setScene(new Scene(root));

            stage.showAndWait();

            //Quando ele sair da seleção
            produtoSelecionado = controller.getProdutoSelecionado();

            carregarDadosProduto(produtoSelecionado);
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
}
