package objects;

import javafx.beans.property.SimpleStringProperty;

public class RZO {

    private SimpleStringProperty idTech = new SimpleStringProperty("");
    private SimpleStringProperty dateStart = new SimpleStringProperty("");
    private SimpleStringProperty dateFinish	 = new SimpleStringProperty("");
    private SimpleStringProperty percent = new SimpleStringProperty("");
    private SimpleStringProperty proff = new SimpleStringProperty("");
    private SimpleStringProperty dateOrder = new SimpleStringProperty("");
    private SimpleStringProperty numOrder = new SimpleStringProperty("");

    public RZO(){}

    public RZO(String idTech, String dateStart, String dateFinish,
               String percent, String proff, String dateOrder, String numOrder){
        this.idTech = new SimpleStringProperty(idTech);
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateFinish = new SimpleStringProperty(dateFinish);
        this.percent = new SimpleStringProperty(percent);
        this.proff = new SimpleStringProperty(proff);
        this.dateOrder = new SimpleStringProperty(dateOrder);
        this.numOrder = new SimpleStringProperty(numOrder);
    }

    public void setIdTech(String idTech) { this.idTech.set(idTech);}

    public String getIdTech() { return idTech.get();}

    public void setDateStart(String dateStart) { this.dateStart.set(dateStart);}

    public String getDateStart() { return dateStart.get();}

    public void setDateFinish(String dateFinish) { this.dateFinish.set(dateFinish);}

    public String getDateFinish() { return dateFinish.get();}

    public void setPercent(String percent) { this.percent.set(percent);}

    public String getPercent() { return percent.get();}

    public void setProff(String proff) { this.proff.set(proff);}

    public String getProff() { return proff.get();}

    public void setDateOrder(String dateOrder) { this.dateOrder.set(dateOrder);}

    public String getDateOrder() { return dateOrder.get();}

    public void setNumOrder(String numOrder) { this.numOrder.set(numOrder);}

    public String getNumOrder() { return numOrder.get();}

    public SimpleStringProperty proffProperty() { return proff;}

    public SimpleStringProperty percentProperty() { return percent;}

    public SimpleStringProperty dateStartProperty() { return dateStart;}

    public SimpleStringProperty dateFinishProperty() { return dateFinish;}
}
