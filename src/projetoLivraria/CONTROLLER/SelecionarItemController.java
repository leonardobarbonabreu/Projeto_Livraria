package projetoLivraria.CONTROLLER;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import projetoLivraria.MODEL.LivroModel;

public class SelecionarItemController implements Initializable {

    //Lista de Produtos
    @FXML 
    public TableView<LivroModel> listaProduto;

    @FXML
    private TextField edtPesquisaProduto;

    //Colunas da Lista de Produtos
    @FXML
    private TableColumn<LivroModel, Integer> prodCodLivro;        
    @FXML
    private TableColumn<LivroModel, String> prodTitulo;    
    @FXML
    private TableColumn<LivroModel, Integer> prodISBN;
    @FXML
    private TableColumn<LivroModel, String> prodAutor;
    @FXML
    private TableColumn<LivroModel, Double> prodValor;
    @FXML
    private TableColumn<LivroModel, Integer> prodQtdeEstoque;
    @FXML
    private TableColumn<LivroModel, String> prodIdioma;
    @FXML
    private TableColumn<LivroModel, LocalDate> prodDtLancamento;
    
//    @FXML
//    private Button btnVisualizarProd;
    @FXML
    private Button btnSelecionarItem;
    @FXML
    public LivroModel produtoSelecionado;
    
    // ADICIONADO: Vamos guardar a lista completa original aqui
    private ObservableList<LivroModel> masterList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Pegue a lista "mestra" de produtos
        masterList = ListasController.livroDAO.listarTodos();

        // 2. Crie a FilteredList envolvendo a lista mestra
        FilteredList<LivroModel> filteredList = new FilteredList<>(masterList);

        // 3. ADICIONADO: Defina o filtro inicial (só livros disponíveis)
        filteredList.setPredicate(livro -> livro.getDisponibilidade());

        // 4. ADICIONADO: Crie o listener de pesquisa do TextField
        edtPesquisaProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(livro -> {
                // Se o campo de pesquisa está vazio, aplica só o filtro de disponibilidade
                if (newValue == null || newValue.isEmpty()) {
                    return livro.getDisponibilidade();
                }

                // Se não, combina o filtro de disponibilidade COM a pesquisa
                String lowerCaseFilter = newValue.toLowerCase();

                // Regra de pesquisa (pode adicionar mais campos, como autor, etc.)
                boolean correspondePesquisa = livro.getTitulo().toLowerCase().contains(lowerCaseFilter) ||
                                              String.valueOf(livro.getIsbn()).contains(lowerCaseFilter) ||
                                              livro.getAutor().toLowerCase().contains(lowerCaseFilter) ||
                                              String.valueOf(livro.getCodLivro()).contains(lowerCaseFilter);

                // O livro só aparece se estiver disponível E corresponder à pesquisa
                return livro.getDisponibilidade() && correspondePesquisa;
            });
        });

        // 5. ADICIONADO: Use uma SortedList para permitir ordenar a tabela filtrada
        SortedList<LivroModel> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(listaProduto.comparatorProperty());

        // 6. Defina a lista final (filtrada e ordenada) na tabela
        listaProduto.setItems(sortedList);        
        
        //listaProduto.setItems(ListasController.livroDAO.listarTodos());
        //Inicializando campos da lista de Produtos            
        prodCodLivro.setCellValueFactory(new PropertyValueFactory<>("codLivro"));
        prodTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        prodISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));        
        prodAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        prodValor.setCellValueFactory(new PropertyValueFactory<>("valor"));               
        prodDtLancamento.setCellValueFactory(new PropertyValueFactory<>("dtLancamento")); 
        prodQtdeEstoque.setCellValueFactory(new PropertyValueFactory<>("qtdeEstoque"));   
        prodIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma")); 
        
        listaProduto.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
        
            if (newItem != null) {
               produtoSelecionado = newItem; 
                btnSelecionarItem.setDisable(false); 
                //btnVisualizarProd.setDisable(false); 
            } else {
               btnSelecionarItem.setDisable(true);               
               //btnVisualizarProd.setDisable(true);                
               produtoSelecionado = null; // Limpa a variável 
            }            
        });        
        
    }    
    
    //Retorna o produto selecionado, para que o formulário pai tenha acesso
    public LivroModel getProdutoSelecionado() {
        return this.produtoSelecionado;
    }    
    
    @FXML
    private void selecionarItem(ActionEvent event){
        if (produtoSelecionado != null) {
            fecharJanela(event);
        }
        
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        // Obtém o Stage (janela) a partir do botão que foi clicado
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fecha a janela
        stage.close();
    }   
    
}
