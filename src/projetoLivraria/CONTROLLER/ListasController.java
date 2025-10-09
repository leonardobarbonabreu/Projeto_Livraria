package projetoLivraria.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Laboratorio
 */
public class ListasController implements Initializable {
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    //Botões do CRUD
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
    private TextField edtPesquisa;
    @FXML
    private Text lblItemSelecionado;
    
    //Lista de Produtos
    @FXML 
    public TableView listaProduto;
    
    //Colunas da Lista de Produtos
    @FXML
    private TableColumn prodTitulo;
    @FXML
    private TableColumn prodISBN;
    @FXML
    private TableColumn prodAutor;
    @FXML
    private TableColumn prodPubAlvo;

    @FXML
    private TableColumn prodValor;
    @FXML
    private TableColumn prodQtdePag;
    @FXML
    private TableColumn prodIdioma;
    @FXML
    private TableColumn prodDtLancamento;
    @FXML
    private TableColumn prodDisponibilidade;    
    
    //ABRIR FORMULÁRIO DE CADASTRO DE PRODUTO
    //Cria o formulário de cad. de produto
    //Manipula componentes com base no tipo de operacao:
    // 1 - ADICAO: 
    //      - Desabilitar botão de exclusão, pois não deve excluir um item não criado
    //      - Deixar invisível o codigo interno do Produto, pois ainda não há item;
    // 2 - EDICAO: Habilitar tudo;
    // 3 - CONSULTAR: Desabilitar todos campos e funções de manipulação do registro;
    
    //Por fim, apresenta formulário, em uma nova aba
    @FXML
    private void abrirCadProd(int TipoOperacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/CadProdutoView.fxml"));
            //Cria instância do Controller
            Parent root = loader.load();
                        
            //Cria variável para manipular controller
            CadProdutoController controller = loader.getController();
            
            //Define o tipo de operação do formulário
            controller.configurarTela(TipoOperacao);                     
            
            // Cria um NOVO Stage (uma nova janela)
            Stage stage = new Stage();
            stage.setTitle("Cadastrar Novo Produto");
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
    private void adicionarItem(ActionEvent event){
        
        abrirCadProd(1);
    }

    //VISUALIZAR ITEM
    //Desabilita os campos do form de cad. Produto
    //deixa invisível o botão de gravar produto
    //Cria o formulário de cad. de Produto    
    @FXML
    private void visualizarItem(ActionEvent event){
        
        abrirCadProd(2);
    }
    
    //EDITAR ITEM
    //Habilita campos do form de cad. Produto
    //Cria o formulário de cad. de Produto
    @FXML
    private void editarItem(ActionEvent event){
        abrirCadProd(3);
    }

    //EXCLUIR ITEM
    //Cria um pop up perguntando sobre a exclusão
    @FXML
    private void excluirItem(ActionEvent event){
        
    }
    
}
