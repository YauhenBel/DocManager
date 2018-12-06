/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package controllers;

import Other.SetupClearButtonField;
import interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import objects.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

/**
 * Work with the list of staff
 */
public class PrivateInfo implements Initializable{
    @FXML public CustomTextField textName;
    @FXML public CustomTextField textSurname;
    @FXML public CustomTextField textFathNam;
    @FXML public CustomTextField textPosition;
    @FXML public CustomTextField textidNum;
    @FXML public CustomTextField textpasspNum;

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
    @FXML public Label errorInformation;

    @FXML public TableColumn<Education, String> columnNameEducations;
    @FXML public TableColumn<Education, String> columnDateFinishEducation;
    @FXML public TableColumn<Education, String> columnSpecialty;
    @FXML public TableView<Education> tableEducation;

    @FXML public TableColumn<Qualification, String> columnWhat;
    @FXML public TableColumn<Qualification, String> columnDateQualification;
    @FXML public TableColumn<Qualification, String> columnHourQualification;
    @FXML public TableView<Qualification> tableQualifications;

    @FXML public TableColumn<Child, String> columnChildName;
    @FXML public TableColumn<Child, String> columnChildSurname;
    @FXML public TableColumn<Child, String> columnChildFathName;
    @FXML public TableColumn<Child, String> columnChildDateOfBirth;
    @FXML public TableView<Child> tableChildren;

    @FXML public TableColumn<RZO, String> columnProfession;
    @FXML public TableColumn<RZO, String> columnPercent;
    @FXML public TableColumn<RZO, String> columnDateStart;
    @FXML public TableColumn<RZO, String> columnDateFinish;
    @FXML public TableView<RZO> tableRZO;

    @FXML public TableColumn<Parthneriship, String> columnDateStartParthnehShip;
    @FXML public TableColumn<Parthneriship, String> columnDateFinishParthnehShip;
    @FXML public TableColumn<Parthneriship, String> columnTypeParthnehShip;
    @FXML public TableColumn<Parthneriship, String> columnProfessionParth;
    @FXML public TableView<Parthneriship> tableParthnerShip;

    @FXML public TableView<Holiday> tableHolidays;
    @FXML public TableColumn<Holiday, String> columnTypeHolidays;
    @FXML public TableColumn<Holiday, String> columnMoneyHolidays;
    @FXML public TableColumn<Holiday, String> columnStartWork;
    @FXML public TableColumn<Holiday, String> columnFinishWork;
    @FXML public TableColumn<Holiday, String> columnDateStartHolidays;
    @FXML public TableColumn<Holiday, String> columnDateFinishHolidays;
    @FXML public TableColumn<Holiday, String> columnDateStartHolidays1;
    @FXML public TableColumn<Holiday, String> columnDateFinishHolidays1;


    @FXML public TableView<Category> tableCategory;
    @FXML public TableColumn<Category, String> columnTypeCategory;
    @FXML public TableColumn<Category, String> columnLevelCategory;
    @FXML public TableColumn<Category, String> columnDateCategory;


    @FXML public Label labelLengthWorkGeneral;
    @FXML public Label labelLengthWork;
    public CustomTextField tfOutsideLengthWorkYears;
    public CustomTextField tfOutsideLengthWorkMonth;
    public CustomTextField tfOutsideLengthWorkDays;


    @FXML private TableView<Contract> tableContracts;
    @FXML private TableColumn<Contract, String> columnTypeContracts;
    @FXML private TableColumn<Contract, String> columnStartContracts;
    @FXML private TableColumn<Contract, String> columnFinishContracts;

    @FXML public DatePicker dpFluoro;
    @FXML public DatePicker dpAds;




    @FXML private Tab tab2;
    @FXML private Tab tab3;
    @FXML private Tab tab4;
    @FXML private Tab tab5;
    @FXML private Tab tab6;
    @FXML private Tab tab7;

    private Staff staff, mStaffAll;
    private int mHowWasClickButton;
    private GetPersonalInformation mGetPersonalInformation;

    private CollectionListStaff mCollectionListStaff;
    private CollectionListEducation mCollectionListEducation;
    private CollectionListQualifications mCollectionListQualifications;
    private CollectionListChildren mCollectionListChildren;
    private CollectionListRZO collectionListRZO;
    private CollectionListParthnership collectionListParthnership;
    private CollectionListHoliday collectionListHoliday;
    private CollectionListCategories collectionListCategories;
    private CollectionListContract collectionListContract;
    private Contract contract;
    private ObjectMedical objectMedical;
    private Medical medical;


