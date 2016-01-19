package com.interpretador.gui;

import com.interpretador.app.InterpretadorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiRenderController c = fxmlLoader.getController();

        primaryStage.setTitle("Interpretador Protótipo");
        Scene scene = new Scene(root, 400, 510);
        primaryStage.setScene(scene);
        primaryStage.show();
        new InterpretadorController(c);
    }


    public static void main(String[] args) {
       launch(args);
    }
}
