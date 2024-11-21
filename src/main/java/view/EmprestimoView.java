package view;

import dao.AmigoDAO;
import dao.EmprestimoDAO;
import dao.FerramentaDAO;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Amigo;
import model.Emprestimo;
import model.Ferramenta;


public class EmprestimoView extends Application {
    
      private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private AmigoDAO amigoDAO = new AmigoDAO();
    private FerramentaDAO ferramentaDAO = new FerramentaDAO();

    private ComboBox<Amigo> amigoComboBox = new ComboBox<>();
    private ComboBox<Ferramenta> ferramentaComboBox = new ComboBox<>();
    private ListView<String> emprestimosListView = new ListView<>();
    private Stage mainStage;

    public EmprestimoView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de Empréstimos");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        amigoComboBox.setPromptText("Selecione o amigo");
        ferramentaComboBox.setPromptText("Selecione a ferramenta");

        Button btnAdicionar = new Button("Adicionar Empréstimo");
        Button btnExcluir = new Button("Excluir Empréstimo");
        Button btnVoltar = new Button("Voltar");

        // Ação do botão Adicionar
        btnAdicionar.setOnAction(e -> {
            Amigo amigo = amigoComboBox.getSelectionModel().getSelectedItem();
            Ferramenta ferramenta = ferramentaComboBox.getSelectionModel().getSelectedItem();

            if (amigo != null && ferramenta != null) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setAmigo_id(amigo.getId());
                emprestimo.setFerramenta_id(ferramenta.getId());

                emprestimoDAO.adicionarEmprestimo(emprestimo);
                atualizarListaEmprestimos();
                limparCampos();
                mostrarMensagem("Empréstimo adicionado com sucesso!");
            } else {
                mostrarMensagem("Selecione o amigo e a ferramenta antes de adicionar um empréstimo.");
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
                new Label("Ferramenta:"), ferramentaComboBox,
                btnAdicionar, btnExcluir, btnVoltar,
                new Label("Empréstimos:"), emprestimosListView
        );

        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        atualizarListas();
        atualizarListaEmprestimos();
    }

    private void atualizarListas() {
        amigoComboBox.getItems().clear();
        ferramentaComboBox.getItems().clear();
        amigoComboBox.getItems().addAll(amigoDAO.listarAmigos());
        ferramentaComboBox.getItems().addAll(ferramentaDAO.listarFerramentas());
    }

    private void atualizarListaEmprestimos() {
        emprestimosListView.getItems().clear();
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimos();
        for (Emprestimo emprestimo : emprestimos) {
            Amigo amigo = amigoDAO.buscarPorId(emprestimo.getAmigo_id());
            Ferramenta ferramenta = ferramentaDAO.buscarPorId(emprestimo.getFerramenta_id());
            emprestimosListView.getItems().add(
                    emprestimo.getId() + " - " + amigo.getNome() + " - " + ferramenta.getNome()
            );
        }
    }

    private void limparCampos() {
        amigoComboBox.getSelectionModel().clearSelection();
        ferramentaComboBox.getSelectionModel().clearSelection();
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

    
    