    private Stage qualificationStage, educationStage, childStage, rzoStage, parthnerShipStage, holidayStage,
            categoryStage, contractStage;
    FXMLLoader fxmlLoaderEducation = new FXMLLoader();
    FXMLLoader fxmlLoaderQualification = new FXMLLoader();
    FXMLLoader fxmlLoaderChild = new FXMLLoader();
    FXMLLoader fxmlLoaderRZO = new FXMLLoader();
    FXMLLoader fxmlLoaderParthner = new FXMLLoader();
    FXMLLoader fxmlLoaderHoliday = new FXMLLoader();
    FXMLLoader fxmlLoaderCategory = new FXMLLoader();
    FXMLLoader fxmlLoaderContract = new FXMLLoader();
    private Parent fxmlInfoEducation, fxmlInfoQualification, fxmlInfoChild, fxmlInfoRZO, fxmlInfoParthnerShip,
            fxmlInfoHoliday, fxmlInfoCategory, fxmlInfoContract;

    private Stage mainStage;
    private static final String FXML_EDUCATION = "/layouts/AddEducation.fxml";
    private static final String FXML_QUALIFICATION = "/layouts/AddQualification.fxml";
    private static final String FXML_CHILD = "/layouts/AddChild.fxml";
    private static final String FXML_RZO = "/layouts/AddRZO.fxml";
    private static final String FXML_PARTHNERSHIP = "/layouts/AddParthnerShip.fxml";
    private static final String FXML_HOLIDAY = "/layouts/AddHoliday.fxml";
    private static final String FXML_CATEGORY = "/layouts/AddCategory.fxml";
    private static final String FXML_CONTRACT = "/layouts/AddContract.fxml";

    private AddEducation addEducation;
    private AddQualification addQualification;
    private AddChild addChild;
    private AddRZO addRZO;
    private AddParthnership addParthnership;
    private AddHoliday addHoliday;
    private AddCategory addCategory;
    private AddContract addContract;

    private static Logger logger = LogManager.getLogger();




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(textName);
        SetupClearButtonField.SetupClearButtonField(textSurname);
        SetupClearButtonField.SetupClearButtonField(textFathNam);
        SetupClearButtonField.SetupClearButtonField(textPosition);
        SetupClearButtonField.SetupClearButtonField(textidNum);
        SetupClearButtonField.SetupClearButtonField(textpasspNum);
        SetupClearButtonField.SetupClearButtonField(textAddress);
        SetupClearButtonField.SetupClearButtonField(textTel1);
        SetupClearButtonField.SetupClearButtonField(textTel2);
        SetupClearButtonField.SetupClearButtonField(textAnyAddress);
        mGetPersonalInformation = new GetPersonalInformation();
        mCollectionListEducation = new CollectionListEducation();
        mCollectionListQualifications = new CollectionListQualifications();
        mCollectionListChildren = new CollectionListChildren();
        collectionListRZO = new CollectionListRZO();
        collectionListParthnership = new CollectionListParthnership();
        collectionListHoliday = new CollectionListHoliday();
        collectionListCategories = new CollectionListCategories();
        collectionListContract = new CollectionListContract();
        objectMedical = new ObjectMedical();

        columnNameEducations.setCellValueFactory(new PropertyValueFactory<Education, String>("mNameEd"));
        columnDateFinishEducation.setCellValueFactory(new PropertyValueFactory<Education, String>("mDateFinish"));
        columnSpecialty.setCellValueFactory(new PropertyValueFactory<Education, String>("mSpecialty"));

        columnWhat.setCellValueFactory(new PropertyValueFactory<Qualification, String>("mWhat"));
        columnDateQualification.setCellValueFactory(new PropertyValueFactory<Qualification, String>("mDate"));
        columnHourQualification.setCellValueFactory(new PropertyValueFactory<Qualification, String>("mHours"));

        columnChildName.setCellValueFactory(new PropertyValueFactory<Child, String>("mName"));
        columnChildSurname.setCellValueFactory(new PropertyValueFactory<Child, String>("mSurname"));
        columnChildFathName.setCellValueFactory(new PropertyValueFactory<Child, String>("mFathName"));
        columnChildDateOfBirth.setCellValueFactory(new PropertyValueFactory<Child, String>("mDateOfBirth"));

