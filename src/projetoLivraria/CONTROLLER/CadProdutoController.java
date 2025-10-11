
package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    //botões
    @FXML
    private Button btnGravarProd;
    @FXML
    private Button btnRetornar;
    
    //labels e textos
    @FXML
    private Text txtTipoOperacao;
    
    //Variáveis
    //TIPO DA OPERAÇÃO(1- ADIÇÃO 2-EDIÇÃO 3-CONSULTA)
    public int TIPO_OPERACAO;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
 
    };
    
    //popula os campos
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
    
    public void configurarTela(int TipoOperacao){
        //Grava para que possamos utilizar ela em outros trechos
        TIPO_OPERACAO = TipoOperacao;
        inicializarCampos();
                
        switch (TipoOperacao) {            
        case 1://ADIÇÃO
            txtTipoOperacao.setText("Cadastro de Produto");
            break;
        case 2://2-EDIÇÃO
            txtTipoOperacao.setText("Alteração de Produto");
            break;                
        case 3://3-CONSULTA
            txtTipoOperacao.setText("Consulta de Produto");
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
        
        cmbIdioma.getSelectionModel().clearSelection();
        
        cmbGenero.getSelectionModel().clearSelection();
        
        edtDtLancamento.setValue(null);
        
        
    }
    
    @FXML
    private void gravarProduto(ActionEvent event){
        limparCampos();
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
    
}
