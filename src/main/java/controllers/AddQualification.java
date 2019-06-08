package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListQualifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.Qualification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddQualification implements Initializable {
    @FXML public Label labelError;
    @FXML public CustomTextField tfWhat;
    @FXML public DatePicker dpDate;
    @FXML public CustomTextField tfCol;
    @FXML public TextArea taTheme;
    @FXML public Button btnSaveQualification;

    private CollectionListQualifications mCollectionListQualifications;
    private Qualification mQualification;

    private int howButtonPressed, idStaff;

    private static Logger logger = LogManager.getLogger();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfWhat);
        SetupClearButtonField.SetupClearButtonField(tfCol);
    }

    public void setQualification(CollectionListQualifications collectionListQualifications,
                             Qualification qualification, int rootLvl){
        logger.info("setQualification - 1");
        if (rootLvl == 2) btnSaveQualification.setDisable(true);
        else btnSaveQualification.setDisable(false);
        mCollectionListQualifications = collectionListQualifications;
        this.mQualification = qualification;
        tfWhat.setText(mQualification.getmWhat());
        if (mQualification.getmDate() == null){
            dpDate.setValue(null);
        }else {
            String[] dateS = qualification.getmDate().split("\\.");
            dpDate.setValue(LocalDate.parse(dateS[2] + "-" + dateS[1] + "-" + dateS[0]));
        }
        tfCol.setText(mQualification.getmHours());
        taTheme.setText(mQualification.getmTheme());
        howButtonPressed = 0;

    }

    public void setQualification(CollectionListQualifications collectionListQualifications,
                             int idStaff){
        logger.info("setQualification - 0");
        mCollectionListQualifications = collectionListQualifications;

        this.idStaff = idStaff;
        this.mQualification = new Qualification();
        tfWhat.clear();
        tfCol.clear();
        taTheme.clear();
        dpDate.setValue(null);
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
            case "btnSaveQualification":
                logger.info("btnSaveQualification");
                if (howButtonPressed == 0){
                    logger.info("btnSaveQualification - 0");
                    //обновляем данные
                    if (checkValue()){
                        getDataFromForm();
                        mCollectionListQualifications.updateQualification(mQualification);
                    }

                    return;
                }
                if (howButtonPressed == 1){
                    logger.info("btnSaveQualification - 1");
                    //записываем новые данные
                    if (checkValue()){
                        getDataFromForm();
                        mCollectionListQualifications.insertNewQualification(mQualification, idStaff);
                        howButtonPressed = 0;
                    }
                    return;
                }


                break;
            case "btnCloseQualification":
                logger.info("btnCloseQualification");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }
    private boolean checkValue(){
        logger.info("checkValue");
        String regex = "\\d+";
        if (tfWhat.getText().isEmpty()
                && tfCol.getText().isEmpty()
                && taTheme.getText().isEmpty()) {
            labelError.setText("Все поля пустые." +
                    " Заполните хотя бы одно поле.");
            return false;
        }

        if (!tfCol.getText().matches(regex)) {
            labelError.setText("Поле количества часов должно содержать только цифры.");
            return false;
        }



        return true;
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        String date = null;
        if (dpDate.getValue() != null){
            String[] dateS = dpDate.getValue().toString().split("-");
            date = dateS[2] + "." + dateS[1] + "." + dateS[0];
        }
        mQualification.setmWhat(tfWhat.getText());
        mQualification.setmDate(date);
        mQualification.setmHours(tfCol.getText());
        mQualification.setmTheme(taTheme.getText());
    }


}
