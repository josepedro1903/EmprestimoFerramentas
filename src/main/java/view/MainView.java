package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Empréstimo Ferramentas");

        // Layout principal
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Título
        Label titulo = new Label("Empréstimo de ferramentas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.BLACK);

        // Botões
        Button btnAmigos = new Button("Gerenciar Amigos");
        Button btnFerramentas = new Button("Gerenciar Ferramentas");
        Button btnEmprestimos = new Button("Gerenciar Empréstimos");
        Button btnRelatorios = new Button("Relatórios");

        // Configurações de estilo dos botões
        String buttonStyle = "-fx-background-color: #87CEEB; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;";
        btnAmigos.setStyle(buttonStyle);
        btnFerramentas.setStyle(buttonStyle);
        btnEmprestimos.setStyle(buttonStyle);
        btnRelatorios.setStyle(buttonStyle);

        // Ajuste de largura dos botões para evitar corte do texto
        btnAmigos.setPrefWidth(200);
        btnFerramentas.setPrefWidth(200);
        btnEmprestimos.setPrefWidth(200);
        btnRelatorios.setPrefWidth(200);

        // Ação dos botões
        btnAmigos.setOnAction(e -> {
            new AmigoView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        btnFerramentas.setOnAction(e -> {
            new FerramentaView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        btnEmprestimos.setOnAction(e -> {
            new EmprestimoView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        btnRelatorios.setOnAction(e -> {
            new RelatorioView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        // Organizar os botões em pares (2 por linha)
        HBox linha1 = new HBox(20, btnAmigos, btnFerramentas);
        HBox linha2 = new HBox(20, btnEmprestimos, btnRelatorios);
        linha1.setAlignment(Pos.CENTER);
        linha2.setAlignment(Pos.CENTER);

        // Adiciona o título e os botões ao layout principal
        layout.getChildren().addAll(titulo, linha1, linha2);

        // Configura a cena e exibe
        Scene scene = new Scene(layout, 500, 300); // Ajuste na largura da cena
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
