package projetoLivraria.CONTROLLER;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Laboratorio
 */
public class ListasController implements Initializable {

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
    
    @FXML
    private HBox VendaSelecionadaArea;    
    @FXML
    private Text lblVendaSelecionada;

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
    public TableView listaVenda;
    
    //Colunas da Lista de vendas
    @FXML
    private TableColumn vendaCodigo;
    @FXML
    private TableColumn vendaTotal;
    @FXML
    private TableColumn vendaEmissao;
    @FXML
    private TableColumn vendaStatus;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa a tela no estado NAVEGANDO
        estadoAtual = EstadoLista.NAVEGANDO;
        atualizarEstado();

        // Listener para seleção de item na tabela
        listaProduto.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                estadoAtual = EstadoLista.ITEM_SELECIONADO;
            } else {
                estadoAtual = EstadoLista.NAVEGANDO;
            }
            atualizarEstado();
        });
        
        // Listener para seleção de item na tabela
        listaVenda.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                estadoAtual = EstadoLista.ITEM_SELECIONADO;
            } else {
                estadoAtual = EstadoLista.NAVEGANDO;
            }
            atualizarEstado();
        });
        
    }

    private void atualizarEstado() {
        //Caso não tenha nenhum item selecionado, permita somenta a adição de um registro
        if (estadoAtual == EstadoLista.NAVEGANDO) {
            btnAdicionarProd.setDisable(false);
            btnVisualizarProd.setDisable(true);
            btnEditarProd.setDisable(true);
            btnExcluirProd.setDisable(true);

            edtPesquisaProduto.setDisable(false);
            lblProdutoSelecionado.setDisable(true);
            
            btnAdicionarProd.setDisable(false);
            btnVisualizarProd.setDisable(true);
            btnEditarProd.setDisable(true);
            btnExcluirProd.setDisable(true);

            edtPesquisaProduto.setDisable(false);
            lblProdutoSelecionado.setDisable(true);
        } else { // ITEM_SELECIONADO
        //Caso o usuário clique em um item na lista, permita que ele visualize, edite e exclua o registro    
            //Aba Produto
            btnAdicionarProd.setDisable(false);
            btnVisualizarProd.setDisable(false);
            btnEditarProd.setDisable(false);
            btnExcluirProd.setDisable(false);

            edtPesquisaProduto.setDisable(false);
            lblProdutoSelecionado.setDisable(false);
            
            //Aba Venda
            btnAdicionarVenda.setDisable(false);
            btnVisualizarVenda.setDisable(false);
            btnEditarVenda.setDisable(false);
            btnCancelarVenda.setDisable(false);

            edtPesquisaVenda.setDisable(false);
            lblVendaSelecionada.setDisable(false);

        }
    }

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
        listaProduto.getItems().add(0, "Olá"); // Retirar essa linha depois
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

    @FXML
    private void abrirCadVenda(int TipoOperacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetoLivraria/VIEW/CadVendaView.fxml"));
            //Cria instância do Controller
            Parent root = loader.load();
                        
            //Cria variável para manipular controller
            CadVendaController controller = loader.getController();
            
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