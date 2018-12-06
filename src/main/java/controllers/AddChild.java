package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListChildren;
import interfaces.CollectionListQualifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Child;
import objects.Qualification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddChild implements Initializable {
    @FXML private CustomTextField tfName;
    @FXML private CustomTextField tfSurname;
    @FXML private CustomTextField tfFathName;
    @FXML private DatePicker dpBithDay;
    @FXML private Label labelError;

    private CollectionListChildren mCollectionListChildren;

    private Child mChild;

    private int idStaff, howButtonPressed;

    private static Logger logger = LogManager.getLogger();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfName);
        SetupClearButtonField.SetupClearButtonField(tfSurname);
        SetupClearButtonField.SetupClearButtonField(tfFathName);
    }

    public void setChild(CollectionListChildren collectionListChildren,
                         Child child){
        logger.info("setChild - 1");
        mCollectionListChildren = collectionListChildren;
        this.mChild = child;
        tfName.setText(mChild.getmName());
        tfSurname.setText(mChild.getmSurname());
        tfFathName.setText(mChild.getmFathName());
        if (child.getmDateOfBirth() == null){
            dpBithDay.setValue(null);
        }else {
            String[] dateS = child.getmDateOfBirth().split("\\.");
            dpBithDay.setValue(LocalDate.parse(dateS[2] + "-" + dateS[1] + "-" + dateS[0]));
        }
        howButtonPressed = 0;
    }

    public void setChild(CollectionListChildren collectionListChildren,
                                 int idStaff){
        logger.info("setChild - 0");
        mCollectionListChildren = collectionListChildren;
        this.idStaff = idStaff;
        this.mChild = new Child();
        tfName.clear();
        tfSurname.clear();
        tfFathName.clear();
        dpBithDay.setValue(null);
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
            case "btnSaveChild":
                logger.info("btnSaveChild");
                if (howButtonPressed == 0){
                    logger.info("btnSaveChild - 0");
                //обновляем данные
                if (checkValue()){
                    getDataFromForm();
                    mCollectionListChildren.updateChild(mChild);
                }

                return;
            }
            if (howButtonPressed == 1){
                //записываем новые данные
                if (checkValue()){
                    logger.info("insertNewChild");
                    getDataFromForm();
                    mCollectionListChildren.insertNewChild(mChild, idStaff);
                    howButtonPressed = 0;
                }
                return;
            }

                break;
            case "btnCloseChild":
                logger.info("btnCloseChild");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }

    private boolean checkValue(){
        logger.info("checkValue");
        if (tfName.getText().isEmpty()
                && tfSurname.getText().isEmpty()
                && tfFathName.getText().isEmpty()) {
            labelError.setText("Все поля пустые.");
            return false;
        }

        return true;
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        if (dpBithDay.getValue() == null){
            mChild.setmDateOfBirth(null);
        }else {
            String[] dateS = dpBithDay.getValue().toString().split("-");
            mChild.setmDateOfBirth(dateS[2] + "." + dateS[1] + "." + dateS[0]);
        }
        mChild.setmName(tfName.getText());
        mChild.setmSurname(tfSurname.getText());
        mChild.setmFathName(tfFathName.getText());
    }

}
