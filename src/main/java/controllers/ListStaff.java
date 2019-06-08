/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import interfaces.CollectionListContract;
import interfaces.CollectionListStaff;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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

import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import objects.Filter;
import objects.Staff;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 *Work with the list of staff
 */

public class ListStaff extends Observable implements Initializable {

    private static final String FXML_PERSONAL_INFORMATION = "/layouts/PersonalInfo.fxml";
    private static final String FXML_FINISH_CONTRACTS = "/layouts/FinishDocuments.fxml";
    private static final String FXML_BIG_FILTER = "/layouts/BigFilter.fxml";

    @FXML public Button btnOpenFinishContracts;
    @FXML public  Button btnOpenFinishMedical;

    @FXML public ComboBox<String> cbFormContract;
    @FXML public CustomTextField tfAgeFrom;
    @FXML public CustomTextField tfAgeTo;
    @FXML public ComboBox<String> cbCategory;
    @FXML public ComboBox<String> cbEducation;
    @FXML public CustomTextField tfLengthWorkFrom;
    @FXML public CustomTextField tfLengthWorkTo;
    @FXML public Button btnFilter;
    @FXML public ComboBox<String> cbHoliday;
    @FXML public CheckBox cbGeneralLengthWork;
    @FXML public Label lSLabelError;

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
    private Stage primaryStage, finishContractsStage, bigFilterStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private FXMLLoader fxmlLoaderFinishContracts = new FXMLLoader();
    private FXMLLoader fxmlLoaderBigFilter = new FXMLLoader();
    private Parent fxmlPersonalInfo, fxmlFinishContracts, fxmlBigFilter;
    private PrivateInfo privateInfo;
    private BigFilter bigFilter;
    private Node clears;
    private ObservableList<Staff> backupList;

    private FinishDocuments finishDocuments;

    private CollectionListContract collectionListContract = new CollectionListContract();

    private Filter filter;

    String lastLogin = "";

    private Boolean status = false;

    private int rootLvl = 0;

    ObservableList<String> typeContract =
            FXCollections.observableArrayList(
                    "Контракт",
                    "Договор подряда",
                    "Трудовой договор"
            );

    ObservableList<String> levelCategories =
            FXCollections.observableArrayList(
                    "Без категории",
                    "Вторая",
                    "Высшая",
                    "Первая",
                    "Учитель методист"
            );

    ObservableList<String> levelEducation =
            FXCollections.observableArrayList(
                    "Высшее",
                    "Высшее педагогическое",
                    "ССО(пед)",
                    "ССО",
                    "ПТО"
            );

    ObservableList<String> types =
            FXCollections.observableArrayList(
                    "Трудовой",
                    "Социальный",
                    "Декретный"
            );

    private static Logger logger = LogManager.getLogger();

    private double xOffset, yOffset;

    /**Initializing data for work*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mSurname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mName"));
        fathNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("mFathName"));
        position.setCellValueFactory(new PropertyValueFactory<Staff, String>("mPosition"));
        initListeners();
        try {
            fillData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        initLoader();
        setupClearButtonField(tfSearch);
        setupClearButtonField(tfAgeFrom);
        setupClearButtonField(tfAgeTo);
        setupClearButtonField(tfLengthWorkFrom);
        setupClearButtonField(tfLengthWorkTo);
        tableStaff.setEditable(true);
        collectionListContract.lengthWork(collectionListStaff.getStaffList());

        cbCategory.setItems(levelCategories);
        cbEducation.setItems(levelEducation);
        cbFormContract.setItems(typeContract);
        cbHoliday.setItems(types);
        lSLabelError.setText("");

    }

    /**Add delete button in textfield*/
    private void setupClearButtonField(CustomTextField customTextField) {
        logger.info("setupClearButtonField");
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);

            m.invoke(null, customTextField, customTextField.rightProperty());
            if (customTextField.getId().equals("tfSearch")) {
                clears = customTextField.getRight();
                setOnMouseClicked();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }




