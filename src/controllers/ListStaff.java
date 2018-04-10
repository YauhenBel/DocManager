package controllers;

import interfaces.CollectionListStaff;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Staff;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.ResourceBundle;

public class ListStaff extends Observable implements Initializable {

    @FXML public Button btnOpen;
    @FXML public Button btnAdd;
    @FXML public Button btnDelete;
    private CollectionListStaff collectionListStaff = new CollectionListStaff();
    @FXML public TableView table_staff;
    @FXML public TableColumn<Staff, String> SurnameColumn;
    @FXML public TableColumn<Staff, String> NameColumn;
    @FXML public TableColumn<Staff, String> FathNameColumn;
    @FXML public TableColumn<Staff, String> position;
    @FXML public Label labelCount;
    private static String FXMLSection = "../layouts/PersonalInfo.fxml";
    private Stage primaryStage;
    private Parent root;

    private ObservableList<Staff> staffList =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize\n");
        SurnameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Surname"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("Name"));
        FathNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("FathName"));
        position.setCellValueFactory(new PropertyValueFactory<Staff, String>("Position"));
        //DownloadDates();
        initListeners();
        fillData();
    }

    private void fillData()
    {
        collectionListStaff.filltestData();
        table_staff.setItems(collectionListStaff.getStaffList());
        collectionListStaff.print();
    }

    private void initListeners() {
        collectionListStaff.getStaffList().addListener(new ListChangeListener<Staff>() {
            @Override
            public void onChanged(Change<? extends Staff> c) {
                updateCountLabel();
            }
        });
    }

    private void updateCountLabel()
    {
        labelCount.setText("Количество записей: "
                + collectionListStaff.getStaffList().size());
    }

    private void DownloadDates() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/office_datas";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT * FROM `staff`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_staff = " + rs.getString("id_staff")
                            + ",\nsurname = " + rs.getString("surname")
                            + ",\nname = " + rs.getString("name")
                            + ",\nfather_name = " + rs.getString("father_name")
                            + ",\ntd_of_birth = "+ rs.getString("d_of_birth")
                            + ",\nnum_passp = " + rs.getString("num_passp")
                            + ",\npassp_private_num = " + rs.getString("passp_private_num")
                            + ",\naddress = " + rs.getString("address")
                            + ",\ntel_1 = "+ rs.getString("tel_1")
                            + ",\ntel_2 = " + rs.getString("tel_2")
                            + ",\nadd_info = " + rs.getString("add_info")
                            + ",\ntype_work = " + rs.getString("type_work")
                            + ",\nposition = "+ rs.getString("position") + ";\n}";
                    System.out.println("info: " + str);
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void ActionButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        Object object = actionEvent.getSource();

        if (!(object instanceof Button))
        {
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnOpen":
                System.out.println("btnOpen");
                createGui();
                break;
            case "btnAdd":
                System.out.println("btnAdd");
                break;
            case "btnDelete":
                System.out.println("btnDelete");
                break;
        }
    }
    private void createGui() throws IOException {
        root = FXMLLoader.load(getClass().getResource(FXMLSection));
        primaryStage = new Stage();
        primaryStage.setTitle("Персональная информация");
        primaryStage.setScene(new Scene(root, 700, 630));
        primaryStage.setMaxWidth(700);
        primaryStage.setMaxHeight(630);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}
