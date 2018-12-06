package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contract {
    private SimpleIntegerProperty idContract = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty idStaff = new SimpleIntegerProperty(0);
    private SimpleStringProperty type = new SimpleStringProperty("");
    private SimpleStringProperty dStart = new SimpleStringProperty("");
    private SimpleStringProperty dFinish = new SimpleStringProperty("");
    private SimpleStringProperty discharge = new SimpleStringProperty("");


    public Contract(){}

    public Contract(Integer idContract,  Integer idStaff, String  type, String dStart, String dFinish, String discharge){
        this.idContract = new SimpleIntegerProperty(idContract);
        this.idStaff = new SimpleIntegerProperty(idStaff);
        this.type = new SimpleStringProperty(type);
        this.dStart = new SimpleStringProperty(dStart);
        this.dFinish = new SimpleStringProperty(dFinish);
        this.discharge = new SimpleStringProperty(discharge);
    }

    public void setIdContract(int idContract) { this.idContract.set(idContract);}

    public int getIdContract() { return idContract.get();}

    public void setIdStaff(int idStaff) { this.idStaff.set(idStaff);}

    public int getIdStaff() { return idStaff.get();}

    public void setType(String type) { this.type.set(type);}

    public String getType() { return type.get();}

    public void setdStart(String dStart) { this.dStart.set(dStart);}

    public String getdStart() { return dStart.get();}

    public void setdFinish(String dFinish) { this.dFinish.set(dFinish);}

    public String getdFinish() { return dFinish.get();}

    public void setDischarge(String discharge) { this.discharge.set(discharge);}

    public String getDischarge() { return discharge.get(); }

    public SimpleStringProperty typeProperty() { return type;}

    public SimpleStringProperty dStartProperty() { return dStart;}

    public SimpleStringProperty dFinishProperty() { return dFinish;}
}
