package view;

import dao.AmigoDAO;
import dao.EmprestimoDAO;
import dao.FerramentaDAO;
import java.time.LocalDate;
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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de eempréstimos");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        amigoComboBox.setPromptText("Selecione o amigo");
        ferramentasListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dataEmprestimoPicker.setPromptText("Data de empréstimo");

        Button btnAdicionar = new Button("Adicionar empréstimo");
        Button btnDevolver = new Button("Devolver ferramenta");
        Button btnVoltar = new Button("Voltar");

        // Ação do botão Adicionar
        btnAdicionar.setOnAction(e -> {
            Amigo amigo = amigoComboBox.getSelectionModel().getSelectedItem();
            List<Ferramenta> ferramentasSelecionadas = ferramentasListView.getSelectionModel().getSelectedItems();
            LocalDate dataEmprestimo = dataEmprestimoPicker.getValue();

            if (amigo != null && !ferramentasSelecionadas.isEmpty() && dataEmprestimo != null) {
                // Verificar se o amigo já possui empréstimos ativos
                if (emprestimoDAO.amigoTemEmprestimosAtivos(amigo.getId())) {
                    mostrarMensagem("Este amigo já possui um empréstimo ativo e não pode realizar um novo empréstimo.");
                    return; // Impede a execução do restante do código
                }

                // Configurar o empréstimo
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setAmigo_id(amigo.getId());
                emprestimo.setDataEmprestimo(dataEmprestimo);

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

        // Ação do botão Devolver
        btnDevolver.setOnAction(e -> {
            String selectedItem = emprestimosListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int idEmprestimo = Integer.parseInt(selectedItem.split(" - ")[0]);
                emprestimoDAO.devolverEmprestimo(idEmprestimo); // Marca a data de devolução no banco
                atualizarListaEmprestimos();
                mostrarMensagem("Empréstimo devolvido com sucesso!");
            } else {
                mostrarMensagem("Selecione um empréstimo para devolver.");
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
                btnAdicionar, btnDevolver, btnVoltar,
                new Label("Empréstimos Ativos:"), emprestimosListView
        );

        Scene scene = new Scene(layout, 450, 600);
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
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimosAtivos(); // Apenas empréstimos sem data de devolução
        System.out.println("Emprestimos ativos: " + emprestimos); // Verifique se a lista está vindo nula ou vazia
        for (Emprestimo emprestimo : emprestimos) {
            Amigo amigo = amigoDAO.buscarPorId(emprestimo.getAmigo_id());
            if (amigo == null) {
                System.out.println("Amigo não encontrado para ID: " + emprestimo.getAmigo_id());
            }
            List<Integer> ferramentasIds = emprestimo.getFerramentasIds();
            if (ferramentasIds == null) {
                System.out.println("Ferramentas não encontradas para empréstimo ID: " + emprestimo.getId());
            }
            List<String> ferramentasNomes = ferramentasIds.stream()
                    .map(id -> {
                        Ferramenta ferramenta = ferramentaDAO.buscarPorId(id);
                        if (ferramenta == null) {
                            System.out.println("Ferramenta não encontrada para ID: " + id);
                        }
                        return ferramenta != null ? ferramenta.getNome() : "N/A";
                    })
                    .collect(Collectors.toList());
            emprestimosListView.getItems().add(
                    emprestimo.getId() + " - " + (amigo != null ? amigo.getNome() : "Amigo não encontrado")
                    + " - " + String.join(", ", ferramentasNomes)
                    + " - Empréstimo: " + emprestimo.getDataEmprestimo()
            );
        }
    }

    private void limparCampos() {
        amigoComboBox.getSelectionModel().clearSelection();
        ferramentasListView.getSelectionModel().clearSelection();
        dataEmprestimoPicker.setValue(null);
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
