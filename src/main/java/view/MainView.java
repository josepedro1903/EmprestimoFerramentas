package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Titulo
        Label titulo = new Label("Empréstimo Ferramentas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titulo.setTextFill(Color.BLACK);

        // Botões para escolher a view
        Button btnAmigos = new Button("Gerenciar Amigos");
        Button btnFerramentas = new Button("Gerenciar Ferramentas");
        Button btnEmprestimos = new Button("Gerenciar Empréstimos");

        // Botões estilizados
        btnAmigos.setStyle("-fx-background-color: #00FFFF; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;");
        btnFerramentas.setStyle("-fx-background-color: #00CED1; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;");
        btnEmprestimos.setStyle("-fx-background-color: #00CED3; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px;");

        // Ação do botão de Amigos
        btnAmigos.setOnAction(e -> {

            new AmigoView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        // Ação do botão de Ferramentas
        btnFerramentas.setOnAction(e -> {

            new FerramentaView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        btnEmprestimos.setOnAction(e -> {

            new EmprestimoView(primaryStage).start(new Stage());
            primaryStage.hide();
        });

        // Adiciona os botões e o TITULO ao layout 
        layout.getChildren().addAll(titulo, btnAmigos, btnFerramentas, btnEmprestimos);

        // Configura a cena e exibe
        Scene scene = new Scene(layout, 450, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
