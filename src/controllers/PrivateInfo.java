package controllers;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivateInfo implements Initializable{
    @FXML public Label labelIdStaff;
    @FXML public CustomTextField textName;
    @FXML public CustomTextField textSurname;
    @FXML public CustomTextField textFathNam;
    @FXML public CustomTextField textPosition;
    @FXML public CustomTextField textNumPassp;
    @FXML public CustomTextField textPrivateNum;
    @FXML public CustomTextField textAddress;
    @FXML public CustomTextField textTel1;
    @FXML public CustomTextField textTel2;
    @FXML public Button btnClose;
    @FXML public Button btnSave;
    @FXML public DatePicker textDateOfBirth;
    @FXML public TextArea textAddInfo;
    @FXML public CheckBox checkPed;
    @FXML public CheckBox checkTech;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            setupClearButtonField(textName);
            setupClearButtonField(textSurname);
            setupClearButtonField(textFathNam);
            setupClearButtonField(textPosition);
            setupClearButtonField(textNumPassp);
            setupClearButtonField(textPrivateNum);
            setupClearButtonField(textAddress);
            setupClearButtonField(textTel1);
            setupClearButtonField(textTel2);

    }


    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
