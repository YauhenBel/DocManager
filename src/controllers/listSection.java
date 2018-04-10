package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class listSection {

    private static String FXMLStaff = "../layouts/ListStaff.fxml";
    private Stage primaryStage;
    private Parent root;

    public void OpenStaffWindow(ActionEvent actionEvent) throws IOException {
        createGui();
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

    private void createGui() throws IOException {
        root = FXMLLoader.load(getClass().getResource(FXMLStaff));
        primaryStage = new Stage();
        primaryStage.setTitle("Персонал");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}
