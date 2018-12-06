package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ForWatchError implements Initializable {

    public TextArea taErrors;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");

    }

    void setErrors(String string){
        logger.info("setErrors");
        System.out.println("string = " + string);

        taErrors.setText(string);
    }


}
