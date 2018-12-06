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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 *Run window the selected option
 */

public class ListSection {

    CreateGui createGui = new CreateGui();
    String lastLogin = "";

    private static Logger logger = LogManager.getLogger();

    public ListSection() throws IOException {
    }

    public void setLastLogin(String lastLogin) {
        logger.info("setLastLogin");
        this.lastLogin = lastLogin;
    }

    public void openStaffWindow(ActionEvent actionEvent) throws IOException {
        logger.info("openStaffWindow");
        createGui.goToListStaff(lastLogin);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }


}
