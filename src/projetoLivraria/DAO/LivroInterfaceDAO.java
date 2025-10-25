package projetoLivraria.DAO;

import java.util.List;
import projetoLivraria.MODEL.LivroModel;

//Manual que deixa explícito quais funções a DAO precisa ter
public interface LivroInterfaceDAO {
    void adicionar(LivroModel livro);
    void atualizar(LivroModel livro);
    void deletar(int codLivro);
    LivroModel buscarPorCod(int codLivro);
    List<LivroModel> listarTodos();    
}
