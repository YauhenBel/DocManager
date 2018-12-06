package objects;

import javafx.beans.property.SimpleStringProperty;

public class Medical {

    private SimpleStringProperty fluoro = new SimpleStringProperty("");
    private SimpleStringProperty ads = new SimpleStringProperty("");

    public Medical(){}

    public Medical(String fluoro, String ads){
        this.fluoro = new SimpleStringProperty(fluoro);
        this.ads = new SimpleStringProperty(ads);
    }

    public void setFluoro(String fluoro) { this.fluoro.set(fluoro);}

    public String getFluoro() { return fluoro.get();}

    public void setAds(String ads) { this.ads.set(ads);}

    public String getAds() { return ads.get();}
}
