package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filter {

    private SimpleStringProperty yearFrom = new SimpleStringProperty("");
    private SimpleStringProperty yearTo = new SimpleStringProperty("");
    private SimpleStringProperty lengthWorkFrom = new SimpleStringProperty("");
    private SimpleStringProperty lengthWorkTo = new SimpleStringProperty("");
    private SimpleStringProperty formContract = new SimpleStringProperty("");
    private SimpleStringProperty levelCategory = new SimpleStringProperty("");
    private SimpleStringProperty levelEducation = new SimpleStringProperty("");
    private SimpleStringProperty holiday = new SimpleStringProperty("");
    private SimpleBooleanProperty generalLengthWork = new SimpleBooleanProperty(false);
    private Boolean useCategory = false;
    private Boolean useChildren = false;
    private Boolean useContacts = false;
    private Boolean useContracts = false;
    private Boolean useDateOfBirth = false;
    private Boolean useDocuments = false;
    private Boolean useEducation = false;
    private Boolean useHolidays = false;
    private Boolean useInformation = false;
    private Boolean useLengthWork = false;
    private Boolean useMedical = false;
    private Boolean useParthnerShip = false;
    private Boolean useStaff = false;
    private Boolean useTech_Staff = false;
    private Boolean useTraining = false;

    private Boolean isCreateExcel = false;

    private String listGetDatas = "";


    public Filter(){}

    public Filter(CustomTextField tfAgeFrom, CustomTextField tfAgeTo, CustomTextField tfLengthWorkFrom, CustomTextField tfLengthWorkTo,
                  ComboBox cbFormContract, ComboBox cbEducation, ComboBox cbCategory, ComboBox cbHoliday, CheckBox cbGeneralLengthWork){



        this.yearFrom = new SimpleStringProperty(tfAgeFrom.getText());
        this.yearTo = new SimpleStringProperty(tfAgeTo.getText());
        this.lengthWorkFrom = new SimpleStringProperty(tfLengthWorkFrom.getText());
        this.lengthWorkTo = new SimpleStringProperty(tfLengthWorkTo.getText());
        if (cbFormContract.getValue() == null) {
            this.formContract = new SimpleStringProperty(null);
        }else {
            this.formContract = new SimpleStringProperty(cbFormContract.getValue().toString());
        }
        if (cbEducation.getValue() == null){
            this.levelEducation = new SimpleStringProperty(null);
        }else {
            this.levelEducation = new SimpleStringProperty(cbEducation.getValue().toString());
        }
        if (cbCategory.getValue() == null){
            this.levelCategory = new SimpleStringProperty(null);
        }else {
            this.levelCategory = new SimpleStringProperty(cbCategory.getValue().toString());
        }
        if (cbHoliday.getValue() == null){
            this.holiday = new SimpleStringProperty(null);
        }else {
            this.holiday = new SimpleStringProperty(cbHoliday.getValue().toString());
        }
        if (!cbGeneralLengthWork.isSelected()){
            this.generalLengthWork = new SimpleBooleanProperty(false);
        }else {
            this.generalLengthWork = new SimpleBooleanProperty(true);
        }




    }

    public void setYearFrom(String yearFrom) { this.yearFrom.set(yearFrom);}

    public String getYearFrom() { return yearFrom.get();}

    public void setYearTo(String yearTo) { this.yearTo.set(yearTo);}

    public String getYearTo() { return yearTo.get();}

    public void setLengthWorkFrom(String lengthWorkFrom) { this.lengthWorkFrom.set(lengthWorkFrom);}

    public String getLengthWorkFrom() { return lengthWorkFrom.get();}

    public void setLengthWorkTo(String lengthWorkTo) { this.lengthWorkTo.set(lengthWorkTo);}

    public String getLengthWorkTo() { return lengthWorkTo.get();}

    public void setFormContract(String formContract) { this.formContract.set(formContract);}

    public String getFormContract() { return formContract.get();}

    public void setLevelCategory(String levelCategory) { this.levelCategory.set(levelCategory);}

    public String getLevelCategory() { return levelCategory.get();}

    public void setLevelEducation(String levelEducation) { this.levelEducation.set(levelEducation);}

    public String getLevelEducation() { return levelEducation.get();}

    public void setHoliday(String holiday) { this.holiday.set(holiday);}

    public String getHoliday() { return holiday.get();}

    public void setGeneralLengthWork(boolean generalLengthWork) { this.generalLengthWork.set(generalLengthWork);}

    public boolean isGeneralLengthWork() { return generalLengthWork.get();}

    public void setData(){

    }

    public void printInfo(){
        System.out.println("Filter:"
        + "\nВозраст: от " + this.yearFrom.get() + " до " + this.yearTo.get()
        + "\nСтаж работы: от " + this.lengthWorkFrom.get() + " до " + this.lengthWorkTo.get()
        + "\nФорма найма: " + this.formContract.get()
        + "\nКатегория: " + this.levelCategory.get()
        + "\nОбразование: " + this.levelEducation.get()
        + "\nОтпуск: " + this.holiday.get()
        + "\nРаспечатать следующую информацию:"
        + "\nФИО: " + this.useStaff
        + "\nКонтракт: " + this.useContracts
        + "\nКатеогрия: " + this.useCategory
        );
    }



    public Boolean getCreateExcel() {
        return isCreateExcel;
    }

    public void setCreateExcel(Boolean createExcel) {
        isCreateExcel = createExcel;
    }

    public Boolean getUseCategory() {
        return useCategory;
    }

    public void setUseCategory(Boolean useCategory) {
        this.useCategory = useCategory;
    }

    public Boolean getUseChildren() {
        return useChildren;
    }

    public void setUseChildren(Boolean useChildren) {
        this.useChildren = useChildren;
    }

    public Boolean getUseContacts() {
        return useContacts;
    }

    public void setUseContacts(Boolean useContacts) {
        this.useContacts = useContacts;
    }

    public Boolean getUseContracts() {
        return useContracts;
    }

    public void setUseContracts(Boolean useContracts) {
        this.useContracts = useContracts;
    }

    public Boolean getUseDocuments() {
        return useDocuments;
    }

    public void setUseDocuments(Boolean useDocuments) {
        this.useDocuments = useDocuments;
    }

    public Boolean getUseEducation() {
        return useEducation;
    }

    public void setUseEducation(Boolean useEducation) {
        this.useEducation = useEducation;
    }

    public Boolean getUseHolidays() {
        return useHolidays;
    }

    public void setUseHolidays(Boolean useHolidays) {
        this.useHolidays = useHolidays;
    }

    public Boolean getUseInformation() {
        return useInformation;
    }

    public void setUseInformation(Boolean useInformation) {
        this.useInformation = useInformation;
    }

    public Boolean getUseLengthWork() {
        return useLengthWork;
    }

    public void setUseLengthWork(Boolean useLengthWork) {
        this.useLengthWork = useLengthWork;
    }

    public Boolean getUseMedical() {
        return useMedical;
    }

    public void setUseMedical(Boolean useMedical) {
        this.useMedical = useMedical;
    }

    public Boolean getUseStaff() {
        return useStaff;
    }

    public void setUseStaff(Boolean useStaff) {
        this.useStaff = useStaff;
    }

    public Boolean getUseTech_Staff() {
        return useTech_Staff;
    }

    public void setUseTech_Staff(Boolean useTech_Staff) {
        this.useTech_Staff = useTech_Staff;
    }

    public Boolean getUseTraining() {
        return useTraining;
    }

    public void setUseTraining(Boolean useTraining) {
        this.useTraining = useTraining;
    }

    public Boolean getUseDateOfBirth() {
        return useDateOfBirth;
    }

    public void setUseDateOfBirth(Boolean useDateOfBirth) {
        this.useDateOfBirth = useDateOfBirth;
    }

    public Boolean getUseParthnerShip() {
        return useParthnerShip;
    }

    public void setUseParthnerShip(Boolean useParthnerShip) {
        this.useParthnerShip = useParthnerShip;
    }

    public String getListGetDatas() {
        return listGetDatas;
    }

    public void setListGetDatas(String listGetDatas) {
        this.listGetDatas = listGetDatas;
    }

    public void addTextToString(String str){
        listGetDatas+=str;
    }
}
