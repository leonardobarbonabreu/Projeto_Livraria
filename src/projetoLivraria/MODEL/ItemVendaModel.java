package projetoLivraria.MODEL;

public class ItemVendaModel {
    //Campos
    private int codItemVenda;
    private int codLivro;
    private int codVenda;
    private double valorVenda;       
    private double desconto;       
    private int qtde;
    
    //Construtor
    public void ItemVendaModel(int codItemVenda, int codLivro, int codVenda, double valorVenda, double desconto, int qtde) {
        this.codItemVenda = codItemVenda;        
        this.codLivro = codLivro;        
        this.codVenda = codVenda;
        this.valorVenda = valorVenda;
        this.desconto = desconto;
        this.qtde = qtde;
    }

    public int getCodItemVenda() {
        return codItemVenda;
    }

    public void setCodItemVenda(int codItemVenda) {
        this.codItemVenda = codItemVenda;
    }
    
    //codLivro
    public int getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }         
}
