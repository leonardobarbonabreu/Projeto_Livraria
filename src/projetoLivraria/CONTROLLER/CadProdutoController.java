
package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Leonardo José
 */
public class CadProdutoController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    };
    
    //Campos do formulário
    @FXML
    private TextField edtTitulo;
    @FXML
    private TextField edtISBN;
    @FXML
    private TextField edtAutor;
    @FXML
    private ComboBox cmbPubAlvo;

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
    
}
