package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tela Principal");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Botões para escolher a view
        Button btnAmigos = new Button("Gerenciar Amigos");
        Button btnFerramentas = new Button("Gerenciar Ferramentas");

        // Ação do botão de Amigos
        btnAmigos.setOnAction(e -> {
            // Chama a View de Amigos
            new AmigoView(primaryStage).start(new Stage());
            primaryStage.hide(); // Oculta a tela principal
        });

        // Ação do botão de Ferramentas
        btnFerramentas.setOnAction(e -> {
            // Chama a View de Ferramentas
            new FerramentaView(primaryStage).start(new Stage());
            primaryStage.hide(); // Oculta a tela principal
        });

        // Adiciona os botões ao layout
        layout.getChildren().addAll(btnAmigos, btnFerramentas);

        // Configura a cena e exibe
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
