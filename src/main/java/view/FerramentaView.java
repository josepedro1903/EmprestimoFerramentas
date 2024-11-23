package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import dao.FerramentaDAO;
import model.Ferramenta;

import java.util.List;

public class FerramentaView extends Application {

    private FerramentaDAO ferramentasDAO = new FerramentaDAO();
    private ListView<String> listView = new ListView<>();
    private TextField nomeField = new TextField();
    private TextField marcaField = new TextField();
    private TextField custoAquisicaoField = new TextField();
    private Ferramenta ferramentaSelecionada = null;
    private Stage mainStage; // Referência à MainView

    // Construtor para passar o mainStage
    public FerramentaView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerenciamento de ferramentas");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Campos de entrada
        nomeField.setPromptText("Nome");
        marcaField.setPromptText("Marca");
        custoAquisicaoField.setPromptText("Custo de aquisição");

        // Botões
        Button btnAdicionar = new Button("Adicionar ferramenta");
        Button btnAtualizar = new Button("Atualizar lista");
        Button btnEditar = new Button("Editar ferramenta");
        Button btnExcluir = new Button("Excluir ferramenta");
        Button btnVoltar = new Button("Voltar");

        // Ação do botão "Voltar"
        btnVoltar.setOnAction(e -> {
            primaryStage.close(); // Fecha a tela atual (Amigos)
            mainStage.show(); // Mostra novamente a tela principal
        });

        // Ação do botão adicionar
        btnAdicionar.setOnAction(e -> {
            String nome = nomeField.getText();
            String marca = marcaField.getText();
            String custo = custoAquisicaoField.getText();
            if (!nome.isEmpty() && !marca.isEmpty() && !custo.isEmpty()) {
                try {
                    double custoAquisicao = Double.parseDouble(custo);
                    Ferramenta novaFerramenta = new Ferramenta();
                    novaFerramenta.setNome(nome);
                    novaFerramenta.setMarca(marca);
                    novaFerramenta.setCustoAquisicao(custoAquisicao);
                    ferramentasDAO.adicionarFerramenta(novaFerramenta);
                    nomeField.clear();
                    marcaField.clear();
                    custoAquisicaoField.clear();
                    mostrarMensagem("Ferramenta adicionada com sucesso!");
                } catch (NumberFormatException ex) {
                    mostrarMensagem("Custo de aquisição deve ser um número válido.");
                }
            } else {
                mostrarMensagem("Por favor, preencha todos os campos.");
            }
        });

        // Ação do botão atualizar
        btnAtualizar.setOnAction(e -> atualizarListaFerramentas());

        // Ação do botão editar
        btnEditar.setOnAction(e -> {
            if (ferramentaSelecionada != null) {
                String nome = nomeField.getText();
                String marca = marcaField.getText();
                String custo = custoAquisicaoField.getText();
                if (!nome.isEmpty() && !marca.isEmpty() && !custo.isEmpty()) {
                    try {
                        double custoAquisicao = Double.parseDouble(custo);
                        ferramentaSelecionada.setNome(nome);
                        ferramentaSelecionada.setMarca(marca);
                        ferramentaSelecionada.setCustoAquisicao(custoAquisicao);
                        ferramentasDAO.atualizarFerramenta(ferramentaSelecionada);
                        atualizarListaFerramentas();
                        mostrarMensagem("Ferramenta editada com sucesso!");
                        nomeField.clear();
                        marcaField.clear();
                        custoAquisicaoField.clear();
                        ferramentaSelecionada = null;
                    } catch (NumberFormatException ex) {
                        mostrarMensagem("Custo de aquisição deve ser um número válido.");
                    }
                } else {
                    mostrarMensagem("Por favor, preencha todos os campos.");
                }
            } else {
                mostrarMensagem("Selecione uma ferramenta para editar.");
            }
        });

        // Ação do botão excluir
        btnExcluir.setOnAction(e -> {
            if (ferramentaSelecionada != null) {
                ferramentasDAO.deletarFerramenta(ferramentaSelecionada.getId());
                atualizarListaFerramentas();
                mostrarMensagem("Ferramenta excluída com sucesso!");
                nomeField.clear();
                marcaField.clear();
                custoAquisicaoField.clear();
                ferramentaSelecionada = null;
            } else {
                mostrarMensagem("Selecione uma ferramenta para excluir.");
            }
        });

        // Ação ao selecionar uma ferramenta na lista
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int id = Integer.parseInt(selectedItem.split(" - ")[0]);
                ferramentaSelecionada = ferramentasDAO.listarFerramentas().stream()
                        .filter(ferramenta -> ferramenta.getId() == id)
                        .findFirst()
                        .orElse(null);
                if (ferramentaSelecionada != null) {
                    nomeField.setText(ferramentaSelecionada.getNome());
                    marcaField.setText(ferramentaSelecionada.getMarca());
                    custoAquisicaoField.setText(String.valueOf(ferramentaSelecionada.getCustoAquisicao()));
                }
            }
        });

        // Adicionando elementos ao layout
        layout.getChildren().addAll(
                new Label("Nome:"), nomeField,
                new Label("Marca:"), marcaField,
                new Label("Custo de Aquisição:"), custoAquisicaoField,
                btnAdicionar, btnAtualizar, btnEditar, btnExcluir, btnVoltar, listView
        );

        // Configuração da cena e exibição
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Atualiza a lista de ferramentas ao iniciar
        atualizarListaFerramentas();
    }

    private void atualizarListaFerramentas() {
        listView.getItems().clear();
        List<Ferramenta> ferramentas = ferramentasDAO.listarFerramentas();
        for (Ferramenta ferramenta : ferramentas) {
            listView.getItems().add(ferramenta.getId() + " - " + ferramenta.getNome() + " (" + ferramenta.getMarca() + " - R$ " + ferramenta.getCustoAquisicao() + ")");
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
