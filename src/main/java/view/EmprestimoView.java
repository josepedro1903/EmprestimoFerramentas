package view;

import dao.AmigoDAO;
import dao.EmprestimoDAO;
import dao.FerramentaDAO;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Amigo;
import model.Emprestimo;
import model.Ferramenta;
import java.util.stream.Collectors;

public class EmprestimoView extends Application {

    private Stage mainStage;

    public EmprestimoView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private AmigoDAO amigoDAO = new AmigoDAO();
    private FerramentaDAO ferramentaDAO = new FerramentaDAO();

    private ComboBox<Amigo> amigoComboBox = new ComboBox<>();
    private ListView<Ferramenta> ferramentasListView = new ListView<>();
    private ListView<String> emprestimosListView = new ListView<>();
    private DatePicker dataEmprestimoPicker = new DatePicker();
    private DatePicker dataDevolucaoPicker = new DatePicker();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de Empréstimos");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        amigoComboBox.setPromptText("Selecione o amigo");
        ferramentasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dataEmprestimoPicker.setPromptText("Data de Empréstimo");
        dataDevolucaoPicker.setPromptText("Data de Devolução");

        Button btnAdicionar = new Button("Adicionar Empréstimo");
        Button btnExcluir = new Button("Excluir Empréstimo");
        Button btnVoltar = new Button("Voltar");

        // Ação do botão Adicionar
        btnAdicionar.setOnAction(e -> {
            Amigo amigo = amigoComboBox.getSelectionModel().getSelectedItem();
            List<Ferramenta> ferramentasSelecionadas = ferramentasListView.getSelectionModel().getSelectedItems();
            var dataEmprestimo = dataEmprestimoPicker.getValue();
            var dataDevolucao = dataDevolucaoPicker.getValue();

            if (amigo != null && !ferramentasSelecionadas.isEmpty() && dataEmprestimo != null) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setAmigo_id(amigo.getId());
                emprestimo.setDataEmprestimo(dataEmprestimo);
                emprestimo.setDataDevolucao(dataDevolucao);

                // Adiciona o empréstimo e associa as ferramentas selecionadas
                emprestimoDAO.adicionarEmprestimo(
                        emprestimo,
                        ferramentasSelecionadas.stream().map(Ferramenta::getId).collect(Collectors.toList())
                );
                atualizarListaEmprestimos();
                limparCampos();
                mostrarMensagem("Empréstimo adicionado com sucesso!");
            } else {
                mostrarMensagem("Selecione um amigo, ferramentas e uma data de empréstimo.");
            }
        });

        // Ação do botão Excluir
        btnExcluir.setOnAction(e -> {
            String selectedItem = emprestimosListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int idEmprestimo = Integer.parseInt(selectedItem.split(" - ")[0]);
                emprestimoDAO.deletarEmprestimo(idEmprestimo);
                atualizarListaEmprestimos();
                mostrarMensagem("Empréstimo excluído com sucesso!");
            } else {
                mostrarMensagem("Selecione um empréstimo para excluir.");
            }
        });

        // Ação do botão Voltar
        btnVoltar.setOnAction(e -> {
            MainView mainView = new MainView();
            try {
                mainView.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(
                new Label("Amigo:"), amigoComboBox,
                new Label("Ferramentas (selecione múltiplas):"), ferramentasListView,
                new Label("Data de Empréstimo:"), dataEmprestimoPicker,
                new Label("Data de Devolução:"), dataDevolucaoPicker,
                btnAdicionar, btnExcluir, btnVoltar,
                new Label("Empréstimos:"), emprestimosListView
        );

        Scene scene = new Scene(layout, 450, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

        atualizarListas();
        atualizarListaEmprestimos();
    }

    private void atualizarListas() {
        amigoComboBox.getItems().clear();
        ferramentasListView.getItems().clear();
        amigoComboBox.getItems().addAll(amigoDAO.listarAmigos());
        ferramentasListView.getItems().addAll(ferramentaDAO.listarFerramentas());
    }

    private void atualizarListaEmprestimos() {
        emprestimosListView.getItems().clear();
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimos();
        for (Emprestimo emprestimo : emprestimos) {
            Amigo amigo = amigoDAO.buscarPorId(emprestimo.getAmigo_id());
            List<Integer> ferramentasIds = emprestimo.getFerramentasIds();
            List<String> ferramentasNomes = ferramentasIds.stream()
                    .map(id -> ferramentaDAO.buscarPorId(id).getNome())
                    .collect(Collectors.toList());
            emprestimosListView.getItems().add(
                    emprestimo.getId() + " - " + amigo.getNome() + " - " + String.join(", ", ferramentasNomes)
                    + " - Empréstimo: " + emprestimo.getDataEmprestimo()
                    + (emprestimo.getDataDevolucao() != null ? " - Devolução: " + emprestimo.getDataDevolucao() : "")
            );
        }
    }

    private void limparCampos() {
        amigoComboBox.getSelectionModel().clearSelection();
        ferramentasListView.getSelectionModel().clearSelection();
        dataEmprestimoPicker.setValue(null);
        dataDevolucaoPicker.setValue(null);
    }

    private void mostrarMensagem(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
