package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListChildren;
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
import objects.Child;
import objects.RZO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddRZO implements Initializable {
    @FXML public Button btnSaveRZO;
    @FXML private CustomTextField tfPercent;
    @FXML private CustomTextField tfOrderNumber;
    @FXML private CustomTextField tfProphesy;
    @FXML private DatePicker dpFromTime;
    @FXML private DatePicker dpToTime;
    @FXML private DatePicker dpOrderFromTime;
    @FXML private Label labelError;

    private CollectionListRZO collectionListRZO;

    private RZO rzo;

    private int idStaff, howButtonPressed;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfPercent);
        SetupClearButtonField.SetupClearButtonField(tfOrderNumber);
        SetupClearButtonField.SetupClearButtonField(tfProphesy);
    }

    public void setRZO(CollectionListRZO collectionListRZO,
                         RZO rzo, int rootLvl){
        logger.info("setRZO - 1");
        if (rootLvl == 2) btnSaveRZO.setDisable(true);
        else btnSaveRZO.setDisable(true);
        this.collectionListRZO = collectionListRZO;
        this.rzo = rzo;
        tfPercent.setText(rzo.getPercent());
        tfProphesy.setText(rzo.getProff());
        if (rzo.getDateStart() == null) {
            dpFromTime.setValue(null);
        }else {
            dpFromTime.setValue(LocalDate.parse(returnDateForViews(rzo.getDateStart())));
        }
        if (rzo.getDateFinish() == null) {
            dpToTime.setValue(null);
        }else {
            dpToTime.setValue(LocalDate.parse(returnDateForViews(rzo.getDateFinish())));
        }
        if (rzo.getDateOrder() == null) {
            dpOrderFromTime.setValue(null);
        }else {
            dpOrderFromTime.setValue(LocalDate.parse(returnDateForViews(rzo.getDateOrder())));
        }
        tfOrderNumber.setText(rzo.getNumOrder());
        howButtonPressed = 0;
    }

    public void setRZO(CollectionListRZO collectionListRZO,
                         int idStaff){
        logger.info("setRZO - 0");
        this.collectionListRZO = collectionListRZO;
        this.idStaff = idStaff;
        this.rzo = new RZO();
        tfPercent.clear();
        tfProphesy.clear();
        dpFromTime.setValue(null);
        dpToTime.setValue(null);
        dpOrderFromTime.setValue(null);
        tfOrderNumber.clear();
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
            case "btnSaveRZO":
                logger.info("btnSaveRZO");
                if (howButtonPressed == 0){
                    logger.info("updateRZO");
                    //обновляем данные
                    if (checkValue()){
                        getDataFromForm();
                        collectionListRZO.updateRZO(rzo);
                    }

                    return;
                }
                if (howButtonPressed == 1){
                    //записываем новые данные
                    if (checkValue()){
                        logger.info("insertNewRZO");
                        getDataFromForm();
                        collectionListRZO.insertNewRZO(rzo, idStaff);
                        howButtonPressed = 0;
                    }
                    return;
                }

                break;
            case "btnCloseRZO":
                logger.info("btnCloseRZO");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }

    private boolean checkValue(){
        logger.info("checkValue");
        if (tfProphesy.getText().isEmpty()
                && tfPercent.getText().isEmpty()
                && tfOrderNumber.getText().isEmpty()) {
            labelError.setText("Все поля пустые." +
                    " Заполните хотя бы одно поле.");
            return false;
        }
        return true;
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        String date = null, date1 = null, date2 = null;

        if (dpFromTime.getValue() != null){
            date = returnDate(dpFromTime.getValue().toString());
        }

        if (dpToTime.getValue() != null){
            date1 = returnDate(dpToTime.getValue().toString());
        }

        if (dpOrderFromTime.getValue() != null){
            date2 = returnDate(dpOrderFromTime.getValue().toString());
        }

        rzo.setProff(tfProphesy.getText());
        rzo.setDateStart(date);
        rzo.setDateFinish(date1);
        rzo.setDateOrder(date2);
        rzo.setNumOrder(tfOrderNumber.getText());
        rzo.setPercent(tfPercent.getText());
    }

    private String returnDate(String date){
        logger.info("returnDate");
        String[] dates;
        if (date != null){
            dates = date.split("-");
            return dates[2] + "." + dates[1] + "." + dates[0];
        }
        return  null;
    }

    private String returnDateForViews(String date){
        logger.info("returnDateForViews");
        String[] dates;
        if (date != null){

            dates = date.split("\\.");
            System.out.println(dates[2] + "-" + dates[1] + "-" + dates[0]);
            return dates[2] + "-" + dates[1] + "-" + dates[0];
        }
        return  null;
    }





}
