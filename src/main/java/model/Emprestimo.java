package model;

import java.util.List;

public class Emprestimo {

    private int id;
    private Amigo amigo;
    private List<Ferramentas> ferramentas;

    public Emprestimo() {
        this(1, null, null);

    }

    public Emprestimo(int id, Amigo amigo, List<Ferramentas> ferramentas) {
        this.id = id;
        this.amigo = amigo;
        this.ferramentas = ferramentas;
    }

    public int getId() {
        return id;
    }

    public Amigo getAmigo() {
        return amigo;
    }

    public List<Ferramentas> getFerramentas() {
        return ferramentas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmigo(Amigo amigo) {
        this.amigo = amigo;
    }

    public void setFerramentas(List<Ferramentas> ferramentas) {
        this.ferramentas = ferramentas;
    }

}