        columnProfession.setCellValueFactory(new PropertyValueFactory<RZO, String>("proff"));
        columnPercent.setCellValueFactory(new PropertyValueFactory<RZO, String>("percent"));
        columnDateStart.setCellValueFactory(new PropertyValueFactory<RZO, String>("dateStart"));
        columnDateFinish.setCellValueFactory(new PropertyValueFactory<RZO, String>("dateFinish"));

        columnDateStartParthnehShip.setCellValueFactory(new PropertyValueFactory<Parthneriship, String>("dStart"));
        columnDateFinishParthnehShip.setCellValueFactory(new PropertyValueFactory<Parthneriship, String>("dFinish"));
        columnTypeParthnehShip.setCellValueFactory(new PropertyValueFactory<Parthneriship, String>("type1"));
        columnProfessionParth.setCellValueFactory(new PropertyValueFactory<Parthneriship, String>("position"));

        columnTypeHolidays.setCellValueFactory(new PropertyValueFactory<Holiday, String>("typeHoliday"));
        columnMoneyHolidays.setCellValueFactory(new PropertyValueFactory<Holiday, String>("saveWage"));
        columnStartWork.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dStartWork"));
        columnFinishWork.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dFinishWork"));
        columnDateStartHolidays.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dStart"));
        columnDateFinishHolidays.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dFinish"));
        columnDateStartHolidays1.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dStart1"));
        columnDateFinishHolidays1.setCellValueFactory(new PropertyValueFactory<Holiday, String>("dFinish1"));

        columnTypeCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("typeCategory"));
        columnLevelCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("levelCategory"));
        columnDateCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("dateCategory"));

        columnTypeContracts.setCellValueFactory(new PropertyValueFactory<Contract, String>("type"));
        columnStartContracts.setCellValueFactory(new PropertyValueFactory<Contract, String>("dStart"));
        columnFinishContracts.setCellValueFactory(new PropertyValueFactory<Contract, String>("dFinish"));

        initListeners();
        initLoader();
    }

    /**Initializing listeners for the table's item*/
    private void initListeners() {
        logger.info("initListeners");

        tableEducation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addEducation.setEducation(mCollectionListEducation,
                        tableEducation.getSelectionModel().getSelectedItem());
                createWindowEducation();
            }
        });

        tableQualifications.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addQualification.setQualification(mCollectionListQualifications,
                        tableQualifications.getSelectionModel().getSelectedItem());
                createWindowQualification();
            }
        });

        tableChildren.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addChild.setChild(mCollectionListChildren,
                        tableChildren.getSelectionModel().getSelectedItem());
                createWindowChild();
            }
        });

        tableRZO.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addRZO.setRZO(collectionListRZO,
                        tableRZO.getSelectionModel().getSelectedItem());
                createWindowRZO();
            }
        });

        tableParthnerShip.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addParthnership.setParthnership(collectionListParthnership,
                        tableParthnerShip.getSelectionModel().getSelectedItem());
                createWindowParthnership();
            }
        });

        tableHolidays.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addHoliday.setHoliday(collectionListHoliday,
                        tableHolidays.getSelectionModel().getSelectedItem());
                createWindowHoliday();
            }
        });

        tableCategory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addCategory.setCategory(collectionListCategories,
                        tableCategory.getSelectionModel().getSelectedItem());
                createWindowCategory();
            }
        });

        tableContracts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                addContract.setContract(collectionListContract,
                        tableContracts.getSelectionModel().getSelectedItem(), labelLengthWork, labelLengthWorkGeneral);
                createWindowContract();
            }
        });
    }

    public void setStaff(Staff staff, CollectionListStaff _collectionListStaff, int _howWasClickButton){
        logger.info("setStaff");
        this.mCollectionListStaff = _collectionListStaff;
        if (staff == null){
            tab2.setDisable(true);
            tab3.setDisable(true);
            tab4.setDisable(true);
            tab5.setDisable(true);
            tab6.setDisable(true);
            tab7.setDisable(true);
            staff = new Staff();
            mStaffAll = new Staff();
            this.staff = staff;
            this.contract = new Contract();
            this.medical = new Medical();
            textName.clear();
            textName.clear();
            textSurname.clear();
            textFathNam.clear();
            textPosition.clear();
            textidNum.clear();
            textpasspNum.clear();
            textAddress.clear();
            textAnyAddress.clear();
            textTel1.clear();
            textTel2.clear();
            textDateOfBirth.setValue(null);
            textAddInfo.clear();
            checkPed.setSelected(false);
            checkTech.setSelected(false);



            dpFluoro.setValue(LocalDate.now());
            dpAds.setValue(LocalDate.now());

            this.mHowWasClickButton = _howWasClickButton;



            return;
        }
        this.staff = staff;

        tab2.setDisable(false);
        tab3.setDisable(false);
        tab4.setDisable(false);
        tab5.setDisable(false);
        tab6.setDisable(false);
        tab7.setDisable(false);

        fillTables();

        objectMedical.fillData(staff.getmIdstaff());
        this.medical = objectMedical.getObjectMedical();

        //refreshLengthWork();

        if (medical.getFluoro() == null){
            dpFluoro.setValue(null);
        }else {
            dpFluoro.setValue(LocalDate.parse(medical.getFluoro()));
        }

        if (medical.getAds() == null){
            dpAds.setValue(null);
        }else {
            dpAds.setValue(LocalDate.parse(medical.getAds()));
        }

        textName.setText(mStaffAll.getmName());
        textSurname.setText(mStaffAll.getmSurname());
        textFathNam.setText(mStaffAll.getmFathName());
        textPosition.setText(mStaffAll.getmPosition());
        textidNum.setText(mStaffAll.getmIdNum());
        textpasspNum.setText(mStaffAll.getmPasspNum());
        textAddress.setText(mStaffAll.getmAddress());
        textAnyAddress.setText(mStaffAll.getmAnyAddress());
        textTel1.setText(mStaffAll.getmTel1());
        textTel2.setText(mStaffAll.getmTel2());
        if (mStaffAll.getmDateOfBirth() == null){
            textDateOfBirth.setValue(null);
        }else {
            textDateOfBirth.setValue(LocalDate.parse(mStaffAll.getmDateOfBirth()));
        }



        textAddInfo.setText(mStaffAll.getmAddInfo());
        if (mStaffAll.getmTypeWork() == 1){
            checkPed.setSelected(true);
            checkTech.setSelected(false);
        } else if (mStaffAll.getmTypeWork() == 2){
            checkTech.setSelected(true);
            checkPed.setSelected(false);
        }

        collectionListContract.lengthWork(labelLengthWork, labelLengthWorkGeneral);
        String[] yeardaymonth = collectionListContract.getYearmonthday();
        tfOutsideLengthWorkYears.setText(yeardaymonth[0]);
        tfOutsideLengthWorkMonth.setText(yeardaymonth[1]);
        tfOutsideLengthWorkDays.setText(yeardaymonth[2]);


    }

    /*private void refreshLengthWork(){

        Period period = Period.between(LocalDate.parse(contract.getdStart()), LocalDate.now());

        System.out.print(period.getYears() + " years,");
        System.out.print(period.getMonths() + " months,");
        System.out.print(period.getDays() + " days");
        String lengthWor = "Стаж: Годы - " + period.getYears() + ", месяцы - " + period.getMonths() +
                ", дни - " + period.getDays() ;
        contract.setPeriodYear(period.getYears() - contract.getPeriodYear());
        contract.setPeriodMonth(period.getMonths() - contract.getPeriodMonth());
        contract.setPeriodYear(period.getYears() - contract.getPeriodYear());
        labelLengthWork.setText(lengthWor);
    }*/

    private void fillTables(){
        logger.info("fillTables");
        mGetPersonalInformation.downloadInformation(staff.getmIdstaff());
        mStaffAll = mGetPersonalInformation.getStaff();

        mCollectionListEducation.fillData(staff.getmIdstaff());
        tableEducation.setItems(mCollectionListEducation.getEducationList());

        mCollectionListQualifications.fillData(staff.getmIdstaff());
        tableQualifications.setItems(mCollectionListQualifications.getQualificationList());

        mCollectionListChildren.fillData(staff.getmIdstaff());
        tableChildren.setItems(mCollectionListChildren.getChildList());

        collectionListRZO.fillData(staff.getmIdstaff());
        tableRZO.setItems(collectionListRZO.getRZOList());

        collectionListParthnership.fillData(staff.getmIdstaff());
        tableParthnerShip.setItems(collectionListParthnership.getParthnerishipList());

        collectionListHoliday.fillData(staff.getmIdstaff());
        tableHolidays.setItems(collectionListHoliday.getHolidaysList());

        collectionListCategories.fillData(staff.getmIdstaff());
        tableCategory.setItems(collectionListCategories.getCategoriesList());

        collectionListContract.fillData(staff.getmIdstaff());
        tableContracts.setItems(collectionListContract.getListContract());
    }
    /**Handler button pressed*/
    public void actionBtnPersInfo(ActionEvent actionEvent) {
        logger.info("actionBtnPersInfo");
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSave":
                logger.info("btnSave");
                if (!checkValues()){
                    return;
                }
                UpdateStaff();
                if (mHowWasClickButton == 0){
                    logger.info("updateStaff");
                    mGetPersonalInformation.update(mStaffAll);


                }
                if (mHowWasClickButton == 1){
                    logger.info("insertStaff");
                    mGetPersonalInformation.addInDb(mStaffAll);

                    this.staff = mCollectionListStaff.addS(mGetPersonalInformation.getStaff());
                    UpdateMedical();
                    //collectionListContract.insertNewContract(contract, staff.getmIdstaff());
                    objectMedical.insertNewMedical(medical, staff.getmIdstaff());
                    tab2.setDisable(false);
                    tab3.setDisable(false);
                    tab4.setDisable(false);
                    tab5.setDisable(false);
                    tab6.setDisable(false);
                    tab7.setDisable(false);

                    mHowWasClickButton = 0;

                    fillTables();


                }
                break;
            case "btnClose":
                logger.info("btnClose");
                mCollectionListEducation.clearList();
                mCollectionListQualifications.clearList();
                mCollectionListChildren.clearList();
                collectionListRZO.clearList();
                collectionListParthnership.clearList();
                collectionListHoliday.clearList();
                collectionListCategories.clearList();
                collectionListContract.clearList();
                System.out.println("btnClose");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
            case "addNewEducation":
                logger.info("addNewEducation");
                addEducation.setEducation(mCollectionListEducation, staff.getmIdstaff());
                createWindowEducation();
                break;
            case "openEducation":
                logger.info("openEducation");
                addEducation.setEducation(mCollectionListEducation,
                        tableEducation.getSelectionModel().getSelectedItem());
                createWindowEducation();
                break;
            case "delEducation":
                logger.info("delEducation");
                mCollectionListEducation.delEd(tableEducation.getSelectionModel().getSelectedItem());
                break;
            case "addNewQualification":
                logger.info("addNewQualification");
                addQualification.setQualification(mCollectionListQualifications, staff.getmIdstaff());
                createWindowQualification();
                break;
            case "openQualification":
                logger.info("openQualification");
                addQualification.setQualification(mCollectionListQualifications,
                        tableQualifications.getSelectionModel().getSelectedItem());
                createWindowQualification();
                break;
            case "delQualification":
                logger.info("delQualification");
                mCollectionListQualifications.delQualification(tableQualifications.getSelectionModel().getSelectedItem());
                break;
            case "addNewChild":
                logger.info("addNewChild");
                addChild.setChild(mCollectionListChildren, staff.getmIdstaff());
                createWindowChild();
                break;
            case "openChild":
                logger.info("openChild");
                addChild.setChild(mCollectionListChildren,
                        tableChildren.getSelectionModel().getSelectedItem());
                createWindowChild();
                break;
            case "delChild":
                logger.info("delChild");
                mCollectionListChildren.delChild(tableChildren.getSelectionModel().getSelectedItem());
                break;
            case "addNewRZO":
                logger.info("addNewRZO");
                addRZO.setRZO(collectionListRZO, staff.getmIdstaff());
                createWindowRZO();
                break;
            case "openRZO":
                logger.info("openRZO");
                addRZO.setRZO(collectionListRZO,
                        tableRZO.getSelectionModel().getSelectedItem());
                createWindowRZO();
                break;
            case "delRZO":
                logger.info("delRZO");
                collectionListRZO.delRZO(tableRZO.getSelectionModel().getSelectedItem());

                break;
            case "addNewParthnership":
                logger.info("addNewParthnership");
                addParthnership.setParthnership(collectionListParthnership,
                        staff.getmIdstaff());
                createWindowParthnership();
                break;
            case "openParthnership":
                logger.info("openParthnership");
                addParthnership.setParthnership(collectionListParthnership,
                        tableParthnerShip.getSelectionModel().getSelectedItem());
                createWindowParthnership();
                break;
            case "delParthnership":
                logger.info("delParthnership");
                collectionListParthnership.delParthnership(tableParthnerShip.getSelectionModel().getSelectedItem());
                break;
            case "btnOpenContract":
                logger.info("btnOpenContract");
                addContract.setContract(collectionListContract,
                        tableContracts.getSelectionModel().getSelectedItem(), labelLengthWork, labelLengthWork);
                createWindowContract();
                break;
            case "btnAddContract":
                logger.info("btnAddContract");
                addContract.setContract(collectionListContract,
                        staff.getmIdstaff(), labelLengthWork, labelLengthWorkGeneral);
                createWindowContract();
                break;
            case "btnDelContract":
                logger.info("btnDelContract");
                collectionListContract.delContract(tableContracts.getSelectionModel().getSelectedItem());
                collectionListContract.lengthWork(labelLengthWork, labelLengthWorkGeneral);
                break;
            case "saveMedical":
                logger.info("saveMedical");
                UpdateMedical();
                objectMedical.updateMedical(medical, staff.getmIdstaff());
                break;
            case "btnAddHoliday":
                logger.info("btnAddHoliday");
                addHoliday.setHoliday(collectionListHoliday, staff.getmIdstaff());
                createWindowHoliday();
                break;
            case "btnOpenHoliday":
                logger.info("btnOpenHoliday");
                addHoliday.setHoliday(collectionListHoliday,
                        tableHolidays.getSelectionModel().getSelectedItem());
                createWindowHoliday();
                break;
            case "btnDelHoliday":
                logger.info("btnDelHoliday");
                collectionListHoliday.delHoliday(tableHolidays.getSelectionModel().getSelectedItem());
                break;
            case "btnAddCategory":
                logger.info("btnAddCategory");
                addCategory.setCategory(collectionListCategories, staff.getmIdstaff());
                createWindowCategory();
                break;
            case "btnOpenCategory":
                logger.info("btnOpenCategory");
                addCategory.setCategory(collectionListCategories,
                        tableCategory.getSelectionModel().getSelectedItem());
                createWindowCategory();

                break;
            case "btnDelCategory":
                logger.info("btnDelCategory");
                break;
            case "btnSaveOutsideLengthWork":
                logger.info("btnSaveOutsideLengthWork");
                String outsideYears = tfOutsideLengthWorkYears.getText() + "-" + tfOutsideLengthWorkMonth.getText()
                        + "-" + tfOutsideLengthWorkDays.getText();
                mGetPersonalInformation.updateOutsideYears(outsideYears, staff.getmIdstaff());
                collectionListContract.lengthWork(labelLengthWork, labelLengthWorkGeneral);

                break;



        }
    }

    private void UpdateMedical(){
        logger.info("UpdateMedical");
        String date = null, date1 = null;
        if (dpFluoro.getValue() != null)  date = dpFluoro.getValue().toString();
        if (dpAds.getValue() != null) date1 = dpAds.getValue().toString();

        medical.setFluoro(date);
        medical.setAds(date1);
    }

    /**check the value which was install in checkbox*/
    public void checkAction(ActionEvent actionEvent) {
        logger.info("checkAction");
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
        logger.info("checkValues");
        System.out.println(textName.getText().equals(""));
        if (textName.getText().isEmpty() || textSurname.getText().isEmpty() || textFathNam.getText().isEmpty()){
            errorInformation.setText("Ошибка: поля \"Имя\", \"Фамилия\", \"Отчество\" не могут быть пустыми");
            return false;
        }
        if (textidNum.getText().length()>14){
            errorInformation.setText("Ошибка: идентификационный номер не может быть больше 14 символов");
            return false;
        }
        if (textpasspNum.getText().length()>9){
            errorInformation.setText("Ошибка: номер пасспорта не может быть больше 9 символов");
            return false;
        }
        return true;
    }
    /**Update object's staff*/
    private void UpdateStaff(){
        logger.info("UpdateStaff");
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

        mStaffAll.setmName(textName.getText());
        mStaffAll.setmSurname(textSurname.getText());
        mStaffAll.setmFathName(textFathNam.getText());
        mStaffAll.setmPosition(textPosition.getText());
        mStaffAll.setmAddInfo(textAddInfo.getText());
        mStaffAll.setmIdNum(textidNum.getText());
        mStaffAll.setmPasspNum(textpasspNum.getText());
        mStaffAll.setmTel1(textTel1.getText());
        mStaffAll.setmTel2(textTel2.getText());
        if (textDateOfBirth.getValue() == null) {
            mStaffAll.setmDateOfBirth(null);
        }else {
            mStaffAll.setmDateOfBirth(textDateOfBirth.getValue().toString());
        }
        mStaffAll.setmTypeWork(typeWork);
        mStaffAll.setmAddress(textAddress.getText());
        mStaffAll.setmAnyAddress(textAnyAddress.getText());
    }

    private void initLoader(){
        logger.info("initLoader");
        fxmlLoaderEducation.setLocation(getClass().getResource(FXML_EDUCATION));
        fxmlLoaderQualification.setLocation(getClass().getResource(FXML_QUALIFICATION));
        fxmlLoaderChild.setLocation(getClass().getResource(FXML_CHILD));
        fxmlLoaderRZO.setLocation(getClass().getResource(FXML_RZO));
        fxmlLoaderParthner.setLocation(getClass().getResource(FXML_PARTHNERSHIP));
        fxmlLoaderHoliday.setLocation(getClass().getResource(FXML_HOLIDAY));
        fxmlLoaderCategory.setLocation(getClass().getResource(FXML_CATEGORY));
        fxmlLoaderContract.setLocation(getClass().getResource(FXML_CONTRACT));
        try {
            fxmlInfoEducation = fxmlLoaderEducation.load();
            fxmlInfoQualification = fxmlLoaderQualification.load();
            fxmlInfoChild = fxmlLoaderChild.load();
            fxmlInfoRZO = fxmlLoaderRZO.load();
            fxmlInfoParthnerShip = fxmlLoaderParthner.load();
            fxmlInfoHoliday = fxmlLoaderHoliday.load();
            fxmlInfoCategory = fxmlLoaderCategory.load();
            fxmlInfoContract = fxmlLoaderContract.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEducation = fxmlLoaderEducation.getController();
        addQualification = fxmlLoaderQualification.getController();
        addChild = fxmlLoaderChild.getController();
        addRZO = fxmlLoaderRZO.getController();
        addParthnership = fxmlLoaderParthner.getController();
        addHoliday = fxmlLoaderHoliday.getController();
        addCategory = fxmlLoaderCategory.getController();
        addContract = fxmlLoaderContract.getController();
    }

    private void createWindowEducation() {
        logger.info("createWindowEducation");
        if (educationStage == null) {
            educationStage = new Stage();
            educationStage.setTitle("Образование");
            educationStage.setScene(new Scene(fxmlInfoEducation, 600, 330));
            educationStage.initModality(Modality.WINDOW_MODAL);
            educationStage.initOwner(mainStage);
        }
        educationStage.show();
        educationStage.setMinHeight(educationStage.getHeight());
        educationStage.setMinWidth(educationStage.getWidth());
        educationStage.setMaxHeight(educationStage.getHeight());
        educationStage.setMaxWidth(educationStage.getWidth());
    }

    private void createWindowQualification() {
        logger.info("createWindowQualification");
        if (qualificationStage == null) {
            qualificationStage = new Stage();
            qualificationStage.setTitle("Квалификации");
            qualificationStage.setScene(new Scene(fxmlInfoQualification));
            qualificationStage.initModality(Modality.WINDOW_MODAL);
            qualificationStage.initOwner(mainStage);
        }
        qualificationStage.show();
        qualificationStage.setMinHeight(qualificationStage.getHeight());
        qualificationStage.setMinWidth(qualificationStage.getWidth());
        qualificationStage.setMaxHeight(qualificationStage.getHeight());
        qualificationStage.setMaxWidth(qualificationStage.getWidth());
    }

    private void createWindowChild() {
        logger.info("createWindowChild");
        if (childStage == null) {
            childStage = new Stage();
            childStage.setTitle("Ребенок");
            childStage.setScene(new Scene(fxmlInfoChild));
            childStage.initModality(Modality.WINDOW_MODAL);
            childStage.initOwner(mainStage);
        }
        childStage.show();
        childStage.setMinHeight(childStage.getHeight());
        childStage.setMinWidth(childStage.getWidth());
        childStage.setMaxHeight(childStage.getHeight());
        childStage.setMaxWidth(childStage.getWidth());
    }

    private void createWindowRZO() {
        logger.info("createWindowRZO");
        if (rzoStage == null) {
            rzoStage = new Stage();
            rzoStage.setTitle("РЗО");
            rzoStage.setScene(new Scene(fxmlInfoRZO));
            rzoStage.initModality(Modality.WINDOW_MODAL);
            rzoStage.initOwner(mainStage);
        }
        rzoStage.show();
        rzoStage.setMinHeight(rzoStage.getHeight());
        rzoStage.setMinWidth(rzoStage.getWidth());
        rzoStage.setMaxHeight(rzoStage.getHeight());
        rzoStage.setMaxWidth(rzoStage.getWidth());
    }

    private void createWindowParthnership() {
        logger.info("createWindowParthnership");
        if (parthnerShipStage == null) {
            parthnerShipStage = new Stage();
            parthnerShipStage.setTitle("Совместительство");
            parthnerShipStage.setScene(new Scene(fxmlInfoParthnerShip));
            parthnerShipStage.initModality(Modality.WINDOW_MODAL);
            parthnerShipStage.initOwner(mainStage);
        }
        parthnerShipStage.show();
        parthnerShipStage.setMinHeight(parthnerShipStage.getHeight());
        parthnerShipStage.setMinWidth(parthnerShipStage.getWidth());
        parthnerShipStage.setMaxHeight(parthnerShipStage.getHeight());
        parthnerShipStage.setMaxWidth(parthnerShipStage.getWidth());
    }

    private void createWindowHoliday() {
        logger.info("createWindowHoliday");
        if (holidayStage == null) {
            holidayStage = new Stage();
            holidayStage.setTitle("Отпуск");
            holidayStage.setScene(new Scene(fxmlInfoHoliday));
            holidayStage.initModality(Modality.WINDOW_MODAL);
            holidayStage.initOwner(mainStage);
        }
        holidayStage.show();
        holidayStage.setMinHeight(holidayStage.getHeight());
        holidayStage.setMinWidth(holidayStage.getWidth());
        holidayStage.setMaxHeight(holidayStage.getHeight());
        holidayStage.setMaxWidth(holidayStage.getWidth());
    }

    private void createWindowCategory() {
        logger.info("createWindowCategory");
        if (categoryStage == null) {
            categoryStage = new Stage();
            categoryStage.setTitle("Категория");
            categoryStage.setScene(new Scene(fxmlInfoCategory));
            categoryStage.initModality(Modality.WINDOW_MODAL);
            categoryStage.initOwner(mainStage);
        }
        categoryStage.show();
        categoryStage.setMinHeight(categoryStage.getHeight());
        categoryStage.setMinWidth(categoryStage.getWidth());
        categoryStage.setMaxHeight(categoryStage.getHeight());
        categoryStage.setMaxWidth(categoryStage.getWidth());
    }

    private void createWindowContract() {
        logger.info("createWindowContract");
        if (contractStage == null) {
            contractStage = new Stage();
            contractStage.setTitle("Контракт");
            contractStage.setScene(new Scene(fxmlInfoContract));
            contractStage.initModality(Modality.APPLICATION_MODAL);
            contractStage.initOwner(mainStage);
        }
        contractStage.show();
        contractStage.setMinHeight(contractStage.getHeight());
        contractStage.setMinWidth(contractStage.getWidth());
        contractStage.setMaxHeight(contractStage.getHeight());
        contractStage.setMaxWidth(contractStage.getWidth());
    }

    void setMainStage(Stage mainStage) {
        logger.info("setMainStage");
        this.mainStage = mainStage;
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                logger.info("Stage is close");
                mCollectionListEducation.clearList();
                mCollectionListQualifications.clearList();
                mCollectionListChildren.clearList();
                collectionListRZO.clearList();
                collectionListParthnership.clearList();
                collectionListHoliday.clearList();
                collectionListCategories.clearList();
                collectionListContract.clearList();
            }
        });
    }


}
