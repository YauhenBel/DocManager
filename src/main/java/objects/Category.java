package objects;

import javafx.beans.property.SimpleStringProperty;

public class Category {

    private SimpleStringProperty idCategory = new SimpleStringProperty("");
    private SimpleStringProperty typeCategory = new SimpleStringProperty("");
    private SimpleStringProperty levelCategory = new SimpleStringProperty("");
    private SimpleStringProperty dateCategory = new SimpleStringProperty("");

    public Category(){}

    public Category(String idCategory, String typeCategory, String levelCategory, String dateCategory){
        this.idCategory = new SimpleStringProperty(idCategory);
        this.typeCategory = new SimpleStringProperty(typeCategory);
        this.levelCategory = new SimpleStringProperty(levelCategory);
        this.dateCategory = new SimpleStringProperty(dateCategory);
    }

    public void setIdCategory(String idCategory) { this.idCategory.set(idCategory);}

    public String getIdCategory() { return idCategory.get();}

    public void setTypeCategory(String typeCategory) { this.typeCategory.set(typeCategory);}

    public String getTypeCategory() { return typeCategory.get();}

    public void setLevelCategory(String levelCategory) { this.levelCategory.set(levelCategory);}

    public String getLevelCategory() { return levelCategory.get();}

    public void setDateCategory(String dateCategory) { this.dateCategory.set(dateCategory);}

    public String getDateCategory() { return dateCategory.get();}

    public SimpleStringProperty typeCategoryProperty() {return typeCategory;}

    public SimpleStringProperty levelCategoryProperty() { return levelCategory;}

    public SimpleStringProperty dateCategoryProperty() { return dateCategory;}
}
