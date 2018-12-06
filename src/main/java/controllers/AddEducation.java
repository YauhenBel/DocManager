package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListEducation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Education;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEducation implements Initializable {

    @FXML public ComboBox<String> cbLevelEducation;
    @FXML public CustomTextField tfNameEducation;
    @FXML public CustomTextField tfSpecialty;
    @FXML public CustomTextField tfQualification;
    @FXML public DatePicker dpDateFinish;
    @FXML public Label labelError;
    private Education education;
    private CollectionListEducation mCollectionListEducation;
    private int howButtonPressed = 3;
    private int idStaff;

    ObservableList<String> levelEducation =
            FXCollections.observableArrayList(
                    "Высшее",
                    "Высшее педагогическое",
                    "ССО(пед)",
                    "ССО",
                    "ПТО"
            );


    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfNameEducation);
        SetupClearButtonField.SetupClearButtonField(tfSpecialty);
        SetupClearButtonField.SetupClearButtonField(tfQualification);
        cbLevelEducation.setItems(levelEducation);
    }

    public void setEducation(CollectionListEducation collectionListEducation,
                             Education education){
        logger.info("setEducation - 1");

        mCollectionListEducation = collectionListEducation;
        this.education = education;
        tfNameEducation.setText(education.getmNameEd());
        tfQualification.setText(education.getmQualification());
        tfSpecialty.setText(education.getmSpecialty());
        cbLevelEducation.setValue(education.getLevelEducation());
        if (education.getmDateFinish() == null){
            dpDateFinish.setValue(null);
        }else {
            String[] dateS = education.getmDateFinish().split("\\.");
            dpDateFinish.setValue(LocalDate.parse(dateS[2] + "-" + dateS[1] + "-" + dateS[0]));
        }
        howButtonPressed = 0;

    }
    public void setEducation(CollectionListEducation collectionListEducation,
                             int idStaff){
        logger.info("setEducation - 0");
        System.out.println("setEducation");
        mCollectionListEducation = collectionListEducation;
        this.idStaff = idStaff;
        this.education = new Education();
        tfNameEducation.clear();
        tfSpecialty.clear();
        tfQualification.clear();
        dpDateFinish.setValue(null);
        cbLevelEducation.setValue(null);
        howButtonPressed = 1;
    }

    public void actionBtnPress(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSaveEducation":
                logger.info("btnSaveEducation");
                if (howButtonPressed == 0){
                    logger.info("updateEd");

                    //обновляем данные
                    getDataFromForm();
                    mCollectionListEducation.updateEd(education);
                    return;
                }
                if (howButtonPressed == 1){
                    //записываем новые данные
                    if (checkValue()){
                        logger.info("insertNewEd");
                        getDataFromForm();
                        mCollectionListEducation.insertNewEd(education, idStaff);
                        howButtonPressed = 0;
                    }else {
                        logger.info("Все поля пустые.");
                        labelError.setText("Все поля пустые." +
                                " Заполните хотя бы одно поле.");
                    }
                    return;
                }
                break;
            case "btnCloseEducation":
                logger.info("btnCloseEducation");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        education.setmNameEd(tfNameEducation.getText());
        education.setmSpecialty(tfSpecialty.getText());
        education.setmQualification(tfQualification.getText());
        if (dpDateFinish.getValue() == null)
        {
         education.setmDateFinish(null);
        }else {
            String[] dateS = dateS = dpDateFinish.getValue().toString().split("-");
            education.setmDateFinish(dateS[2] + "." + dateS[1] + "." + dateS[0]);
        }

        education.setLevelEducation(cbLevelEducation.getValue());

    }

    private boolean checkValue(){
        logger.info("checkValue");
        if (!tfNameEducation.getText().isEmpty()
                || !tfSpecialty.getText().isEmpty()
                || !tfQualification.getText().isEmpty()){
            return true;
        }

        return false;
    }

}
