package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListParthnership;
import interfaces.CollectionListRZO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Parthneriship;
import objects.RZO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddParthnership implements Initializable {
    @FXML public CustomTextField tfPosition;
    @FXML public DatePicker dpFromTime;
    @FXML public DatePicker dpToTime;
    @FXML public CheckBox checkInside;
    @FXML public CheckBox checkOutside;
    @FXML private Label labelError;

    private CollectionListParthnership collectionListParthnership;
    private Parthneriship parthneriship;

    private int idStaff, howButtonPressed;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfPosition);
    }

    public void setParthnership(CollectionListParthnership collectionListParthnership,
                       Parthneriship parthneriship){
        logger.info("setParthnership - 1");
        this.collectionListParthnership = collectionListParthnership;
        this.parthneriship = parthneriship;
        tfPosition.setText(parthneriship.getPosition());

        if (parthneriship.getdStart() == null){
            dpFromTime.setValue(null);
        }else {
            String[] dateS = parthneriship.getdStart().split("\\.");
            dpFromTime.setValue(LocalDate.parse(dateS[2] + "-" + dateS[1] + "-" + dateS[0]));
        }

        if (parthneriship.getdFinish() == null){
            dpToTime.setValue(null);
        }else {
            String[] dateS1 = parthneriship.getdFinish().split("\\.");
            dpToTime.setValue(LocalDate.parse(dateS1[2] + "-" + dateS1[1] + "-" + dateS1[0]));
        }

        if (parthneriship.getType().equals("0")){
            checkInside.setSelected(true);
            checkOutside.setSelected(false);
        }
        if (parthneriship.getType().equals("1")){
            checkInside.setSelected(false);
            checkOutside.setSelected(true);
        }
        howButtonPressed = 0;
    }

    public void setParthnership(CollectionListParthnership collectionListParthnership,
                       int idStaff){
        logger.info("setParthnership - 0");
        this.collectionListParthnership = collectionListParthnership;
        this.idStaff = idStaff;
        this.parthneriship = new Parthneriship();
        tfPosition.clear();
        dpFromTime.setValue(null);
        dpToTime.setValue(null);
        checkInside.setSelected(false);
        checkOutside.setSelected(false);
        howButtonPressed = 1;
    }

    public void actionBtnPress(ActionEvent actionEvent) {
        logger.info("actionBtnPress");
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSaveParthnership":
                logger.info("btnSaveParthnership");
                if (howButtonPressed == 0){
                    logger.info("btnSaveParthnership - 0");
                    //обновляем данные
                        getDataFromForm();
                        collectionListParthnership.updateParthnership(parthneriship);
                    return;
                }
                if (howButtonPressed == 1){
                    logger.info("btnSaveParthnership - 1");
                    //записываем новые данные
                        getDataFromForm();
                        collectionListParthnership.insertNewParthnership(parthneriship, idStaff);
                        howButtonPressed = 0;
                    return;
                }

                break;
            case "btnCloseParthnership":
                logger.info("btnCloseParthnership");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        String[] dateS, dateS1;


        parthneriship.setPosition(tfPosition.getText());

        if (dpFromTime.getValue() == null){
            parthneriship.setdStart(null);
        }else {
            dateS = dpFromTime.getValue().toString().split("-");
            parthneriship.setdStart(dateS[2] + "." + dateS[1] + "." + dateS[0]);

        }

        if (dpToTime.getValue() == null){
            parthneriship.setdFinish(null);
        }else {
            dateS1 = dpToTime.getValue().toString().split("-");
            parthneriship.setdFinish(dateS1[2] + "." + dateS1[1] + "." + dateS1[0]);
        }

        if (checkInside.isSelected()) {
            parthneriship.setType("0");
            parthneriship.setType1("Внутреннее");
        }
        if (checkOutside.isSelected()){
            parthneriship.setType("1");
            parthneriship.setType1("Внешнее");
        }
        if (!checkInside.isSelected() && !checkOutside.isSelected()) {
            parthneriship.setType("2");
            parthneriship.setType1("Не отмечено");
        }
    }

    public void checkAction(ActionEvent actionEvent) {
        logger.info("checkAction");
        Object object = actionEvent.getSource();
        if (!(object instanceof CheckBox)){
            return;
        }
        CheckBox checkBox = (CheckBox) object;
        switch (checkBox.getId()) {
            case "checkInside":
                checkInside.setSelected(true);
                checkOutside.setSelected(false);
                break;
            case "checkOutside":
                checkInside.setSelected(false);
                checkOutside.setSelected(true);
                break;
        }
    }
}
