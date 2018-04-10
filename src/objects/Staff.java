package objects;

import javafx.beans.property.SimpleStringProperty;

public class Staff {
    //ИД сотрудника
    private SimpleStringProperty ID_staff = new SimpleStringProperty();
    //Фамилия
    private SimpleStringProperty Surname = new SimpleStringProperty();
    //Имя
    private SimpleStringProperty Name = new SimpleStringProperty();
    //Отчество
    private SimpleStringProperty FathName = new SimpleStringProperty();
    //Дата рождения
    private SimpleStringProperty DataOfBirth = new SimpleStringProperty();
    //Номер пасспорта
    private SimpleStringProperty NumPass = new SimpleStringProperty();
    //Идентификационный номер
    private SimpleStringProperty NumPrivate = new SimpleStringProperty();
    //Адрес
    private SimpleStringProperty Address = new SimpleStringProperty();
    //Первый номер телефона
    private SimpleStringProperty Tel1 = new SimpleStringProperty();
    //Второй номер телефона
    private SimpleStringProperty Tel2 = new SimpleStringProperty();
    //Дополнительная информация
    private SimpleStringProperty AddInfo = new SimpleStringProperty();
    //Тех/Пед персонал
    private SimpleStringProperty TypeWork = new SimpleStringProperty();
    //Должность
    private SimpleStringProperty Position = new SimpleStringProperty();


    public Staff()
    {

    }

    public Staff(String idstaff, String surname, String name, String fathname,
                 String dataOfbirth, String numpass,
                 String numprivate, String address, String tel1,
                 String tel2, String addinfo, String typework,
                 String position) {
        this.ID_staff = new SimpleStringProperty(idstaff);
        this.Surname = new SimpleStringProperty(surname);
        this.Name = new SimpleStringProperty(name);
        this.FathName = new SimpleStringProperty(fathname);
        this.DataOfBirth = new SimpleStringProperty(dataOfbirth);
        this.NumPass = new SimpleStringProperty(numpass);
        this.NumPrivate = new SimpleStringProperty(numprivate);
        this.Address = new SimpleStringProperty(address);
        this.Tel1 = new SimpleStringProperty(tel1);
        this.Tel2 = new SimpleStringProperty(tel2);
        this.AddInfo = new SimpleStringProperty(addinfo);
        this.TypeWork = new SimpleStringProperty(typework);
        this.Position = new SimpleStringProperty(position);
    }

    public Staff(String surname, String name, String fathname,
                 String position) {
        this.Surname = new SimpleStringProperty(surname);
        this.Name = new SimpleStringProperty(name);
        this.FathName = new SimpleStringProperty(fathname);
        this.Position = new SimpleStringProperty(position);
    }

    public String getID_staff() {
        return ID_staff.get();
    }

    public void setID_staff(String ID_staff) {
        this.ID_staff.set(ID_staff);
    }

    public String getSurname(){return Surname.get();}

    public void setSurname(String surname){this.Surname.set(surname);}

    public String getName(){return Name.get();}

    public void setName(String name){this.Name.set(name);}


    public String getFathName() {
        return FathName.get();
    }

    public void setFathName(String fathName) {
        this.FathName.set(fathName);
    }

    public String getDateOfBirth() {return this.DataOfBirth.get();}

    public void setDataOfBirth(String dataOfBirth) {
        this.DataOfBirth.set(dataOfBirth);
    }

    public String getNummPass() {return NumPass.get();}

    public void setNumPass(String numPass) {
        this.NumPass.set(numPass);
    }

    public String getNumPrivate() { return NumPrivate.get(); }

    public void setNumPrivate(String numPrivate) {
        this.NumPrivate.set(numPrivate);
    }

    public String getAddress() { return Address.get(); }

    public void setAddress(String address) {
        this.Address.set(address);
    }

    public String getTel1() { return Tel1.get(); }

    public void setTel1(String tel1) {
        this.Tel1.set(tel1);
    }

    public String getTel2() { return Tel2.get(); }

    public void setTel2(String tel2) {
        this.Tel2.set(tel2);
    }

    public String getAddInfo() { return AddInfo.get(); }

    public void setAddInfo(String addInfo) {
        this.AddInfo.set(addInfo);
    }

    public String getTypeWork() { return TypeWork.get(); }

    public void setTypeWork(String typeWork) {
        this.TypeWork.set(typeWork);
    }

    public String getPosition() {
        return Position.get();
    }

    public void setPosition(String position) {
        this.Position.set(position);
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public SimpleStringProperty surnameProperty() {
        return Surname;
    }

    public SimpleStringProperty fathNameProperty() {
        return FathName;
    }

    public SimpleStringProperty positionProperty() {
        return Position;
    }

    @Override
    public String toString() {
        return "Person{\n"
                + "Surname = '" + Surname + "',"
                + "\nName = '" + Name + "',"
                + "\nFather name = '" + FathName + "',"
                +"\nPosition = '" + Position + "';\n}";

    }
}
