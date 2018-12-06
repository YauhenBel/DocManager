package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PeriodWork {
    private SimpleIntegerProperty idStaff = new SimpleIntegerProperty(0);
    private SimpleStringProperty dStart = new SimpleStringProperty("");
    private SimpleIntegerProperty periodYear = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty periodMonth = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty periodDays = new SimpleIntegerProperty(0);

    public PeriodWork(){}

    public PeriodWork(Integer idStaff, String dStart, Integer periodYear, Integer periodMonth, Integer periodDays){
        this.idStaff = new SimpleIntegerProperty(idStaff);
        this.dStart = new SimpleStringProperty(dStart);
        this.periodYear = new SimpleIntegerProperty(periodYear);
        this.periodMonth = new SimpleIntegerProperty(periodMonth);
        this.periodDays = new SimpleIntegerProperty(periodDays);
    }

    public void setIdStaff(int idStaff) { this.idStaff.set(idStaff);}

    public int getIdStaff() { return idStaff.get();}

    public void setdStart(String dStart) { this.dStart.set(dStart);}

    public String getdStart() { return dStart.get();}

    public void setPeriodYear(int periodYear) { this.periodYear.set(periodYear);}

    public int getPeriodYear() { return periodYear.get();}

    public void setPeriodMonth(int periodMonth) { this.periodMonth.set(periodMonth);}

    public int getPeriodMonth() { return periodMonth.get();}

    public void setPeriodDays(int periodDays) { this.periodDays.set(periodDays);}

    public int getPeriodDays() { return periodDays.get();}
}
