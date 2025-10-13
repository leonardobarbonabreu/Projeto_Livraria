package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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

/**
 *
 * @author Leonardo José
 */
public class CadVendaController {
    
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
    private ComboBox<?> cmbFormaPgto;

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
    private TableView<?> listaItemVenda;
    @FXML
    private TableColumn<?, ?> itemCodProduto;
    @FXML
    private TableColumn<?, ?> itemDesconto;
    @FXML
    private TableColumn<?, ?> itemISBN;
    @FXML
    private TableColumn<?, ?> itemQtde;
    @FXML
    private TableColumn<?, ?> itemSubtotal;
    @FXML
    private TableColumn<?, ?> itemTitulo;
    @FXML
    private TableColumn<?, ?> itemValorUn;
    
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
        
    public void configurarTela(int TipoOperacao){
        //Grava para que possamos utilizar ela em outros trechos
        TIPO_OPERACAO = TipoOperacao;
        inicializarCampos();
                
        switch (TipoOperacao) {            
        case 1://ADIÇÃO
            txtTipoOperacao.setText("Cadastro de Produto");
            lblCodVenda.setVisible(false);
            txtCodVenda.setVisible(false);            
            
            break;
        case 2://2-EDIÇÃO
            txtTipoOperacao.setText("Alteração de Produto");
            break;                
        case 3://3-CONSULTA
            txtTipoOperacao.setText("Consulta de Produto");            
            //desabilitaCampos();            
            break;
        default:
            throw new AssertionError();
        }
    }
    
    //preenche os dados padrões dos campos
    private void inicializarCampos(){
        return;
    }

    @FXML    
    private void adicionarItem(ActionEvent event){
        return;
    }
    
    @FXML
    private void excluirItem(ActionEvent event){
        Alert alertaExclusao = new Alert(Alert.AlertType.CONFIRMATION);        
        alertaExclusao.setTitle("Confirmação de exclusão");
        alertaExclusao.setHeaderText("Deseja realizar a exclusão do item "+""+"?");
        //alertaExclusao.setContentText("");
        
        Optional<ButtonType> botaoClicado = alertaExclusao.showAndWait();
        if (botaoClicado.get() == ButtonType.OK) {
                    
        }
        
        return;
    }
    
    @FXML
    private void visualizarItem(ActionEvent event){
        return;
    }
    
    @FXML
    private void gravarVenda(ActionEvent event){
        return;
    }
        
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }    
    
}
