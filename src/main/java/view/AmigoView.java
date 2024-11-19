package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import dao.AmigoDAO;
import model.Amigo;

import java.util.List;

public class AmigoView extends Application {

    private AmigoDAO amigoDAO = new AmigoDAO();
    private ListView<String> listView = new ListView<>();
    private TextField nomeField = new TextField();
    private TextField telefoneField = new TextField();
    private Amigo amigoSelecionado = null;
    private Stage mainStage; // Referência à MainView

    // Construtor para passar o mainStage
    public AmigoView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de Amigos");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Campos de entrada
        nomeField.setPromptText("Nome");
        telefoneField.setPromptText("Telefone");

        // Botões
        Button btnAdicionar = new Button("Adicionar Amigo");
        Button btnAtualizar = new Button("Atualizar Lista");
        Button btnEditar = new Button("Editar Amigo");
        Button btnExcluir = new Button("Excluir Amigo");
        Button btnVoltar = new Button("Voltar");

        // Ação do botão "Voltar"
        btnVoltar.setOnAction(e -> {
            primaryStage.close(); // Fecha a tela atual (Amigos)
            mainStage.show(); // Mostra novamente a tela principal
        });

        // Ação do botão adicionar
        btnAdicionar.setOnAction(e -> {
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            if (!nome.isEmpty() && !telefone.isEmpty()) {
                Amigo novoAmigo = new Amigo();
                novoAmigo.setNome(nome);
                novoAmigo.setTelefone(telefone);
                amigoDAO.adicionarAmigo(novoAmigo);
                nomeField.clear();
                telefoneField.clear();
                atualizarListaAmigos();
                mostrarMensagem("Amigo adicionado com sucesso!");
            } else {
                mostrarMensagem("Por favor, preencha todos os campos.");
            }
        });

        // Ação do botão atualizar
        btnAtualizar.setOnAction(e -> atualizarListaAmigos());

        // Ação do botão editar
        btnEditar.setOnAction(e -> {
            if (amigoSelecionado != null) {
                String nome = nomeField.getText();
                String telefone = telefoneField.getText();
                if (!nome.isEmpty() && !telefone.isEmpty()) {
                    amigoSelecionado.setNome(nome);
                    amigoSelecionado.setTelefone(telefone);
                    amigoDAO.atualizarAmigo(amigoSelecionado);
                    atualizarListaAmigos();
                    mostrarMensagem("Amigo editado com sucesso!");
                    nomeField.clear();
                    telefoneField.clear();
                    amigoSelecionado = null;
                } else {
                    mostrarMensagem("Por favor, preencha todos os campos.");
                }
            } else {
                mostrarMensagem("Selecione um amigo para editar.");
            }
        });

        // Ação do botão excluir
        btnExcluir.setOnAction(e -> {
            if (amigoSelecionado != null) {
                amigoDAO.deletarAmigo(amigoSelecionado.getId());
                atualizarListaAmigos();
                mostrarMensagem("Amigo excluído com sucesso!");
                nomeField.clear();
                telefoneField.clear();
                amigoSelecionado = null;
            } else {
                mostrarMensagem("Selecione um amigo para excluir.");
            }
        });

        // Ação ao selecionar um amigo na lista
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int id = Integer.parseInt(selectedItem.split(" - ")[0]);
                amigoSelecionado = amigoDAO.listarAmigos().stream()
                        .filter(amigo -> amigo.getId() == id)
                        .findFirst()
                        .orElse(null);
                if (amigoSelecionado != null) {
                    nomeField.setText(amigoSelecionado.getNome());
                    telefoneField.setText(amigoSelecionado.getTelefone());
                }
            }
        });

        // Adicionando elementos ao layout
        layout.getChildren().addAll(new Label("Nome:"), nomeField, new Label("Telefone:"), telefoneField, btnAdicionar, btnAtualizar, btnEditar, btnExcluir, btnVoltar, listView);

        // Configuração da cena e exibição
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Atualiza a lista de amigos ao iniciar
        atualizarListaAmigos();
    }

    private void atualizarListaAmigos() {
        listView.getItems().clear();
        List<Amigo> amigos = amigoDAO.listarAmigos();
        for (Amigo amigo : amigos) {
            listView.getItems().add(amigo.getId() + " - " + amigo.getNome() + " (" + amigo.getTelefone() + ")");
        }
    }

    private void mostrarMensagem(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
