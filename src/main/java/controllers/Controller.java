/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Processing authorization
 */
public class Controller implements Initializable {

    @FXML private CustomTextField tfLogin;
    @FXML private CustomPasswordField tfPassword;
    @FXML private Label labelErrors;

    private String lastLogin = "";
    private int lvlAccess = 0;

    private CreateGui createGui = new CreateGui();
    String errrors = "";

    private static Logger logger = LogManager.getLogger();


    /**
     * Initialization add button of clear in the textfield
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");

        SetupClearButtonField.SetupClearButtonField(tfLogin);
        SetupClearButtonField.SetupClearButtonField(tfPassword);

        labelErrors.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                try {
                    createGui.createGUIForWatchError("hello");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        createNewFile();
    }

    private void createNewFile(){
        File file = new File("address.txt");
        try {
            if(file.createNewFile()){
                System.out.println("file.txt файл создан в корневой директории проекта");
            }else System.out.println("file.txt файл уже существует в корневой директории проекта");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        try(FileWriter writer = new FileWriter("address.txt", false))
        {
            // запись всей строки
            writer.write("jdbc:mysql://localhost:3306/office_datas");

            writer.flush();
        }
        catch(IOException ex){
            logger.error(ex.getMessage());
        }

    }

    /**
     * Checking input information
     * @param actionEvent
     * @throws Exception
     */
    public void logIn(ActionEvent actionEvent) throws Exception {
        logger.info("logIn");
        labelErrors.setAlignment(Pos.CENTER);

        if (!tfLogin.getText().isEmpty()
                && !tfPassword.getText().isEmpty()) {
            goToNext(sendQuery(tfLogin.getText(), tfPassword.getText()), actionEvent);
        } else if (tfLogin.getText().isEmpty()
                && tfPassword.getText().isEmpty()) {
            logger.info("Введите логин и пароль!");
            labelErrors.setText("Введите логин и пароль!");
        } else if (!tfLogin.getText().isEmpty()
                && tfPassword.getText().isEmpty()) {
            logger.info("Введите пароль!");
            labelErrors.setText("Введите пароль!");
        } else {
            logger.info("Введите логин!");
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
        logger.info("goToNext");
        if (bool){
            switch (lvlAccess){
                case 0:
                    createGui.goToSectionList();
                break;
                case 1:
                    createGui.goToListStaff(lastLogin);
                break;
            }
            closeWindow(actionEvent);
        }else {
            logger.info("Неправильный логин или пароль!");
            labelErrors.setText("Неправильный логин или пароль!");
        }

    }

    /**
     * Close window
     * @param actionEvent
     */
    private void closeWindow(ActionEvent actionEvent) {
        logger.info("closeWindow");
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public Boolean sendQuery(String login, String passw){
        logger.info("sendQuery");
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement("SELECT * FROM `root_admins` WHERE `login`='" + login
                    + "' && `passw`='" + passw+"'");
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.first()) {
                String str = "id = " + resultSet.getString("id") +
                        "; lastLogin = " + resultSet.getString("lastLogin")
                        + "; lvlAccess = " + resultSet.getInt("lvlAccess");
                System.out.println("info: " + str);

                lastLogin = resultSet.getString("lastLogin");
                lvlAccess = resultSet.getInt("lvlAccess");

                resultSet.close();
                return true;
            }
            if (!resultSet.first()) {
                return false;
            }

            resultSet.close();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            labelErrors.setText("Ошибка");

            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(stmt, connection);
        }
        return false;
    }

    public void menuSetting(ActionEvent actionEvent) {
        logger.info("menuSetting");
        try {
            createGui.goToSetting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void menuAboutProgram(ActionEvent actionEvent) {
        logger.info("menuAboutProgram");
        try {
            createGui.aboutProgram();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
