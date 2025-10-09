
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
import javafx.stage.Stage;

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
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
    
}
