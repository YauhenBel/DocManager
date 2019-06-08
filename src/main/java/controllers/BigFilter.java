package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import objects.Filter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BigFilter implements Initializable {

    public CustomTextField tfAgeFrom;
    public CustomTextField tfAgeTo;
    public CustomTextField tfLengthWorkFrom;
    public CustomTextField tfLengthWorkTo;
    public ComboBox<String> cbFormContract;
    public ComboBox<String> cbEducation;
    public ComboBox<String> cbCategory;
    public ComboBox<String> cbHoliday;
    public CheckBox cbGeneralLengthWork;
    public CheckBox chbFIO;
    public CheckBox chbFormContract;
    public CheckBox chbCategory;
    public CheckBox chbCreateExcel;
    private Filter filter;
    private static Logger logger = LogManager.getLogger();

    ObservableList<String> typeContract =
            FXCollections.observableArrayList(
                    "Контракт",
                    "Договор подряда",
                    "Трудовой договор"
            );

    ObservableList<String> levelCategories =
            FXCollections.observableArrayList(
                    "Без категории",
                    "Вторая",
                    "Высшая",
                    "Первая",
                    "Учитель методист"
            );

    ObservableList<String> levelEducation =
            FXCollections.observableArrayList(
                    "Высшее",
                    "Высшее педагогическое",
                    "ССО(пед)",
                    "ССО",
                    "ПТО"
            );

    ObservableList<String> types =
            FXCollections.observableArrayList(
                    "Трудовой",
                    "Социальный",
                    "Декретный"
            );


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbCategory.setItems(levelCategories);
        cbEducation.setItems(levelEducation);
        cbFormContract.setItems(typeContract);
        cbHoliday.setItems(types);

        tfAgeFrom.clear();
        tfAgeTo.clear();
        tfLengthWorkFrom.clear();
        tfLengthWorkTo.clear();
        cbFormContract.setValue(null);
        cbEducation.setValue(null);
        cbCategory.setValue(null);
        cbHoliday.setValue(null);
        cbGeneralLengthWork.setSelected(false);


    }

    public void clickButton(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        Button button = (Button) object;
        switch (button.getId()){
            case "filterIsReady":
                getFilter();
                clearFilter();
                stage.close();
                break;

            case "goOut":
                clearFilter();
                stage.close();
                break;


        }
    }

    private void getFilter(){
        logger.info("getFilter");
        filter = new Filter(
                tfAgeFrom,
                tfAgeTo,
                tfLengthWorkFrom,
                tfLengthWorkTo,
                cbFormContract,
                cbEducation,
                cbCategory,
                cbHoliday,
                cbGeneralLengthWork
        );

        if (chbFIO.isSelected()) filter.setUseStaff(true);
        if (chbFormContract.isSelected()) filter.setUseContracts(true);
        if (chbCategory.isSelected()) filter.setUseCategory(true);
        if (chbCreateExcel.isSelected()) filter.setCreateExcel(true);
        filter.printInfo();


    }

    private void clearFilter(){
        tfAgeFrom.clear();
        tfAgeTo.clear();
        tfLengthWorkFrom.clear();
        tfLengthWorkTo.clear();
        cbFormContract.setValue(null);
        cbEducation.setValue(null);
        cbCategory.setValue(null);
        cbHoliday.setValue(null);
        cbGeneralLengthWork.setSelected(false);
    }

    public Filter getBigFilter(){
        return filter;
    }

    /*public void set(String text){
        logger.info(text);
        tfAgeFrom.setText("hh");

    }*/
}
