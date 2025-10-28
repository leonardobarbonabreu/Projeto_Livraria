package projetoLivraria.MODEL;

public class ItemVendaModel {
    //Campos
    private static int ultimoCodItemVenda = 0;  //Variável estática para controlar o último código gerado, começa em 0
    
    private int codVenda;
    private int codItemVenda;
    private int codLivro;    
    private double valorVenda;       
    private double desconto;       
    private int qtde;
    
    //Construtor
    public void ItemVendaModel(int codLivro, int codVenda, double valorVenda, double desconto, int qtde) {
        this.codItemVenda = ++ultimoCodItemVenda;        
        this.codLivro = codLivro;        
        this.codVenda = codVenda;
        this.valorVenda = valorVenda;
        this.desconto = desconto;
        this.qtde = qtde;
    }

    public int getCodItemVenda() {
        return codItemVenda;
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
