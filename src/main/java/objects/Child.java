package objects;

import javafx.beans.property.SimpleStringProperty;

public class Child {

    private SimpleStringProperty mIdChild = new SimpleStringProperty("");
    private SimpleStringProperty mName = new SimpleStringProperty("");
    private SimpleStringProperty mSurname = new SimpleStringProperty("");
    private SimpleStringProperty mFathName = new SimpleStringProperty("");
    private SimpleStringProperty mDateOfBirth = new SimpleStringProperty("");

    public Child(){}

    public Child(String idChild, String name, String surname, String fathName, String dateOfBirth){
        this.mIdChild = new SimpleStringProperty(idChild);
        this.mName = new SimpleStringProperty(name);
        this.mSurname = new SimpleStringProperty(surname);
        this.mFathName = new SimpleStringProperty(fathName);
        this.mDateOfBirth = new SimpleStringProperty(dateOfBirth);
    }

    public void setmIdChild(String mIdChild) { this.mIdChild.set(mIdChild);}

    public String getmIdChild() { return mIdChild.get();}

    public void setmName(String mName) { this.mName.set(mName);}

    public String getmName() { return mName.get();}

    public void setmSurname(String mSurname) { this.mSurname.set(mSurname);}

    public String getmSurname() { return mSurname.get();}

    public void setmFathName(String mFathName) { this.mFathName.set(mFathName);}

    public String getmFathName() { return mFathName.get();}

    public void setmDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth.set(mDateOfBirth);
    }

    public String getmDateOfBirth() {
        return mDateOfBirth.get();
    }

    public SimpleStringProperty mNameProperty() { return mName;}

    public SimpleStringProperty mSurnameProperty() { return mSurname;}

    public SimpleStringProperty mFathNameProperty() { return mFathName;}

    public SimpleStringProperty mDateOfBirthProperty() {
        return mDateOfBirth;}
}
