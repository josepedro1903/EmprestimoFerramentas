package model;

//import javax.swing.JOptionPane;

public class Amigo {

    private int id;
    private String nome;
    private String telefone;

    public Amigo() {
        this(1, "Junior", "48991196373");

    }

    // Construtores, getters e setters
    public Amigo(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;

    }

    public void setId(int id) {
        this.id = id;

    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
   /* 
   public void ler() {
      
        setId(id);
        setNome(JOptionPane.showInputDialog("Digite o nome do amigo"));
         setTelefone(JOptionPane.showInputDialog("Digite o telefone do amigo"));
    }
*/
    public String test() {
        return "ID: " + getId() + ", " + "NOME: " + getNome() + ", " + "Telefone: " + getTelefone() + ".";
    }

    
}

