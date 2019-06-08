/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
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

    public ProgressIndicator progressIndicator;
    @FXML private CustomTextField tfLogin;
    @FXML private CustomPasswordField tfPassword;
    @FXML private Label labelErrors;

    private String lastLogin = "";
    private int lvlAccess = 0, rootLvl = 0;
    private String id = "";

    private CreateGui createGui = new CreateGui();
    String errrors = "", TAG = "Controller";
    long timeStart;

    private Stage stage;


    private static Logger logger = LogManager.getLogger();




    /**
     * Initialization add button of clear in the textfield
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        timeStart = System.nanoTime();
        logger.info("timeStart = " + timeStart);
        progressIndicator.setVisible(false);


        //progressIndicator.setVisible(false);

        SetupClearButtonField.SetupClearButtonField(tfLogin);
        SetupClearButtonField.SetupClearButtonField(tfPassword);

        createNewFile();
        labelErrors.setAlignment(Pos.CENTER);

    }

    private void createNewFile(){
        File file = new File("address.txt");
        try {
            if(file.createNewFile()){
                System.out.println("file.txt файл создан в корневой директории проекта");
                try(FileWriter writer = new FileWriter("address.txt",
                        false))
                {
                    // запись всей строки
                    writer.write("jdbc:mysql://127.0.0.1:3306/office_datas");

                    writer.flush();
                }
                catch(IOException ex){
                    logger.error(ex.getMessage());
                }
            }else System.out.println("file.txt файл уже существует в корневой " +
                    "директории проекта");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }



    }

    /**
     * Checking input information
     * @param actionEvent
     * @throws Exception
     */
    public void logIn(ActionEvent actionEvent) throws Exception {

        if (tfLogin.getText().isEmpty() || tfPassword.getText().isEmpty()) {
            logger.info("Не введен логин или пароль!");
            labelErrors.setText("Не введен логин или пароль!");
            return;
        } else labelErrors.setText("");

        DataSource.clearAddress();
        Connection connection = DataSource.getConnection();
        if (connection == null){
            logger.info("connection == null - 3");
            CreateGui createGui = new CreateGui();
            try {
                createGui.createErrorGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }else {
            connection.close();
        }
        progressIndicator.setVisible(true);
        logger.info("logIn");
        //progressIndicator.setVisible(true);
        Thread thread = new Thread(new SimpleThread(actionEvent));
        thread.start();
        logger.info("finishLogin");

    }

    /**
     * Go to the new GUI
     * @param bool
     * @param actionEvent
     */
    private void goToNext(Boolean bool, ActionEvent actionEvent) {
        logger.info("goToNext");
        if (bool){
            updateLastLogin();
            switch (lvlAccess){
                case 0:
                    Platform.runLater(() -> {
                        try {
                            progressIndicator.setVisible(false);

                            createGui.goToSectionList(lastLogin, rootLvl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                break;
                case 1:
                    Platform.runLater(() -> {
                        try {
                            progressIndicator.setVisible(false);
                            createGui.goToListStaff(lastLogin, rootLvl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                break;
            }
            Platform.runLater(() -> closeWindow(actionEvent));

        }else {
            Platform.runLater(() -> {
                labelErrors.setText("Неправильный логин или пароль!");
                progressIndicator.setVisible(false);
            });
            logger.info("Неправильный логин или пароль!");

        }

    }



    /**
     * Close window
     * @param actionEvent
     */
    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        logger.info("closeWindowNow");
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }



    public void setMainStage(Stage mainStage){
       /* mainStage.setOnCloseRequest(we -> logger.info("Stage is closing"));
        mainStage.setOnHiding(event -> logger.info("Stage is Hiding"));
        mainStage.setOnShowing(event -> logger.info("Stage is Showing"));
        mainStage.setOnShown(event -> logger.info("Stage is Shown"));*/

    }

    public Boolean sendQuery(String login, String passw) throws IOException {

        logger.info("sendQuery");
        PreparedStatement stmt = null;
        Connection connection = null;

        try {
         connection = DataSource.getConnection();
         connection.setAutoCommit(false);
         stmt = connection.prepareStatement("SELECT * FROM root_admins WHERE login = ? && passw = ?");
         stmt.setString(1, login);
         stmt.setString(2, passw);

         ResultSet resultSet = stmt.executeQuery();

         if (!resultSet.first()) {
             logger.info("!resultSet.first()");
             return false;
         }

         if (resultSet.first()) {
             String str = "id = " + resultSet.getString("id") +
                     "; lastLogin = " + resultSet.getString("lastLogin")
                     + "; lvlAccess = " + resultSet.getInt("lvlAccess")
                     + "; rootLevel = " + resultSet.getInt("rootLevel");
             System.out.println("info: " + str);

             id = resultSet.getString("id");
             lastLogin = resultSet.getString("lastLogin");
             lvlAccess = resultSet.getInt("lvlAccess");
             rootLvl = resultSet.getInt("rootLevel");


             connection.commit();
             resultSet.close();
             return true;
         }


         resultSet.close();
         stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(stmt, connection);
        }
        return false;
    }

    private void updateLastLogin(){
        logger.info("updateLastLogin");
        PreparedStatement updateLastLogin = null;
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);


                updateLastLogin = connection.prepareStatement("UPDATE root_admins "+
                        "SET " +
                        "lastLogin = CURRENT_DATE() " +
                        "WHERE id = ?");
                updateLastLogin.setString(1, id);
                updateLastLogin.executeUpdate();

                connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateLastLogin);
        }
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



    class SimpleThread extends Thread {
        ActionEvent event = null;
        public SimpleThread(ActionEvent actionEvent) {
            this.event = actionEvent;
        }
        public void run() {
            logger.info("logIn");
            if (!tfLogin.getText().isEmpty() && !tfPassword.getText().isEmpty()) {
                try {
                    goToNext(sendQuery(tfLogin.getText().trim(),
                            tfPassword.getText().trim()), event);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (isInterrupted()){
                    logger.info("Thread is died");
                }
            }

        }
    }




}
