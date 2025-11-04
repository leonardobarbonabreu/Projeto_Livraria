
package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projetoLivraria.MODEL.LivroModel;
import projetoLivraria.CONTROLLER.ListasController;
/**
 * FXML Controller class
 *
 * @author Leonardo José
 */
public class CadProdutoController implements Initializable {
    
    //Campos do formulário
    @FXML
    private TextField edtTitulo;
    @FXML
    private TextField edtISBN;
    @FXML
    private TextField edtAutor;
    @FXML
    private ComboBox cmbGenero;

    @FXML
    private DatePicker edtDtLancamento;    
    @FXML
    private ComboBox cmbIdioma;
    @FXML
    private TextField edtQtdePag;
    @FXML
    private TextField edtValor;
    @FXML
    private TextField edtQtdeEstoque;
    @FXML
    private CheckBox chkDisponibilidade;
    
    //botões
    @FXML
    private Button btnGravarProd;
    @FXML
    private Button btnRetornar;
    
    //labels e textos
    @FXML
    private Text txtTipoOperacao;
    @FXML
    private Text txtCodProduto;    
    @FXML
    private Text lblMensagemValidacao;
    @FXML
    private Text lblCodProduto;    
    
    //Variáveis
    //TIPO DA OPERAÇÃO(1- ADIÇÃO 2-EDIÇÃO 3-CONSULTA)
    public int TIPO_OPERACAO;
    
    public LivroModel livro;    //variavel local de livro
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
    };
    
    //preenche os dados padrões dos campos
    private void inicializarCampos(){
        
        //popula os campos combo Genero
        cmbGenero.getItems().add("Aventura");
        cmbGenero.getItems().add("Romance");
        cmbGenero.getItems().add("Ação");
        cmbGenero.getItems().add("Terror");
        cmbGenero.getItems().add("Aventura");
        cmbGenero.getItems().add("Ficção científica");
        cmbGenero.getItems().add("Acadêmico");
        
        //popula o combo idioma
        cmbIdioma.getItems().add("Português");
        cmbIdioma.getItems().add("Inglês");
        cmbIdioma.getItems().add("Espanhol");
        cmbIdioma.getItems().add("Alemão");
        cmbIdioma.getItems().add("Francês");
        cmbIdioma.getItems().add("Italiano");
        cmbIdioma.getItems().add("Mandarim");
    }
    
    //funcao para carregar os dados do livro selecionado
    public void carregarLivro(LivroModel livro){
        // Campos de Texto (TextField)
        edtTitulo.setText(livro.getTitulo());
        edtISBN.setText(String.valueOf(livro.getIsbn()));
        edtAutor.setText(livro.getAutor());
        edtQtdePag.setText(String.valueOf(livro.getQtdePag()));
        edtValor.setText(String.valueOf(livro.getValor()));
        edtQtdeEstoque.setText(String.valueOf(livro.getQtdeEstoque()));

        // Campos de Seleção (ComboBox, DatePicker, CheckBox)
        cmbGenero.setValue(livro.getGenero());
        cmbIdioma.setValue(livro.getIdioma());
        edtDtLancamento.setValue(livro.getDtLancamento());
        chkDisponibilidade.setSelected(livro.getDisponibilidade());

        // Labels (Text) que exibem dados
        txtCodProduto.setText(String.valueOf(livro.getCodLivro()));                       
    }
        
    public void configurarTela(int TipoOperacao){
        //Grava para que possamos utilizar ela em outros trechos
        TIPO_OPERACAO = TipoOperacao;
        inicializarCampos();
                
        switch (TipoOperacao) {            
        case 1://ADIÇÃO
            txtTipoOperacao.setText("Cadastro de Produto");
            lblCodProduto.setVisible(false);
            txtCodProduto.setVisible(false);            
                                   
            break;
        case 2://2-EDIÇÃO
            txtTipoOperacao.setText("Alteração de Produto");
            carregarLivro(livro);
            break;                
        case 3://3-CONSULTA
            txtTipoOperacao.setText("Consulta de Produto");            
            carregarLivro(livro);
            desabilitaCampos();            
            break;
        default:
            throw new AssertionError();
        }
    }
    
    private void limparCampos(){
        //limpa os campos da tela de cadastro
        edtTitulo.clear();
        
        edtISBN.clear();
        
        edtAutor.clear();
        
        edtValor.clear();
        
        edtQtdePag.clear();
        
        edtQtdeEstoque.clear();
        
        cmbIdioma.getSelectionModel().clearSelection();
        
        cmbGenero.getSelectionModel().clearSelection();
        
        edtDtLancamento.setValue(null);
               
    }

    //desabilita campos
    private void desabilitaCampos(){
        //desabilita campos
        edtTitulo.setEditable(false);
        edtAutor.setEditable(false);
        edtDtLancamento.setEditable(false);
        edtISBN.setEditable(false);
        edtQtdePag.setEditable(false);        
        edtValor.setEditable(false);
        edtQtdeEstoque.setDisable(true);
        cmbGenero.setDisable(true);
        cmbIdioma.setDisable(true);
        chkDisponibilidade.setDisable(true);
      
        btnGravarProd.setVisible(false);              
    }
    
    //Valida se conteúdo dos campos seguem a regra de negócios e regras básicas de formatação 
    private boolean validarCampos(){
        return true;  
    };
    
    @FXML
    private void gravarProduto(ActionEvent event){
        if (validarCampos() == false){
            return;
        }
        LivroModel Addlivro;
                
        if(TIPO_OPERACAO == 1){
         Addlivro = new LivroModel(edtTitulo.getText(), Integer.valueOf(edtISBN.getText()), edtAutor.getText(),
            String.valueOf(cmbGenero.getValue()), edtDtLancamento.getValue(), String.valueOf(cmbIdioma.getValue()),
            Integer.valueOf(edtQtdePag.getText()), Double.valueOf(edtValor.getText()),
            Integer.valueOf(edtQtdeEstoque.getText()), chkDisponibilidade.isSelected());
        
            
            ListasController.livroDAO.adicionar(Addlivro);
            
            //comando para setar (alterar) os dados do livro ja cadastrado
        } else if (TIPO_OPERACAO == 2) {
            livro.setAutor(edtAutor.getText());
            livro.setDisponibilidade(chkDisponibilidade.isSelected());
            livro.setDtLancamento(edtDtLancamento.getValue());
            livro.setIdioma((String) cmbIdioma.getValue());
            livro.setIsbn(Integer.valueOf(edtISBN.getText()));
            livro.setQtdeEstoque(Integer.valueOf(edtQtdeEstoque.getText()));
            livro.setTitulo(edtTitulo.getText());
            livro.setQtdePag(Integer.valueOf(edtQtdePag.getText()));
            livro.setValor(Double.valueOf(edtValor.getText()));
            livro.setGenero(String.valueOf(cmbGenero.getValue()));
                        
            ListasController.livroDAO.atualizar(livro);        
        }
        
        limparCampos();
        fecharJanela(event);
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
    
}
