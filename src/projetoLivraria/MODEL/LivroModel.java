package projetoLivraria.MODEL;

import java.time.LocalDate;

public class LivroModel {
    private static int ultimoCodLivro = 0;  //Variável estática para controlar o último código gerado, começa em 1    
    //Campos
    private int codLivro;
    private String titulo;
    private int isbn;
    private String autor;
    private String genero;
    private LocalDate dtLancamento;    
    private String idioma;
    private int qtdePag;   
    private double valor;   
    private int qtdeEstoque;
    private boolean disponibilidade;
    //private String pubAlvo;
    
    //Construtor
    public LivroModel(String titulo, int isbn, String autor, String genero,
            LocalDate dtLancamento, String idioma, int qtdePag, double valor, int qtdeEstoque, boolean disponibilidade) {
        this.codLivro = ++ultimoCodLivro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.genero = genero;
        this.dtLancamento = dtLancamento;
        this.idioma = idioma; 
        this.qtdePag = qtdePag;
        this.valor = valor;
        this.qtdeEstoque = qtdeEstoque;
        this.disponibilidade = disponibilidade;
        //this.pubAlvo = pubAlvo;
    }

    //codLivro
    public int getCodLivro() {
        return codLivro;
    }
      
    //titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    //isbn
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    //autor
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    //genero
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    //dtlancamento
    public LocalDate getDtLancamento() {
        return dtLancamento;
    }

    public void setDtLancamento(LocalDate dtLancamento) {
        this.dtLancamento = dtLancamento;
    }

    //idioma
    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    //qtdePag
    public int getQtdePag() {
        return qtdePag;
    }

    public void setQtdePag(int qtdePag) {
        this.qtdePag = qtdePag;
    }

    //valor
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    //qtdeEstoque
    public int getQtdeEstoque() {
        return qtdeEstoque;
    }
    
    public void setQtdeEstoque(int qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }

    //disponibilidade
    public boolean getDisponibilidade() {
        return disponibilidade;
    }
    
    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

//    public String getPubAlvo() {
//        return pubAlvo;
//    }
//
//    public void setPubAlvo(String pubAlvo) {
//        this.pubAlvo = pubAlvo;
//    }
//        
}
