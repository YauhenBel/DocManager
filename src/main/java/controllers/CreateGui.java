package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class CreateGui {
    private static final String FXML_STAFF = "/layouts/ListStaff.fxml";
    private static final String FXML_SECTION = "/layouts/ListSection.fxml";
    private static final String FXML_SETTING = "/layouts/Setting.fxml";
    private static final String FXML_ABOUT_PROGRAM = "/layouts/AboutProgram.fxml";
    private static final String FXML_GUI_ERRORS = "/layouts/ForWatchError.fxml";

    private static Logger logger = LogManager.getLogger();



    public void createGUIForWatchError(String errors) throws IOException {
        logger.info("createGUIForWatchError");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_GUI_ERRORS));
        Parent root = fxmlLoader.load();
        ForWatchError forWatchError = fxmlLoader.getController();
        forWatchError.setErrors(errors);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Ошибка");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }


    public void goToListStaff(String lastLogin) throws IOException {
        logger.info("goToListStaff");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_STAFF));
        Parent root = fxmlLoader.load();
        ListStaff listStaff = fxmlLoader.getController();
        Stage primaryStage = new Stage();
        listStaff.setMainStage(primaryStage, lastLogin);
        primaryStage.setTitle("Персонал");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }

    public void goToSectionList() throws IOException {
        logger.info("goToSectionList");
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

    public void goToSetting() throws IOException {
        logger.info("goToSetting");
        Parent root = FXMLLoader.load(getClass().getResource(FXML_SETTING));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Настройки");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }


    public void aboutProgram() throws IOException {
        logger.info("aboutProgram");
        Parent root = FXMLLoader.load(getClass().getResource(FXML_ABOUT_PROGRAM));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("О программе");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }


}
