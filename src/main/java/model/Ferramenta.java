package model;

public class Ferramenta {

    private int id;
    private String nome;
    private String marca;
    private double custoAquisicao;

    public Ferramenta() {
        this(2, "Machado", "Collins", 25);
    }

    public Ferramenta(int id, String nome, String marca, double custoAquisicao) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.custoAquisicao = custoAquisicao;
    }

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

    public String test() {
        return "ID: " + getId() + ", " + "NOME: " + getNome() + ", " + "Marca: " + getMarca() + ", " + "Custo: " + getCustoAquisicao() + ".";
    }
}
