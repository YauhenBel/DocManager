package objects;

import javafx.beans.property.SimpleStringProperty;

public class Education {

    private SimpleStringProperty mIdEd = new SimpleStringProperty("");
    private SimpleStringProperty mNameEd = new SimpleStringProperty("");
    private SimpleStringProperty mDateFinish = new SimpleStringProperty("");
    private SimpleStringProperty mSpecialty = new SimpleStringProperty("");
    private SimpleStringProperty mQualification = new SimpleStringProperty("");
    private SimpleStringProperty levelEducation = new SimpleStringProperty("");

    public Education(){}

    public Education(String id_ed, String name_ed, String d_finish, String specialty, String qualification,
    String levelEducation){
        this.mIdEd = new SimpleStringProperty(id_ed);
        this.mNameEd = new SimpleStringProperty(name_ed);
        this.mDateFinish = new SimpleStringProperty(d_finish);
        this.mSpecialty = new SimpleStringProperty(specialty);
        this.mQualification = new SimpleStringProperty(qualification);
        this.levelEducation = new SimpleStringProperty(levelEducation);
    }

    public void setmIdEd(String mId_ed) { this.mIdEd.set(mId_ed);}

    public String getmIdEd() { return mIdEd.get();}

    public void setmNameEd(String mName_ed) { this.mNameEd.set(mName_ed);}

    public String getmNameEd() { return mNameEd.get();}

    public void setmDateFinish(String mD_finish) { this.mDateFinish.set(mD_finish);}

    public String getmDateFinish() { return mDateFinish.get();}

    public void setmSpecialty(String mSpecialty) { this.mSpecialty.set(mSpecialty);}

    public String getmSpecialty() { return mSpecialty.get();}

    public void setmQualification(String mQualification) { this.mQualification.set(mQualification);}

    public String getmQualification() { return mQualification.get();}

    public void setLevelEducation(String levelEducation) { this.levelEducation.set(levelEducation);}

    public String getLevelEducation() { return levelEducation.get();}

    public SimpleStringProperty mNameEdProperty() { return mNameEd;}

    public SimpleStringProperty mDateFinishProperty() { return mDateFinish;}

    public SimpleStringProperty mSpecialtyProperty() { return mSpecialty;}
}
