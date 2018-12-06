package objects;

import javafx.beans.property.SimpleStringProperty;

public class Parthneriship {

    private SimpleStringProperty idParth	 = new SimpleStringProperty("");
    private SimpleStringProperty dStart = new SimpleStringProperty("");
    private SimpleStringProperty dFinish	 = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");
    private SimpleStringProperty type1 = new SimpleStringProperty("");
    private SimpleStringProperty position = new SimpleStringProperty("");

    public Parthneriship(){}

    public Parthneriship(String idParth, String dStart, String dFinish,
               String type, String type1, String position){
        this.idParth = new SimpleStringProperty(idParth);
        this.dStart = new SimpleStringProperty(dStart);
        this.dFinish = new SimpleStringProperty(dFinish);
        this.type = new SimpleStringProperty(type);
        this.type1 = new SimpleStringProperty(type1);
        this.position = new SimpleStringProperty(position);
    }

    public void setIdParth(String idParth) { this.idParth.set(idParth);}

    public String getIdParth() { return idParth.get();}

    public void setdStart(String dStart) { this.dStart.set(dStart);}

    public String getdStart() { return dStart.get();}

    public void setdFinish(String dFinish) { this.dFinish.set(dFinish);}

    public String getdFinish() { return dFinish.get();}

    public void setType(String type) { this.type.set(type);}

    public String getType() { return type.get();}

    public void setType1(String type1) { this.type1.set(type1);}

    public String getType1() { return type1.get();}

    public void setPosition(String position) { this.position.set(position);}

    public String getPosition() { return position.get();}

    public SimpleStringProperty dStartProperty() { return dStart;}

    public SimpleStringProperty dFinishProperty() { return dFinish;}

    public SimpleStringProperty type1Property() { return type1;}

    public SimpleStringProperty positionProperty() { return position;}
}
