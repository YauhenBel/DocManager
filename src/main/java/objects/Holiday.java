package objects;

import javafx.beans.property.SimpleStringProperty;

public class Holiday {
    private SimpleStringProperty idHoliday = new SimpleStringProperty("");
    private SimpleStringProperty year = new SimpleStringProperty("");
    private SimpleStringProperty colDays = new SimpleStringProperty("");
    private SimpleStringProperty dStartWork = new SimpleStringProperty("");
    private SimpleStringProperty dFinishWork = new SimpleStringProperty("");
    private SimpleStringProperty dStart = new SimpleStringProperty("");
    private SimpleStringProperty dFinish = new SimpleStringProperty("");
    private SimpleStringProperty dStart1 = new SimpleStringProperty("");
    private SimpleStringProperty dFinish1 = new SimpleStringProperty("");
    private SimpleStringProperty dStart2 = new SimpleStringProperty("");
    private SimpleStringProperty dFinish2 = new SimpleStringProperty("");
    private SimpleStringProperty typeHoliday = new SimpleStringProperty("");
    private SimpleStringProperty saveWage = new SimpleStringProperty("");

    public Holiday(){}

    public Holiday(String idHoliday, String year, String colDays, String dStartWork, String dFinishWork,
                   String dStart, String dFinish, String dStart1, String dFinish1, String dStart2, String dFinish2,
                   String typeHoliday, String saveWage){
        this.idHoliday = new SimpleStringProperty(idHoliday);
        this.year = new SimpleStringProperty(year);
        this.colDays = new SimpleStringProperty(colDays);
        this.dStartWork = new SimpleStringProperty(dStartWork);
        this.dFinishWork = new SimpleStringProperty(dFinishWork);
        this.dStart = new SimpleStringProperty(dStart);
        this.dFinish = new SimpleStringProperty(dFinish);
        this.dStart1 = new SimpleStringProperty(dStart1);
        this.dFinish1 = new SimpleStringProperty(dFinish1);
        this.dStart2 = new SimpleStringProperty(dStart2);
        this.dFinish2 = new SimpleStringProperty(dFinish2);
        this.typeHoliday = new SimpleStringProperty(typeHoliday);
        this.saveWage = new SimpleStringProperty(saveWage);
    }

    public void setIdHoliday(String idHoliday) { this.idHoliday.set(idHoliday);}

    public String getIdHoliday() { return idHoliday.get();}

    public void setYear(String year) { this.year.set(year);}

    public String getYear() { return year.get();}

    public void setColDays(String colDays) { this.colDays.set(colDays);}

    public String getColDays() { return colDays.get();}

    public void setdStartWork(String dStartWork) { this.dStartWork.set(dStartWork);}

    public String getdStartWork() { return dStartWork.get();}

    public void setdFinishWork(String dFinishWork) { this.dFinishWork.set(dFinishWork);}

    public String getdFinishWork() { return dFinishWork.get();}

    public void setdStart(String dStart) { this.dStart.set(dStart);}

    public String getdStart() { return dStart.get();}

    public void setdFinish(String dFinish) { this.dFinish.set(dFinish);}

    public String getdFinish() { return dFinish.get();}

    public void setdStart1(String dStart1) { this.dStart1.set(dStart1);}

    public String getdStart1() { return dStart1.get();}

    public void setdFinish1(String dFinish1) { this.dFinish1.set(dFinish1);}

    public String getdFinish1() { return dFinish1.get();}

    public void setdStart2(String dStart2) { this.dStart2.set(dStart2);}

    public String getdStart2() { return dStart2.get();}

    public void setdFinish2(String dFinish2) { this.dFinish2.set(dFinish2);}

    public String getdFinish2() { return dFinish2.get();}

    public void setTypeHoliday(String typeHoliday) { this.typeHoliday.set(typeHoliday);}

    public String getTypeHoliday() { return typeHoliday.get();}

    public void setSaveWage(String saveWage) { this.saveWage.set(saveWage);}

    public String getSaveWage() { return saveWage.get();}

    public SimpleStringProperty typeHolidayProperty() { return typeHoliday;}

    public SimpleStringProperty saveWageProperty() { return saveWage;}

    public SimpleStringProperty dStartWorkProperty() { return dStartWork;}

    public SimpleStringProperty dFinishWorkProperty() { return dFinishWork;}

    public SimpleStringProperty dStartProperty() { return dStart;}

    public SimpleStringProperty dFinishProperty() { return dFinish;}

    public SimpleStringProperty dStart1Property() { return dStart1;}

    public SimpleStringProperty dFinish1Property() { return dFinish1;}
}




