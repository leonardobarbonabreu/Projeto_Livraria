package projetoLivraria.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projetoLivraria.MODEL.VendaModel;
import projetoLivraria.MODEL.ItemVendaModel;
//import projetoLivraria.MODEL.LivroModel;

public class VendaDAO implements VendaInterfaceDAO{
    private ObservableList<VendaModel> listaVenda = FXCollections.observableArrayList();
    
    @Override
    public void adicionar(VendaModel venda){
        listaVenda.add(venda);
    };
    
    @Override
    //Percorre todos os livros na lista de produto, procurando pelo codigo
    //caso encontrado, ela retorna o livro
    //caso contrário, ela retorna null
    public VendaModel buscarPorCod(int codVenda){
        for(VendaModel venda : listaVenda){
            if(venda.getCodVenda() == codVenda){
                return venda;                
            }
        }
        return null;
    }
        
    @Override
    public void atualizar(VendaModel venda){
        VendaModel vendaAlterada;
        //localize o item a ser alterado pelo codLivro
        vendaAlterada = buscarPorCod(venda.getCodVenda());
        
        //se o resultado da busca for diferente de null
        if (vendaAlterada != null) {
            //faça o item da posicao do livroAlterado receber livro;
            listaVenda.set(listaVenda.indexOf(vendaAlterada), venda);
        }
    };
    
    @Override
    public void cancelar(int codVenda){
        VendaModel vendaCancelada;
        //localize o item a ser excluido pelo codlivro
        vendaCancelada = buscarPorCod(codVenda);
        
        if (vendaCancelada != null){
            vendaCancelada.setCancelado(true);
        }
    };
    
    @Override
    public ObservableList<VendaModel> listarTodos(){
        return listaVenda;
    };
    
    //ITENS
    @Override //MOSTRAR ITENS
    public ObservableList<ItemVendaModel> listarItens(VendaModel venda){
        return listaVenda.get(venda.getCodVenda()).getItens();        
    };
        
}
