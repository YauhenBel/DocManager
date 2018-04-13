package controllers;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import objects.Staff;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
          private Staff staff;
          private ListStaff listStaff = new ListStaff();


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

    public void setStaff(Staff staff){
        if (staff == null){
            return;
        }
        this.staff = staff;
        //labelIdStaff.setText("Номер сотрудника: " + staff.getID_staff());
        textName.setText(staff.getName());
        textSurname.setText(staff.getSurname());
        textFathNam.setText(staff.getFathName());
        textPosition.setText(staff.getPosition());
        /*textNumPassp.setText(staff.getNummPass());
        textPrivateNum.setText(staff.getNumPrivate());
        textAddress.setText(staff.getAddress());
        textTel1.setText(staff.getTel1());
        textTel2.setText(staff.getTel2());
        textDateOfBirth.setValue(LocalDate.of(staff.getYyyyofBirth(), staff.getMmofBirth(), staff.getDdofBirth()));
        textAddInfo.setText(staff.getAddInfo());
        if (staff.getTypeWork() == 1){
            checkPed.setSelected(true);
            checkTech.setSelected(false);
        } else if (staff.getTypeWork() == 2){
            checkTech.setSelected(true);
            checkPed.setSelected(false);
        }*/
    }


    public void ActionBtnPersInfo(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId())
        {
            case "btnSave":
                UpdateDataInDB();
                //listStaff.fillData();
                //listStaff.check();
                break;
            case "btnClose":
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.hide();
                break;
        }

    }

    public void checkAction(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (!(object instanceof CheckBox)){
            return;
        }
        CheckBox checkBox = (CheckBox) object;
        switch (checkBox.getId())
        {
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

    private void UpdateDataInDB(){
        System.out.println("UpdateDataInDB");
        staff.setName(textName.getText());
       /* try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/office_datas?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);

            try {
                Statement stmt = con.createStatement();
                Integer typeWork = 0;
                if (checkPed.isSelected() == true){
                    typeWork = 1;
                }
                if (checkTech.isSelected() == true){
                    typeWork =2;
                }
                String sql_query = "UPDATE `staff` SET "
                        + "`surname` =  " + "'" + textSurname.getText() + "',"
                        + "`name` =  " + "'" + textName.getText() + "',"
                        + "`father_name` =  " + "'" + textFathNam.getText() + "',"
                        + "`num_passp` =  " + "'" + textNumPassp.getText() + "',"
                        + "`passp_private_num` =  " + "'" + textPrivateNum.getText() + "',"
                        + "`address` =  " + "'" + textAddress.getText() + "',"
                        + "`tel_1` =  " + "'" + textTel1.getText() + "',"
                        + "`tel_2` =  " + "'" + textTel2.getText() + "',"
                        + "`add_info` =  " + "'" + textAddInfo.getText() + "',"
                        + "`d_of_birth` =  " + "'" + textDateOfBirth.getValue() + "',"
                        + "`type_work` =  " + "'" + typeWork + "',"
                        + "`position` =  " + "'" + textPosition.getText() + "'"
                        + " WHERE `staff`.`id_staff` = " +staff.getID_staff();
                System.out.println(sql_query);
                /*System.out.println("checkPed = " + checkPed.isSelected());
                System.out.println("checkTech = " + checkTech.isSelected());
                int rs = stmt.executeUpdate(sql_query);
                System.out.println(rs);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
