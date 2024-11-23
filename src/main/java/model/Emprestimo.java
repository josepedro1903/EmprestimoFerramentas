package model;

import java.time.LocalDate;
import java.util.List;

public class Emprestimo {

    private int id;
    private int amigo_id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private List<Integer> ferramentasIds;

    // Construtor padrão
    public Emprestimo() {
        this(0, 0, LocalDate.now(), null);
    }

    // Construtor parametrizado
    public Emprestimo(int id, int amigo_id, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.amigo_id = amigo_id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    // Métodos getters
    public int getId() {
        return id;
    }

    public int getAmigo_id() {
        return amigo_id;
    }

    public List<Integer> getFerramentasIds() {
        return ferramentasIds;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    // Métodos setters
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setAmigo_id(int amigo_id) {
        this.amigo_id = amigo_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setFerramentasIds(List<Integer> ferramentasIds) {
        this.ferramentasIds = ferramentasIds;
    }

    // Representação textual do objeto Emprestimo
    @Override
    public String toString() {
        return "Emprestimo{"
                + "id=" + id
                + ", amigo_id=" + amigo_id
                + ", dataEmprestimo=" + dataEmprestimo
                + ", dataDevolucao=" + dataDevolucao
                + ", ferramentasIds=" + ferramentasIds
                + '}';
    }
}
