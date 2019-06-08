package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CreateGui {
    private static final String FXML_STAFF = "/layouts/ListStaff.fxml";
    private static final String FXML_SECTION = "/layouts/ListSection.fxml";
    private static final String FXML_SETTING = "/layouts/Setting.fxml";
    private static final String FXML_ABOUT_PROGRAM = "/layouts/AboutProgram.fxml";
    private static final String FXML_ERROR = "/layouts/ErrorGUI.fxml";
    private static final String LOG_IN = "/layouts/Sample.fxml";
    private double xOffset, yOffset;


    private static Logger logger = LogManager.getLogger();



    public void goToListStaff(String lastLogin, int rootLvl) throws IOException {
        logger.info("goToListStaff");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_STAFF));
        Parent root = fxmlLoader.load();
        ListStaff listStaff = fxmlLoader.getController();
        Stage primaryStage = new Stage();
        listStaff.setMainStage(primaryStage, lastLogin, rootLvl);
        primaryStage.setTitle("Персонал");
        Scene scene = new Scene(root, 1000, 550);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }

    public void goToSectionList(String lastLogin, int rootLvl) throws IOException {
        logger.info("goToSectionList");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_SECTION));
        Parent root = fxmlLoader.load();
        ListSection listSection = fxmlLoader.getController();
        Stage primaryStage = new Stage();
        listSection.setMainStage(lastLogin, rootLvl);
        primaryStage.setTitle("Отделы");
        Scene scene = new Scene(root);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }

    public void goToSetting() throws IOException {
        logger.info("goToSetting");
        Parent root = FXMLLoader.load(getClass().getResource(FXML_SETTING));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Настройки");
        Scene scene = new Scene(root);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }


    public void aboutProgram() throws IOException {
        logger.info("aboutProgram");
        Parent root = FXMLLoader.load(getClass().getResource(FXML_ABOUT_PROGRAM));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("О программе");
        Scene scene = new Scene(root);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }

    public void createErrorGUI() throws IOException {
        logger.info("ErrorGUI");
        Parent root = FXMLLoader.load(getClass().getResource(FXML_ERROR));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Ошибка");
        Scene scene = new Scene(root);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }

    public void backToLogIn() throws IOException {
        logger.info("backToLogIn");
        Parent root = FXMLLoader.load(getClass().getResource(LOG_IN));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Авторизация");
        Scene scene = new Scene(root);
        settingScene(scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }


    private void settingScene(Scene scene, Stage stage){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }



}
