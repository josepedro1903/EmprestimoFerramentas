package model;

public class Amigo {

    private int id;
    private String nome;
    private String telefone;

    // Construtor padrão
    public Amigo() {
        this(0, "", "");
    }

    // Construtor parametrizado
    public Amigo(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Métodos getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    // Métodos setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Representação textual do objeto Amigo, que retorna o nome
    @Override
    public String toString() {
        return this.nome;
    }

}
