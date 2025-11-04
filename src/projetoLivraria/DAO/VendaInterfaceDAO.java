package projetoLivraria.DAO;

import javafx.collections.ObservableList;
import projetoLivraria.MODEL.ItemVendaModel;
import projetoLivraria.MODEL.VendaModel;

public interface VendaInterfaceDAO {
    void adicionar(VendaModel venda);
    void atualizar(VendaModel venda);
    void cancelar(int codVenda);
    VendaModel buscarPorCod(int codVenda);
    ObservableList<VendaModel> listarTodos();
    ObservableList<ItemVendaModel> listarItens(VendaModel venda);    
}
