package objects;

import javafx.beans.property.SimpleStringProperty;

public class Qualification {
    private SimpleStringProperty mIdTrai = new SimpleStringProperty("");
    private SimpleStringProperty mWhat = new SimpleStringProperty("");
    private SimpleStringProperty mDate = new SimpleStringProperty("");
    private SimpleStringProperty mHours = new SimpleStringProperty("");
    private SimpleStringProperty mTheme = new SimpleStringProperty("");

    public Qualification(){}

    public Qualification(String idTrai, String what, String date, String hours, String theme){
        this.mIdTrai = new SimpleStringProperty(idTrai);
        this.mWhat = new SimpleStringProperty(what);
        this.mDate = new SimpleStringProperty(date);
        this.mHours = new SimpleStringProperty(hours);
        this.mTheme = new SimpleStringProperty(theme);
    }

    public void setmIdTrai(String mIdTrai) { this.mIdTrai.set(mIdTrai);}

    public String getmIdTrai() { return mIdTrai.get();}

    public void setmWhat(String mWhat) { this.mWhat.set(mWhat);}

    public String getmWhat() { return mWhat.get();}

    public void setmDate(String mDate) { this.mDate.set(mDate);}

    public String getmDate() { return mDate.get();}

    public void setmHours(String mHours) { this.mHours.set(mHours);}

    public String getmHours() { return mHours.get();}

    public void setmTheme(String mTheme) { this.mTheme.set(mTheme);}

    public String getmTheme() { return mTheme.get();}

    public SimpleStringProperty mWhatProperty() { return mWhat;}

    public SimpleStringProperty mDateProperty() { return mDate;}

    public SimpleStringProperty mHoursProperty() { return mHours;}
}
