package projetoLivraria.MODEL;

import java.time.Instant;
import java.time.LocalDateTime; // Importa a classe para registrar a data e hora exatas da venda.
import java.util.ArrayList; // Importa a lista dinâmica para armazenar os itens da venda.
import java.util.Date;
import java.util.List; // Importa a interface List.

public class VendaModel {
    private int codVenda;
    private String nomeComprador;
    private Date emissao;
    private List<ItemVendaModel> itens;
    private double valorSubtotal;
    private double valorTotal;       
    private String metodoPagamento;
    
    //Construtor
    public VendaModel(int codVenda,String nomeComprador,Date emissao,String metodoPagamento) {
        this.codVenda = codVenda;
        this.nomeComprador = nomeComprador;
        this.emissao = Date.from(Instant.MIN); 
        this.itens = new ArrayList<>(); 
        this.valorTotal = 0.0; 
        this.metodoPagamento = metodoPagamento;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public List<ItemVendaModel> getItens() {
        return itens;
    }

    public void adicionarItem(ItemVendaModel item) throws Exception {
    // Regra de Negócio: Verificação de Estoque
//    if (item.getQtde() > item.getLivro().getEstoque()) {
//        throw new Exception("Erro: Estoque insuficiente para o livro " + item.getLivro().getTitulo());
//    }

    this.itens.add(item); 
//    this.valorTotal += item.calcularSubtotal(); 
//
//    // Atualiza o estoque do livro
//    int novoEstoque = item.getLivro().getEstoque() - item.getQuantidade();
//    item.getLivro().setEstoque(novoEstoque); 

    }

    
}
