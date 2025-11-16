package projetoLivraria.MODEL;

import java.time.LocalDate; // Importa a classe para registrar a data e hora exatas da venda.
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendaModel {
    private static int ultimoCodVenda = 0;  //Variável estática para controlar o último código gerado, começa em 0
    private int codVenda;
    private String nomeComprador;
    private LocalDate emissao;
    private ObservableList<ItemVendaModel> itens;
    private double valorSubtotal;
    private double valorDesconto;
    private double valorTotal;       
    private String metodoPagamento;
    private boolean cancelado;
    
    
    //Construtor
    public VendaModel(String nomeComprador,LocalDate emissao,String metodoPagamento, ObservableList<ItemVendaModel> itens, double valorSubtotal, double valorTotal, double valorDesconto) {
    //public VendaModel(String nomeComprador,LocalDate emissao,String metodoPagamento, ObservableList<ItemVendaModel> itens) {        
        this.codVenda = ++ultimoCodVenda;  //Incrementa o contador estático e depois atribui ao codVenda da instância
        this.nomeComprador = nomeComprador;
        this.emissao = emissao;
        this.metodoPagamento = metodoPagamento;
        this.itens = FXCollections.observableArrayList();     //Inicialização da lista de itens
                
        this.valorDesconto = valorDesconto;
        this.valorSubtotal = valorSubtotal;
        this.valorTotal = valorTotal;                        
        this.cancelado = false;
    }
    
    public int getCodVenda() {
        return codVenda;
    }
        
    public static int getUltimoCodigoVenda(){
        return ultimoCodVenda;
    }  
    
    //DATA DE EMISSAO
    public LocalDate getEmissao() {
        return emissao;
    }
          
    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }
    
//    //FORMA PGTO;
    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    //NOME COMPRADOR
    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    //TOTAL
    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    //SUBTOTAL
    public double getValorSubtotal() {
        return valorSubtotal;
    }
    
    public void setValorSubtotal(double valorSubtotal) {
        this.valorSubtotal = valorSubtotal;
    }    
        
    //ITENS
    public void setItens(ObservableList<ItemVendaModel> itens){
        this.itens = itens;
    }
    
    public ObservableList<ItemVendaModel> getItens() {
        return itens;
    }
    
    public ItemVendaModel getItem(int codItemVenda) {
        for(ItemVendaModel item : getItens()){
            if(item.getCodItemVenda() == codItemVenda){
                return item;
            }
        }
        return null;
    }
    
    //DESCONTO
    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
    
    //CANCELADO
    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }   
    
    public boolean getCancelado(){                
        return cancelado;
    }
}
