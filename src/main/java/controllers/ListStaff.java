/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import interfaces.CollectionListStaff;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import objects.Staff;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 *Work with the list of staff
 */

public class ListStaff extends Observable implements Initializable {

    private static final String FXML_PERSONAL_INFORMATION = "../layouts/PersonalInfo.fxml";

    private Stage mainStage;
    @FXML public Button btnOpen;
    @FXML public Button btnAdd;
    @FXML public Button btnDelete;
    @FXML public TableView<Staff> tableStaff;
    @FXML public TableColumn<Staff, String> surnameColumn;
    @FXML public TableColumn<Staff, String> nameColumn;
    @FXML public TableColumn<Staff, String> fathNameColumn;
    @FXML public TableColumn<Staff, String> position;
    @FXML public Label labelCount;
    @FXML public CustomTextField tfSearch;

    private CollectionListStaff collectionListStaff = new CollectionListStaff();
    private Stage primaryStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlPersonalInfo;
    private PrivateInfo privateInfo;
    private Node clears;
    private ObservableList<Staff> backupList;

    /**Initializing data for work*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize\n");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mSurname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mName"));
        fathNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mFathName"));
        position.setCellValueFactory(new PropertyValueFactory<Staff, String>("mPosition"));
        initListeners();
        try {
            fillData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initLoader();
        setupClearButtonField(tfSearch);
        tableStaff.setEditable(true);

    }

    /**Add delete button in textfield*/
    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
            clears = customTextField.getRight();
            setOnMouseClicked();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**Handler action for button's clear*/
    private void setOnMouseClicked()
    {
        clears.setOnMouseClicked(event -> {
            System.out.println("Clears");
            collectionListStaff.clearList();

            for (Staff staff: backupList){
                collectionListStaff.getStaffList().add(staff);
            }
        });
    }
    /**Download data from DB for table*/
    private void fillData() throws Exception {
        if (collectionListStaff != null) {
            collectionListStaff.clearList();
        }
        assert collectionListStaff != null;
        collectionListStaff.fillData();
        tableStaff.setItems(collectionListStaff.getStaffList());
        if (backupList != null) {
            backupList.clear();
        }
        backupList = FXCollections.observableArrayList();
        backupList.addAll(collectionListStaff.getStaffList());
    }
    /**Initializing listeners for the table's item*/
    private void initListeners() {
        collectionListStaff.getStaffList().addListener((ListChangeListener<Staff>) c -> updateCountLabel());

        tableStaff.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                privateInfo.setStaff(tableStaff.getSelectionModel().getSelectedItem(),
                        collectionListStaff, 1);
                createGui();
                //collectionListStaff.update(privateInfo.getStaff());
            }
        });
    }
    /**Count list in the table*/
    private void updateCountLabel() {
        labelCount.setText("Количество записей: "
                + collectionListStaff.getStaffList().size());
    }

    /**Handle button pressed*/
     public void ActionButtonPressed(javafx.event.ActionEvent actionEvent) throws SQLException {
        Object object = actionEvent.getSource();

        Staff selectedStaff = tableStaff.getSelectionModel().getSelectedItem();

        if (!(object instanceof Button)) {
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnAdd":
                privateInfo.setStaff(null, collectionListStaff, 1);
                createGui();
                /*if (privateInfo.checkValues()){
                    return;
                }
                collectionListStaff.add(privateInfo.getStaff());*/
                break;
            case "btnOpen":
                if (personIsSelected(selectedStaff)) {
                    return;
                }
                privateInfo.setStaff(selectedStaff, null, 0);
                createGui();
                //collectionListStaff.update(privateInfo.getStaff());
                break;
            case "btnDelete":
                if (personIsSelected(selectedStaff)) {
                    return;
                }
                collectionListStaff.delete(selectedStaff);
                break;
        }
    }
    /**Create new window for with personal information of staff*/
    private void createGui() {
        if (primaryStage == null) {
            primaryStage = new Stage();
            primaryStage.setTitle("Персональная информация");
            primaryStage.setScene(new Scene(fxmlPersonalInfo));
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(mainStage);
            primaryStage.setMaxWidth(1020);
            primaryStage.setMaxHeight(660);
            primaryStage.setMinHeight(primaryStage.getHeight());
            primaryStage.setMinWidth(primaryStage.getWidth());
        }
        primaryStage.show();
    }

    void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    /**Initializing loader*/
    private void initLoader(){
        fxmlLoader.setLocation(getClass().getResource(FXML_PERSONAL_INFORMATION));
        try {
            fxmlPersonalInfo = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        privateInfo = fxmlLoader.getController();
    }
    /**Check is selected person on table */
    private boolean personIsSelected(Staff selectPerson) {
        if (selectPerson == null) {
            System.out.println("Не выбран объект!");
            return true;
        }
        System.out.println("Выбран объект!");
        return false;
    }
    /**Search select*/
    public void tfSearchAct(KeyEvent actionEvent) {

        if (!tfSearch.getText().equals("")) {
            collectionListStaff.clearList();
            for (Staff staff : backupList) {
                if (staff.getmSurname().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                    collectionListStaff.addS(staff);
                    System.out.println("getSurname = " + staff.getmSurname());
                }
            }
        }
        if (tfSearch.getText().equals("")){
            System.out.println("Clears");
            collectionListStaff.clearList();

            for (Staff staff: backupList){
                collectionListStaff.getStaffList().add(staff);
            }
        }
        /**Get color to text in table, which was to write in textfield*/
        surnameColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(null);
                    if (!tfSearch.getText().isEmpty() && item.toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                        Double rowHeight = this.getTableRow().getHeight();
                        setGraphic(buildTextFlow(item, tfSearch.getText()));
                        setHeight(rowHeight);
                        setStyle("");
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    } else {

                        setText(item);
                        setTextFill(Color.BLACK);
                        setStyle("");
                        setContentDisplay(ContentDisplay.TEXT_ONLY);
                    }
                }
            }
        });
    }

    private TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length()));
        textFilter.setFill(Color.ORANGE);
        TextFlow textFlow = new TextFlow(textBefore, textFilter, textAfter);
        textFlow.setPrefHeight(10);
        return textFlow;
    }


    /*System.out.println("tfSearchAct");
        if (!tfSearch.getText().equals("")) {
            for (Staff staff : collectionListStaff.getStaffList()) {
                System.out.println("Найдено1");
                if (staff.getSurname().toLowerCase().contains(tfSearch.getText().toLowerCase())) {


                    //collectionListStaff.delete(staff);
                }
            }
        }*/




            /*if (!staff.getSurname().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                collectionListStaff.delete(staff);
            }*/


    public void tfSearchActMouse(MouseEvent mouseEvent) {
        System.out.println("tfSearchActMouse");
        //createBackupList();
    }
}