    /**Handler action for button's clear*/
    private void setOnMouseClicked()
    {
        logger.info("setOnMouseClicked");
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
        logger.info("fillData");
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

        collectionListStaff.fillFinishWorkers();
        btnOpenFinishContracts.setText("Окончание контракта(" + collectionListStaff.getColFinishWorkers() + ")");

        collectionListStaff.fillFinishMed();
        btnOpenFinishMedical.setText("АДС и флюорография(" + collectionListStaff.getColFinishMed() + ")");

        //collectionListStaff.refreshLengthWork();
    }
    /**Initializing listeners for the table's item*/
    private void initListeners() {
        logger.info("initListeners");
        collectionListStaff.getStaffList().addListener((ListChangeListener<Staff>) c -> updateCountLabel());

        tableStaff.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                privateInfo.setStaff(tableStaff.getSelectionModel().getSelectedItem(),
                        collectionListStaff, 1);
                createGui();

                collectionListStaff.fillFinishWorkers();
                btnOpenFinishContracts.setText("Окончание контракта(" + collectionListStaff.getColFinishWorkers() + ")");

                collectionListStaff.fillFinishMed();
                btnOpenFinishMedical.setText("АДС и флюорография(" + collectionListStaff.getColFinishMed() + ")");
            }
        });
    }
    /**Count list in the table*/
    private void updateCountLabel() {
        logger.info("updateCountLabel");
        labelCount.setText("Количество записей: "
                + collectionListStaff.getStaffList().size());
    }

    /**Handle button pressed*/
     public void ActionButtonPressed(javafx.event.ActionEvent actionEvent) {
        logger.info("ActionButtonPressed");
        Object object = actionEvent.getSource();

        Staff selectedStaff = tableStaff.getSelectionModel().getSelectedItem();

        if (!(object instanceof Button)) {
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnAdd":
                logger.info("btnAdd");
                privateInfo.setStaff(null, collectionListStaff, 1);
                createGui();
                /*if (privateInfo.checkValues()){
                    return;
                }
                collectionListStaff.add(privateInfo.getStaff());*/
                break;
            case "btnOpen":
                logger.info("btnOpen");
                if (personIsSelected(selectedStaff)) {
                    return;
                }
                privateInfo.setStaff(selectedStaff, null, 0);
                createGui();
                collectionListStaff.fillFinishWorkers();
                btnOpenFinishContracts.setText("Окончание контракта(" + collectionListStaff.getColFinishWorkers() + ")");

                collectionListStaff.fillFinishMed();
                btnOpenFinishMedical.setText("АДС и флюорография(" + collectionListStaff.getColFinishMed() + ")");
                break;
            case "btnDelete":
                logger.info("btnDelete");
                if (personIsSelected(selectedStaff)) {
                    return;
                }
                collectionListStaff.delete(selectedStaff);
                break;
            case "btnOpenFinishContracts":
                logger.info("btnOpenFinishContracts");
                finishDocuments.setListFinishDocuments(collectionListStaff.getListWorkers(), 0);
                creteWindowFinishContracts();
                break;
            case "btnOpenFinishMedical":
                logger.info("btnOpenFinishMedical");
                finishDocuments.setListFinishDocuments(collectionListStaff.getListmed(), 1);
                creteWindowFinishContracts();
                break;
            case "btnFilter":
                logger.info("btnFilter");
                getFilter();
                if (!collectionListStaff.toFilter(filter)){
                    lSLabelError.setText("Укажите хотя бы один критерий для поиска.");
                    return;
                } else {
                    status = true;
                }
                break;
            case "btnCloseFilter":
                logger.info("btnCloseFilter");
                lSLabelError.setText("");
                if (status){
                    collectionListStaff.getStaffList().clear();
                    collectionListStaff.fillData();
                }

                tfAgeFrom.clear();
                tfAgeTo.clear();
                tfLengthWorkFrom.clear();
                tfLengthWorkTo.clear();
                cbFormContract.setValue(null);
                cbEducation.setValue(null);
                cbCategory.setValue(null);
                cbHoliday.setValue(null);
                cbGeneralLengthWork.setSelected(false);
                status = false;
                break;
            case "bigFilterOpen":
                logger.info("bigFilterOpen");
                try {
                    createBigFilterGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filter = bigFilter.getBigFilter();
                if (filter == null){
                    return;
                }
                //filter.printInfo();
                if (!collectionListStaff.toFilter(filter)){
                    lSLabelError.setText("Укажите хотя бы один критерий для поиска.");
                    return;
                } else {
                    lSLabelError.setText("");
                    status = true;
                }
                status = true;
                break;
            case "logOut":
                try {
                    logOut(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        logger.info("logOut");
        CreateGui createGui = new CreateGui();
        createGui.backToLogIn();
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

    private void getFilter(){
         logger.info("getFilter");
         filter = new Filter(
                 tfAgeFrom,
                 tfAgeTo,
                 tfLengthWorkFrom,
                 tfLengthWorkTo,
                 cbFormContract,
                 cbEducation,
                 cbCategory,
                 cbHoliday,
                 cbGeneralLengthWork
                 );
         /*filter.setYearFrom(tfAgeFrom.getText());
         filter.setYearTo(tfAgeTo.getText());
         filter.setLengthWorkFrom(tfLengthWorkFrom.getText());
         filter.setLengthWorkTo(tfLengthWorkTo.getText());
         if (cbFormContract.getValue() == null) {
             filter.setFormContract(null);
         }else {
             filter.setFormContract(cbFormContract.getValue().toString());
         }
         if (cbEducation.getValue() == null){
             filter.setLevelEducation(null);
         }else {
             filter.setLevelEducation(cbEducation.getValue().toString());
         }
         if (cbCategory.getValue() == null){
             filter.setLevelCategory(null);
         }else {
             filter.setLevelCategory(cbCategory.getValue().toString());
         }
         if (cbHoliday.getValue() == null){
             filter.setHoliday(null);
         }else {
             filter.setHoliday(cbHoliday.getValue().toString());
         }
         if (!cbGeneralLengthWork.isSelected()){
             filter.setGeneralLengthWork(false);
         }else {
             filter.setGeneralLengthWork(true);
         }*/

         filter.printInfo();
    }
    /**Create new window for with personal information of staff*/
    private void createGui() {
        logger.info("createGui");
        if (primaryStage == null) {
            primaryStage = new Stage();
            privateInfo.setMainStage(primaryStage, rootLvl);
            primaryStage.setTitle("Персональная информация");
            Scene scene = new Scene(fxmlPersonalInfo);
            settingScene(scene, primaryStage);
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(mainStage);
        }
        primaryStage.showAndWait();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
    }

    private void creteWindowFinishContracts() {
        logger.info("creteWindowFinishContracts");
        if (finishContractsStage == null) {
            finishContractsStage = new Stage();
            finishContractsStage.setTitle("Окончание сроков");
            Scene scene = new Scene(fxmlFinishContracts);
            settingScene(scene, finishContractsStage);
            finishContractsStage.setScene(scene);
            finishContractsStage.initModality(Modality.WINDOW_MODAL);
            finishContractsStage.initOwner(mainStage);
        }
        finishContractsStage.show();
        finishContractsStage.setMinHeight(finishContractsStage.getHeight());
        finishContractsStage.setMinWidth(finishContractsStage.getWidth());
        finishContractsStage.setMaxHeight(finishContractsStage.getHeight());
        finishContractsStage.setMaxWidth(finishContractsStage.getWidth());
    }

    public void createBigFilterGUI() throws IOException {
        logger.info("Big Filter");
        if (bigFilterStage == null){
            bigFilterStage = new Stage();
            bigFilterStage.setTitle("Расширенный фильтр");
            Scene scene = new Scene(fxmlBigFilter);
            settingScene(scene, bigFilterStage);
            bigFilterStage.setScene(scene);
            bigFilterStage.initModality(Modality.APPLICATION_MODAL);
        }
        bigFilterStage.showAndWait();
        bigFilterStage.setMinHeight(bigFilterStage.getHeight());
        bigFilterStage.setMinWidth(bigFilterStage.getWidth());
        bigFilterStage.setMaxWidth(bigFilterStage.getHeight());
        bigFilterStage.setMaxHeight(bigFilterStage.getWidth());
    }

    void setMainStage(Stage mainStage, String lastLogin, int rootLvl) {
        logger.info("setMainStage");
        this.mainStage = mainStage;
        this.lastLogin = lastLogin;
        this.rootLvl = rootLvl;
        switch (rootLvl){
            case 0:
                btnOpen.setDisable(false);
                btnAdd.setDisable(false);
                btnDelete.setDisable(false);
                btnFilter.setDisable(false);
                btnOpenFinishMedical.setDisable(false);
                btnOpenFinishContracts.setDisable(false);
                break;
            case 1:
                btnOpen.setDisable(false);
                btnAdd.setDisable(false);
                btnDelete.setDisable(true);
                btnFilter.setDisable(false);
                btnOpenFinishMedical.setDisable(false);
                btnOpenFinishContracts.setDisable(false);
                break;
            case 2:
                btnOpen.setDisable(false);
                btnAdd.setDisable(true);
                btnDelete.setDisable(true);
                btnFilter.setDisable(false);
                btnOpenFinishMedical.setDisable(false);
                btnOpenFinishContracts.setDisable(false);
                break;
        }

    }

    private <T extends Event> void closeWindowEvent(T t) {
        logger.info("closeWindowEvent");
    }

    /**Initializing loader*/
    private void initLoader(){
        logger.info("initLoader");
        fxmlLoader.setLocation(getClass().getResource(FXML_PERSONAL_INFORMATION));
        fxmlLoaderFinishContracts.setLocation(getClass().getResource(FXML_FINISH_CONTRACTS));
        fxmlLoaderBigFilter.setLocation(getClass().getResource(FXML_BIG_FILTER));

        try {
            fxmlPersonalInfo = fxmlLoader.load();
            fxmlFinishContracts = fxmlLoaderFinishContracts.load();
            fxmlBigFilter = fxmlLoaderBigFilter.load();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        privateInfo = fxmlLoader.getController();
        finishDocuments = fxmlLoaderFinishContracts.getController();
        bigFilter = fxmlLoaderBigFilter.getController();
    }
    /**Check is selected person on table */
    private boolean personIsSelected(Staff selectPerson) {
        logger.info("personIsSelected");
        if (selectPerson == null) {
            logger.info("Не выбран объект!");
            return true;
        }
        return false;
    }
    /**Search select*/
    public void tfSearchAct(KeyEvent actionEvent) {

        if (!tfSearch.getText().equals("")) {
            collectionListStaff.clearList();
            for (Staff staff : backupList) {
                if (staff.getmSurname().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                    collectionListStaff.addS(staff);
                    logger.info("getSurname = " + staff.getmSurname());
                }
            }
        }
        if (tfSearch.getText().equals("")){
            logger.info("Clears");
            collectionListStaff.clearList();

            for (Staff staff: backupList){
                collectionListStaff.getStaffList().add(staff);
            }
        }
        /**Get color to text in table, which was to write in textfield*/
        surnameColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                logger.info("updateItem");
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
        logger.info("buildTextFlow");
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length()));
        textFilter.setFill(Color.ORANGE);
        TextFlow textFlow = new TextFlow(textBefore, textFilter, textAfter);
        textFlow.setPrefHeight(10);
        return textFlow;
    }

    public void tfSearchActMouse(MouseEvent mouseEvent) {
        logger.info("tfSearchActMouse");
        //createBackupList();
    }

    private void settingScene(Scene scene, Stage stage){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }
}
