/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class for save data about staff
 */
public class Staff {
    private SimpleIntegerProperty mIdstaff = new SimpleIntegerProperty();
    private SimpleStringProperty mSurname = new SimpleStringProperty("");
    private SimpleStringProperty mName = new SimpleStringProperty("");
    private SimpleStringProperty mFathName = new SimpleStringProperty("");
    private SimpleStringProperty mDateOfBirth = new SimpleStringProperty("");
    private SimpleStringProperty mIdNum = new SimpleStringProperty("");
    private SimpleStringProperty mPasspNum = new SimpleStringProperty("");
    private SimpleStringProperty mAddress = new SimpleStringProperty("");
    private SimpleStringProperty mAnyAddress = new SimpleStringProperty("");
    private SimpleStringProperty mTel1 = new SimpleStringProperty("");
    private SimpleStringProperty mTel2 = new SimpleStringProperty("");
    private SimpleStringProperty mAddInfo = new SimpleStringProperty("");
    private SimpleIntegerProperty mTypeWork = new SimpleIntegerProperty();
    private SimpleStringProperty mPosition = new SimpleStringProperty("");

    public Staff()
    {

    }

    public Staff(Integer idstaff, String surname, String name, String fathname, String position) {
        this.mIdstaff = new SimpleIntegerProperty(idstaff);
        this.mSurname = new SimpleStringProperty(surname);
        this.mName = new SimpleStringProperty(name);
        this.mFathName = new SimpleStringProperty(fathname);
        this.mPosition = new SimpleStringProperty(position);
    }

    public Staff(Integer idstaff, String surname, String name, String fathname,
                 String dateOfBirth, String idNum, String passpNum,
                 String address, String anyaddress, String tel1, String tel2, String addinfo,
                 Integer typework, String position) {
        this.mIdstaff = new SimpleIntegerProperty(idstaff);
        this.mSurname = new SimpleStringProperty(surname);
        this.mName = new SimpleStringProperty(name);
        this.mFathName = new SimpleStringProperty(fathname);
        this.mDateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.mIdNum = new SimpleStringProperty(idNum);
        this.mPasspNum = new SimpleStringProperty(passpNum);
        this.mAddress = new SimpleStringProperty(address);
        this.mAnyAddress = new SimpleStringProperty(anyaddress);
        this.mTel1 = new SimpleStringProperty(tel1);
        this.mTel2 = new SimpleStringProperty(tel2);
        this.mAddInfo = new SimpleStringProperty(addinfo);
        this.mTypeWork = new SimpleIntegerProperty(typework);
        this.mPosition = new SimpleStringProperty(position);
    }

    public int getmIdstaff() {
        return mIdstaff.get();
    }

    public void setmIdstaff(int mID_staff) {
        this.mIdstaff.set(mID_staff);
    }

    public String getmSurname() {
        return mSurname.get();
    }

    public void setmSurname(String mSurname) {
        this.mSurname.set(mSurname);
    }

    public void setmName(String mName) {
        this.mName.set(mName);
    }

    public String getmName() {
        return mName.get();
    }

    public void setmFathName(String mFathName) {
        this.mFathName.set(mFathName);
    }

    public String getmFathName() {
        return mFathName.get();
    }

    public void setmDateOfBirth(String dateOfBirth) { this.mDateOfBirth.set(dateOfBirth);}

    public String getmDateOfBirth() { return mDateOfBirth.get();}

    public void setmIdNum(String mIdNum) { this.mIdNum.set(mIdNum);}

    public String getmIdNum() { return mIdNum.get();}

    public void setmPasspNum(String mPasspNum) { this.mPasspNum.set(mPasspNum);}

    public String getmPasspNum() {return mPasspNum.get();}

    public void setmAddress(String mAddress) {
        this.mAddress.set(mAddress);
    }

    public String getmAddress() {
        return mAddress.get();
    }

    public void setmAnyAddress(String mAnyAddress) {
        this.mAnyAddress.set(mAnyAddress);
    }

    public String getmAnyAddress() {
        return mAnyAddress.get();
    }

    public void setmTel1(String mTel1) {
        this.mTel1.set(mTel1);
    }

    public String getmTel1() {
        return mTel1.get();
    }

    public void setmTel2(String mTel2) {
        this.mTel2.set(mTel2);
    }

    public String getmTel2() {
        return mTel2.get();
    }

    public void setmAddInfo(String mAddInfo) {
        this.mAddInfo.set(mAddInfo);
    }

    public String getmAddInfo() {
        return mAddInfo.get();
    }

    public void setmTypeWork(int mTypeWork) {
        this.mTypeWork.set(mTypeWork);
    }

    public int getmTypeWork() {
        return mTypeWork.get();
    }

    public void setmPosition(String mPosition) {
        this.mPosition.set(mPosition);
    }

    public String getmPosition() {
        return mPosition.get();
    }

    public SimpleStringProperty mNameProperty() {
        return mName;
    }

    public SimpleStringProperty mSurnameProperty() {
        return mSurname;
    }

    public SimpleStringProperty mFathNameProperty() {
        return mFathName;
    }

    public SimpleStringProperty mPositionProperty() { return mPosition; }

    @Override
    public String toString() {
        return "Person{\n"
                + "Surname = '" + mSurname + "',"
                + "\nName = '" + mName + "',"
                + "\nFather name = '" + mFathName + "',"
                +"\nPosition = '" + mPosition + "';\n}";

    }
}
