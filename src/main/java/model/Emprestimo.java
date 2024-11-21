package model;

import java.util.List;

public class Emprestimo {

    private int id;
    private int amigo_id;
    private int ferramenta_id;

    public Emprestimo() {
        this(1, 0, 0);

    }

    public Emprestimo(int id,int amigo_id, int ferramenta_id) {
        this.id = id;
        this.amigo_id = amigo_id;
        this.ferramenta_id = ferramenta_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmigo_id() {
        return amigo_id;
    }

    public void setAmigo_id(int amigo_id) {
        this.amigo_id = amigo_id;
    }

    public int getFerramenta_id() {
        return ferramenta_id;
    }

    public void setFerramenta_id(int ferramenta_id) {
        this.ferramenta_id = ferramenta_id;
    }

    
    
 

}
