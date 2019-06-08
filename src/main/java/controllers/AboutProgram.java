package controllers;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import objects.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AboutProgram implements Initializable {
    private static Logger logger = LogManager.getLogger();
    public Label labelAboutDeveloper;
    public Label labelAboutProgram;
    private String aboutProgram = "";
    private String aboutDeveloper = "";



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        logger.info("AboutProgram");
        //dowloadInformation();
        labelAboutProgram.setText("Данная программа предназначена для автоматизации работы отдела кадров.");
        labelAboutDeveloper.setText("Разработчик: Ужахов Евгений\n Связь: eugenuzahov@gmail.com");


    }
}
