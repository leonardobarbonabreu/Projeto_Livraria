
package projetoLivraria.DAO;

//import java.util.ArrayList;
//import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projetoLivraria.MODEL.LivroModel;

public class LivroDAO implements LivroInterfaceDAO {
    private ObservableList<LivroModel> listaProduto = FXCollections.observableArrayList();
    
    @Override
    public void adicionar(LivroModel livro){
        listaProduto.add(livro);
    };
    
    @Override
    //Percorre todos os livros na lista de produto, procurando pelo codigo
    //caso encontrado, ela retorna o livro
    //caso contrário, ela retorna null
    public LivroModel buscarPorCod(int codLivro){
        for(LivroModel livro : listaProduto){
            if(livro.getCodLivro() == codLivro){
                return livro;                
            }
        }
        return null;
    }
        
    @Override
    public void atualizar(LivroModel livro){
        LivroModel livroAlterado;
        //localize o item a ser alterado pelo codLivro
        livroAlterado = buscarPorCod(livro.getCodLivro());
        
        //se o resultado da busca for diferente de null
        if (livroAlterado != null) {
            //faça o item da posicao do livroAlterado receber livro;
            listaProduto.set(listaProduto.indexOf(livroAlterado), livro);
        }
    };
    
    @Override
    public void deletar(int codLivro){
        LivroModel livroExcluido;
        //localze o item a ser exluido pelo codlivro
        livroExcluido = buscarPorCod(codLivro);
        
        if (livroExcluido != null){
            listaProduto.remove(livroExcluido);        
        }
    };
    
    @Override
    public ObservableList<LivroModel> listarTodos(){
        return listaProduto;
    };
        
}
