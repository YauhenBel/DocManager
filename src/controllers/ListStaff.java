package controllers;

import interfaces.CollectionListStaff;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Staff;
import sample.Main;

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

import static com.sun.deploy.util.UpdateCheck.showDialog;

public class ListStaff extends Observable implements Initializable {

    @FXML public Button btnOpen;
    @FXML public Button btnAdd;
    @FXML public Button btnDelete;
    public CollectionListStaff collectionListStaff = new CollectionListStaff();
    @FXML public TableView table_staff;
    @FXML public TableColumn<Staff, String> SurnameColumn;
    @FXML public TableColumn<Staff, String> NameColumn;
    @FXML public TableColumn<Staff, String> FathNameColumn;
    @FXML public TableColumn<Staff, String> position;
    @FXML public Label labelCount;
    private static String FXMLSection = "../layouts/PersonalInfo.fxml";
    private Stage primaryStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlPersonalInfo;

    private PrivateInfo privateInfo;

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
        initLoader();
    }

    public void fillData() {
        if (collectionListStaff != null) {
            collectionListStaff.clearList();
        }
        collectionListStaff.filltestData();
        table_staff.setItems(collectionListStaff.getStaffList());
    }

    private void initListeners() {
        collectionListStaff.getStaffList().addListener(new ListChangeListener<Staff>() {
            @Override
            public void onChanged(Change<? extends Staff> c) {
                updateCountLabel();
            }
        });

        table_staff.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    privateInfo.setStaff((Staff) table_staff.getSelectionModel().getSelectedItem());
                    createGui();
                    System.out.println("close window1");
                    //fillData();
                }
            }
        });
    }

    private void updateCountLabel() {
        labelCount.setText("Количество записей: "
                + collectionListStaff.getStaffList().size());
    }

     public void ActionButtonPressed(javafx.event.ActionEvent actionEvent){
        Object object = actionEvent.getSource();

        Staff selectedStaff = (Staff) table_staff.getSelectionModel().getSelectedItem();

        if (!(object instanceof Button)) {
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnOpen":
                System.out.println("btnOpen");
                if (!personIsSelected(selectedStaff)) {
                    return;
                }
                privateInfo.setStaff(selectedStaff);
                createGui();
                System.out.println("close window");
                //fillData();
                break;
            case "btnAdd":
                System.out.println("btnAdd");
                break;
            case "btnDelete":
                System.out.println("btnDelete");
                break;
        }
    }
    private void createGui() {
        if (primaryStage == null) {
            primaryStage = new Stage();
            primaryStage.setTitle("Персональная информация");
            primaryStage.setScene(new Scene(fxmlPersonalInfo));
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.setMaxWidth(1020);
            primaryStage.setMaxHeight(660);
            primaryStage.setMinHeight(primaryStage.getHeight());
            primaryStage.setMinWidth(primaryStage.getWidth());
        }
        primaryStage.showAndWait();
    }

    private boolean personIsSelected(Staff selectPerson) {
        if (selectPerson == null) {
            System.out.println("Не выбран объект!");
            return false;
        }
        System.out.println("Выбран объект!");
        return true;
    }

    private void initLoader() {
        try {

            fxmlLoader.setLocation(getClass().getResource(FXMLSection));
            fxmlPersonalInfo = fxmlLoader.load();
            privateInfo = fxmlLoader.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateTable(javafx.event.ActionEvent actionEvent) {
        //fillData();
    }
}
