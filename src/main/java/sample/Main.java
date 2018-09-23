/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *Start program and window of authorization
 */
public class Main extends Application {

    private static final String FXML_SAMPLE = "../layouts/Sample.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(FXML_SAMPLE));
        primaryStage.setTitle("Авторизация");
        Scene scene = new Scene(root, 220, 180);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(220);
        primaryStage.setMaxHeight(180);
    }

    public static void main(String[] args) { launch(args); }
}
