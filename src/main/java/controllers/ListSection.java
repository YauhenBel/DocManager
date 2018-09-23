/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *Run window the selected option
 */

public class ListSection {

    private static final String FXML_STAFF = "../layouts/ListStaff.fxml";

    public void openStaffWindow(ActionEvent actionEvent) throws IOException {
        createGui();
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

    private void createGui() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_STAFF));
        Parent root = fxmlLoader.load();
        ListStaff listStaff = fxmlLoader.getController();

        Stage primaryStage = new Stage();
        listStaff.setMainStage(primaryStage);
        primaryStage.setTitle("Персонал");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}
