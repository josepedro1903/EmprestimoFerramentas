package model;

import java.time.LocalDate;
import java.util.List;

public class Emprestimo {

    private int id; // ID do empréstimo
    private int amigo_id; // ID do amigo relacionado ao empréstimo
    private LocalDate dataEmprestimo; // Data em que o empréstimo foi realizado
    private LocalDate dataDevolucao; // Data de devolução do empréstimo
    private List<Integer> ferramentasIds; // Lista de IDs de ferramentas associadas a este empréstimo

    // Construtor padrão
    public Emprestimo() {
        this(0, 0, LocalDate.now(), null);
    }

    // Construtor com parâmetros
    public Emprestimo(int id, int amigo_id, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.amigo_id = amigo_id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    // Getters e Setters
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

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public List<Integer> getFerramentasIds() {
        return ferramentasIds;
    }

    public void setFerramentasIds(List<Integer> ferramentasIds) {
        this.ferramentasIds = ferramentasIds;
    }

    // Método para exibir os dados do empréstimo (opcional, útil para debug)
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
