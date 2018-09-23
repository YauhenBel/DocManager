/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import interfaces.CollectionListStaff;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import objects.Staff;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Work with the list of staff
 */
public class PrivateInfo implements Initializable{
    @FXML public CustomTextField textName;
    @FXML public CustomTextField textSurname;
    @FXML public CustomTextField textFathNam;
    @FXML public CustomTextField textPosition;
    @FXML public CustomTextField textNumPassp;
    @FXML public CustomTextField textPrivateNum;
    @FXML public CustomTextField textAddress;
    @FXML public CustomTextField textAnyAddress;
    @FXML public CustomTextField textTel1;
    @FXML public CustomTextField textTel2;
    @FXML public Button btnClose;
    @FXML public Button btnSave;
    @FXML public DatePicker textDateOfBirth;
    @FXML public TextArea textAddInfo;
    @FXML public CheckBox checkPed;
    @FXML public CheckBox checkTech;
    private Staff staff;
          private CollectionListStaff mCollectionListStaff;
          private int mHowWasClickButton;


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
            setupClearButtonField(textAnyAddress);
           // mCollectionListStaff = new CollectionListStaff();
    }
    /**Create button of clear for textfield*/
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

    public void setStaff(Staff staff, CollectionListStaff _collectionListStaff, int _howWasClickButton){
        if (staff == null){
            staff = new Staff();
            this.staff = staff;
            textName.clear();
            textName.clear();
            textSurname.clear();
            textFathNam.clear();
            textPosition.clear();
            textNumPassp.clear();
            textPrivateNum.clear();
            textAddress.clear();
            textAnyAddress.clear();
            textTel1.clear();
            textTel2.clear();
            textDateOfBirth.setValue(LocalDate.now());
            textAddInfo.clear();
            checkPed.setSelected(false);
            checkTech.setSelected(false);
            this.mCollectionListStaff = _collectionListStaff;
            this.mHowWasClickButton = _howWasClickButton;
            return;
        }
        this.staff = staff;
        textName.setText(staff.getmName());
        textSurname.setText(staff.getmSurname());
        textFathNam.setText(staff.getmFathName());
        textPosition.setText(staff.getmPosition());
        textNumPassp.setText(staff.getmIdDoc());
        textPrivateNum.setText(staff.getmDocPrivetNum());
        textAddress.setText(staff.getmAddress());
        textAnyAddress.setText(staff.getmAnyAddress());
        textTel1.setText(staff.getmTel1());
        textTel2.setText(staff.getmTel2());
        textDateOfBirth.setValue(LocalDate.parse(staff.getmDateOfBirth()));
        textAddInfo.setText(staff.getmAddInfo());
        if (staff.getmTypeWork() == 1){
            checkPed.setSelected(true);
            checkTech.setSelected(false);
        } else if (staff.getmTypeWork() == 2){
            checkTech.setSelected(true);
            checkPed.setSelected(false);
        }
    }
    /**Handler button pressed*/
    public void actionBtnPersInfo(ActionEvent actionEvent) throws SQLException {
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSave":
                System.out.println("btnSave");
                if (checkValues()){
                    return;
                }
                UpdateStaff();
                if (mHowWasClickButton == 0){
                    mCollectionListStaff.update(staff);/*
                    Thread t = new Thread(() -> {
                        try {
                            mCollectionListStaff.update(staff);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                    t.start();*/

                }
                if (mHowWasClickButton == 1){
                    mCollectionListStaff.addInDb(staff);
                    /*Thread t = new Thread(() -> {
                        try {
                            mCollectionListStaff.addInDb(staff);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                    t.start();*/

                }
                break;
            case "btnClose":
                System.out.println("btnClose");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }
    /**check the value which was install in checkbox*/
    public void checkAction(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (!(object instanceof CheckBox)){
            return;
        }
        CheckBox checkBox = (CheckBox) object;
        switch (checkBox.getId()) {
            case "checkPed":
                checkPed.setSelected(true);
                checkTech.setSelected(false);
                break;
            case "checkTech":
                checkTech.setSelected(true);
                checkPed.setSelected(false);
                break;
        }
    }

    private boolean checkValues(){
        System.out.println(textName.getText().equals(""));
        if (!textName.getText().equals("") || !textSurname.getText().equals("") || !textFathNam.getText().equals("")){
            System.out.println("Return false!");
            return false;
        }
        return true;
    }
    /**Update object's staff*/
    private void UpdateStaff(){
        Integer typeWork = 0;
        if (checkPed.isSelected()){
            typeWork = 1;
        }
        if (checkTech.isSelected()){
            typeWork =2;
        }
        staff.setmName(textName.getText());
        staff.setmSurname(textSurname.getText());
        staff.setmFathName(textFathNam.getText());
        staff.setmPosition(textPosition.getText());
        staff.setmAddInfo(textAddInfo.getText());
        staff.setmIdDoc(textNumPassp.getText());
        staff.setmDocPrivetNum(textPrivateNum.getText());
        staff.setmTel1(textTel1.getText());
        staff.setmTel2(textTel2.getText());
        staff.setmDateOfBirth(textDateOfBirth.getValue().toString());
        staff.setmTypeWork(typeWork);
        staff.setmAddress(textAddress.getText());
        staff.setmAnyAddress(textAnyAddress.getText());

        /*Thread t = new Thread(() -> {
            try {

                System.out.println("Hello");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        t.start();*/
    }

    Staff getStaff(){ return staff; }
}
