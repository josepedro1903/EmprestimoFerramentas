package view;

import dao.AmigoDAO;
import dao.EmprestimoDAO;
import dao.FerramentaDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Amigo;
import model.Emprestimo;
import model.Ferramenta;
import java.util.List;

public class RelatorioView extends Application {

    private AmigoDAO amigoDAO = new AmigoDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private FerramentaDAO ferramentaDAO = new FerramentaDAO();
    private Stage mainStage;

    public RelatorioView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatórios");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Botões para os relatórios
        Button btnRelatorioFerramentas = new Button("Custo das ferramentas");
        Button btnRelatorioEmprestimosAtivos = new Button("Empréstimos ativos");
        Button btnRelatorioEmprestimosRealizados = new Button("Empréstimos realizados");
        Button btnRelatorioAmigosPendentes = new Button("Amigos com pendências");
        Button btnVoltar = new Button("Voltar");

        // Área de texto para exibir os relatórios
        TextArea relatorioArea = new TextArea();
        relatorioArea.setEditable(false);

        // Ação do botão Relatório de Ferramentas
        btnRelatorioFerramentas.setOnAction(e -> {
            List<Ferramenta> ferramentas = ferramentaDAO.listarFerramentas();
            StringBuilder relatorio = new StringBuilder("");
            double totalGasto = 0.0;

            for (Ferramenta ferramenta : ferramentas) {
                relatorio.append("ID: ").append(ferramenta.getId())
                        .append(", Nome: ").append(ferramenta.getNome())
                        .append(", Marca: ").append(ferramenta.getMarca())
                        .append(", Custo: R$ ").append(String.format("%.2f", ferramenta.getCustoAquisicao()))
                        .append("\n");

                // Soma os custos das ferramentas
                totalGasto += ferramenta.getCustoAquisicao();
            }

            // Adiciona o total gasto ao final do relatório
            relatorio.append("\nTotal gasto: R$ ").append(String.format("%.2f", totalGasto));

            relatorioArea.setText(relatorio.toString());
        });

        // Ação do botão Relatório de Empréstimos Ativos
        btnRelatorioEmprestimosAtivos.setOnAction(e -> {
            List<Emprestimo> ativos = emprestimoDAO.listarEmprestimosAtivos();
            StringBuilder relatorio = new StringBuilder("");
            for (Emprestimo emprestimo : ativos) {
                Amigo amigo = amigoDAO.buscarPorId(emprestimo.getAmigo_id());
                relatorio.append("Empréstimo ID: ").append(emprestimo.getId())
                        .append(", Amigo: ").append(amigo.getNome())
                        .append(", Data de Empréstimo: ").append(emprestimo.getDataEmprestimo())
                        .append("\n");
            }
            relatorioArea.setText(relatorio.toString());
        });

        // Ação do botão Relatório de Empréstimos Realizados
        btnRelatorioEmprestimosRealizados.setOnAction(e -> {
            List<Emprestimo> realizados = emprestimoDAO.listarEmprestimos();
            StringBuilder relatorio = new StringBuilder("");
            for (Emprestimo emprestimo : realizados) {
                Amigo amigo = amigoDAO.buscarPorId(emprestimo.getAmigo_id());
                relatorio.append("Empréstimo ID: ").append(emprestimo.getId())
                        .append(", Amigo: ").append(amigo.getNome())
                        .append(", Data de Empréstimo: ").append(emprestimo.getDataEmprestimo())
                        .append(", Data de Devolução: ").append(emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao() : "Não devolvido")
                        .append("\n");
            }
            relatorioArea.setText(relatorio.toString());
        });

        // Ação do botão Relatório de Amigos com Pendências
        btnRelatorioAmigosPendentes.setOnAction(e -> {
            List<Amigo> pendentes = amigoDAO.amigosComPendencias();
            StringBuilder relatorio = new StringBuilder("");
            for (Amigo amigo : pendentes) {
                relatorio.append("Amigo ID: ").append(amigo.getId())
                        .append(", Nome: ").append(amigo.getNome())
                        .append("\n");
            }
            relatorioArea.setText(relatorio.toString());
        });

        // Ação do botão Voltar
        btnVoltar.setOnAction(e -> {
            mainStage.show();
            primaryStage.close();
        });

        layout.getChildren().addAll(
                btnRelatorioFerramentas,
                btnRelatorioEmprestimosAtivos,
                btnRelatorioEmprestimosRealizados,
                btnRelatorioAmigosPendentes,
                relatorioArea,
                btnVoltar
        );

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
