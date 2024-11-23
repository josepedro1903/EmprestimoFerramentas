package model;

public class Ferramenta {

    private int id;
    private String nome;
    private String marca;
    private double custoAquisicao;

    // Construtor padrão
    public Ferramenta() {
        this(0, "", "", 0.0);
    }

    // Construtor parametrizado
    public Ferramenta(int id, String nome, String marca, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
    }

    // Métodos getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public double getCustoAquisicao() {
        return custoAquisicao;
    }

    // Métodos setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCustoAquisicao(double custoAquisicao) {
        this.custoAquisicao = custoAquisicao;
    }

    // Representação textual do objeto Ferramenta, que retorna o nome.
    @Override
    public String toString() {
        return this.nome;
    }
}
