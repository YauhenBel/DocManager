/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import ConnectionPooling.ConnectionPool;
import interfaces.LogIn;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Processing authorization
 */
public class Controller implements Initializable {

    private static final String FXML_SECTION = "../layouts/ListSection.fxml";

    @FXML private CustomTextField tfLogin;
    @FXML private CustomPasswordField tfPassword;
    @FXML private Label labelErrors;
    LogIn logIn = new LogIn();

    /**
     * Initialization add button of clear in the textfield
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClearButtonField(tfLogin);
        setupClearButtonField(tfPassword);
        /*ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Checking input information
     * @param actionEvent
     * @throws Exception
     */
    public void logIn(ActionEvent actionEvent) throws Exception {
        labelErrors.setAlignment(Pos.CENTER);

        if (!tfLogin.getText().isEmpty()
                && !tfPassword.getText().isEmpty()) {
            goToNext(logIn.sendQuery(tfLogin.getText(), tfPassword.getText()), actionEvent);
        } else if (tfLogin.getText().isEmpty()
                && tfPassword.getText().isEmpty()) {
            labelErrors.setText("Введите логин и пароль!");
        } else if (!tfLogin.getText().isEmpty()
                && tfPassword.getText().isEmpty()) {
            labelErrors.setText("Введите пароль!");
        } else {
            labelErrors.setText("Введите логин!");
        }
    }

    /**
     * Go ti new GUI
     * @param bool
     * @param actionEvent
     * @throws IOException
     */
    private void goToNext(Boolean bool, ActionEvent actionEvent) throws IOException {
        if (bool){
            createGui();
            closeWindow(actionEvent);
        }else {
            labelErrors.setText("Неправильный логин или пароль!");
        }

    }

    /**
     * Close window
     * @param actionEvent
     */
    private void closeWindow(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Add button of clear in the textfield
     * @param object
     */
    private void setupClearButtonField(Object object){
        try {
        if (object instanceof CustomTextField){
            CustomTextField customTextField = (CustomTextField) object;
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        }
        if (object instanceof CustomPasswordField){
            CustomPasswordField customPasswordField = (CustomPasswordField) object;
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customPasswordField, customPasswordField.rightProperty());
        }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**Create new window*/
    private void createGui() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FXML_SECTION));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Отделы");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }
}
