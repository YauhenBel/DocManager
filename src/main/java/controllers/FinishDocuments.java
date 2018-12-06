package controllers;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FinishDocuments {

    public TextArea taFinishDocuments;
    public Label label;

    private static Logger logger = LogManager.getLogger();

    public void setListFinishDocuments(String str, int x){
        logger.info("setListFinishDocuments");
        switch (x){
            case 0:
                label.setText("Контракты");
                break;
            case 1:
                label.setText("Прививки или флюорографии:");
        }

        taFinishDocuments.setText(str);
    }
}
