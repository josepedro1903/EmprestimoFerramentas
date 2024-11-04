package model;

//import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class Emprestimo {

    private int id;
    private Amigo amigo;
    private List<Ferramentas> ferramentas;

    /*
    private Date dataEmprestimo;
    private Date dataDevolucao;
     */
    public Emprestimo() {
        this(1, null, null);

    }

    public Emprestimo(int id, Amigo amigo, List<Ferramentas> ferramentas /*Date dataEmprestimo, Date dataDevolucao*/) {
        this.id = id;
        this.amigo = amigo;
        this.ferramentas = ferramentas;
        // this.dataEmprestimo = dataEmprestimo;
        //this.dataDevolucao = dataDevolucao;
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

    // Testar isso depois
    /* public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public Date getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(Date dataDevolucao) { this.dataDevolucao = dataDevolucao; }

     */
    
    
    }

