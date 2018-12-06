package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Filter {

    SimpleStringProperty yearFrom = new SimpleStringProperty("");
    SimpleStringProperty yearTo = new SimpleStringProperty("");
    SimpleStringProperty lengthWorkFrom = new SimpleStringProperty("");
    SimpleStringProperty lengthWorkTo = new SimpleStringProperty("");
    SimpleStringProperty formContract = new SimpleStringProperty("");
    SimpleStringProperty levelCategory = new SimpleStringProperty("");
    SimpleStringProperty levelEducation = new SimpleStringProperty("");
    SimpleStringProperty holiday = new SimpleStringProperty("");
    SimpleBooleanProperty generalLengthWork = new SimpleBooleanProperty(false);


    public Filter(){}

    public Filter(String yearFrom, String yearTo, String lengthWorkFrom, String lengthWorkTo, String formContract,
                  String levelCategory, String levelEducation, String holiday, Boolean generalLengthWork){
        this.yearFrom = new SimpleStringProperty(yearFrom);
        this.yearTo = new SimpleStringProperty(yearTo);
        this.lengthWorkFrom = new SimpleStringProperty(lengthWorkFrom);
        this.lengthWorkTo = new SimpleStringProperty(lengthWorkTo);
        this.formContract = new SimpleStringProperty(formContract);
        this.levelCategory = new SimpleStringProperty(levelCategory);
        this.levelEducation = new SimpleStringProperty(levelEducation);
        this.holiday = new SimpleStringProperty(holiday);
        this.generalLengthWork = new SimpleBooleanProperty(generalLengthWork);

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

    public void printInfo(){
        System.out.println("Filter:"
        + "\nВозраст: от " + this.yearFrom.get() + " до " + this.yearTo.get()
        + "\nСтаж работы: от " + this.lengthWorkFrom.get() + " до " + this.lengthWorkTo.get()
        + "\nФорма найма: " + this.formContract.get()
        + "\nКатегория: " + this.levelCategory.get()
        + "\nОбразование: " + this.levelEducation.get()
        + "\nОтпуск: " + this.holiday.get());
    }
}
