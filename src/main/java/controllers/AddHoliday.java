package controllers;

import Other.SetupClearButtonField;
import interfaces.CollectionListHoliday;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import objects.Holiday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddHoliday implements Initializable {
    @FXML public ComboBox<String> cbTypeHoliday;
    @FXML public ComboBox<String> cbSaveWage;
    @FXML public CustomTextField tfYear;
    @FXML public CustomTextField tfColDays;
    @FXML public DatePicker dpDStartWork;
    @FXML public DatePicker dpDFinishWork;
    @FXML public DatePicker dpDStart;
    @FXML public DatePicker dpDFinish;
    @FXML public DatePicker dpDStart1;
    @FXML public DatePicker dpDFinish1;
    @FXML public DatePicker dpDStart2;
    @FXML public DatePicker dpDFinish2;

    private CollectionListHoliday collectionListHoliday;
    private Holiday holiday;
    ObservableList<String> types =
            FXCollections.observableArrayList(
                    "Трудовой",
                    "Социальный",
                    "Декретный"
            );
    ObservableList<String> wage =
            FXCollections.observableArrayList(
                    "С сохранением",
                    "Без сохранения"
            );

    private int idStaff, howButtonPressed;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfYear);
        SetupClearButtonField.SetupClearButtonField(tfColDays);
        cbTypeHoliday.setItems(types);
        cbTypeHoliday.setPromptText("Тип отпуска");
        cbSaveWage.setItems(wage);
        cbSaveWage.setPromptText("Заработная плата");
        howButtonPressed = 1;
    }

    public void setHoliday(CollectionListHoliday collectionListHoliday,
                       Holiday holiday){
        logger.info("setHoliday - 1");

        dpDStartWork.setValue(null);
        dpDFinishWork.setValue(null);
        dpDStart.setValue(null);
        dpDFinish.setValue(null);
        dpDStart1.setValue(null);
        dpDFinish1.setValue(null);
        dpDStart2.setValue(null);
        dpDFinish2.setValue(null);

        this.collectionListHoliday = collectionListHoliday;
        this.holiday = holiday;
        cbTypeHoliday.setValue(holiday.getTypeHoliday());
        cbSaveWage.setValue(holiday.getSaveWage());
        tfYear.setText(holiday.getYear());
        tfColDays.setText(holiday.getColDays());

        if (holiday.getdStartWork() != null){
            dpDStartWork.setValue(LocalDate.parse(returnDateForViews(holiday.getdStartWork())));
        }
        if (holiday.getdFinishWork() != null){
            dpDFinishWork.setValue(LocalDate.parse(returnDateForViews(holiday.getdFinishWork())));
        }

        if (holiday.getdStart() != null){
            dpDStart.setValue(LocalDate.parse(returnDateForViews(holiday.getdStart())));
        }
        if (holiday.getdFinish() != null){
            dpDFinish.setValue(LocalDate.parse(returnDateForViews(holiday.getdFinish())));
        }

        if (holiday.getdStart1() != null){
            dpDStart1.setValue(LocalDate.parse(returnDateForViews(holiday.getdStart1())));
        }
        if (holiday.getdFinish1() != null){
            dpDFinish1.setValue(LocalDate.parse(returnDateForViews(holiday.getdFinish1())));
        }

        if (holiday.getdStart2() != null){
            dpDStart2.setValue(LocalDate.parse(holiday.getdStart2()));
        }
        if (holiday.getdFinish2() != null){
            dpDFinish2.setValue(LocalDate.parse(holiday.getdFinish2()));
        }
        howButtonPressed = 0;
    }

    public void setHoliday(CollectionListHoliday collectionListHoliday,
                       int idStaff){
        logger.info("setHoliday - 0");
        this.collectionListHoliday = collectionListHoliday;
        this.idStaff = idStaff;
        this.holiday = new Holiday();

        cbTypeHoliday.setValue(null);
        cbSaveWage.setValue(null);
        tfYear.clear();
        tfColDays.clear();

        dpDStartWork.setValue(null);
        dpDFinishWork.setValue(null);
        dpDStart.setValue(null);
        dpDFinish.setValue(null);
        dpDStart1.setValue(null);
        dpDFinish1.setValue(null);
        dpDStart2.setValue(null);
        dpDFinish2.setValue(null);
        howButtonPressed = 1;
    }

    public void actionBtnPress(ActionEvent actionEvent) {
        logger.info("actionBtnPress");
        Object object = actionEvent.getSource();
        if (!(object instanceof Button)){
            return;
        }
        Button button = (Button) object;
        switch (button.getId()) {
            case "btnSave":
                System.out.println(cbTypeHoliday.getValue());
                logger.info("btnSave");
                if (howButtonPressed == 0){
                    logger.info("btnSave - 0");
                    getDataFromForm();
                    collectionListHoliday.updateHoliday(holiday);

                    return;
                }
                if (howButtonPressed == 1){
                    logger.info("btnSave - 1");
                    //записываем новые данные
                    getDataFromForm();
                    collectionListHoliday.insertNewHoliday(holiday, idStaff);
                    howButtonPressed = 0;
                    return;
                }

                break;
            case "btnClose":
                logger.info("btnClose");
                Node source = (Node) actionEvent.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                break;
        }
    }

    private void getDataFromForm(){
        logger.info("getDataFromForm");
        holiday.setTypeHoliday(cbTypeHoliday.getValue());
        holiday.setSaveWage(cbSaveWage.getValue());

        holiday.setYear(tfYear.getText());
        holiday.setColDays(tfColDays.getText());

        holiday.setdStartWork(returnDate(dpDStartWork.getValue()));
        holiday.setdFinishWork(returnDate(dpDFinishWork.getValue()));

        holiday.setdStart(returnDate(dpDStart.getValue()));
        holiday.setdFinish(returnDate(dpDFinish.getValue()));

        holiday.setdStart1(returnDate(dpDStart1.getValue()));
        holiday.setdFinish1(returnDate(dpDFinish1.getValue()));

        if (dpDStart2.getValue() == null){
            holiday.setdStart2(null);
        }else {
            holiday.setdStart2(dpDStart2.getValue().toString());
        }

        if (dpDFinish2.getValue() == null){
            holiday.setdFinish2(null);
        }else {
            holiday.setdFinish2(dpDFinish2.getValue().toString());
        }


    }

    private String returnDate(LocalDate date){
        logger.info("returnDate");
        String[] dates;
        if (date != null){
            dates = date.toString().split("-");
            System.out.println("Date = " + dates[2] + "." + dates[1] + "." + dates[0]);
            return dates[2] + "." + dates[1] + "." + dates[0];
        }
        return  null;
    }

    private String returnDateForViews(String date){
        logger.info("returnDateForViews");
        String[] dates;
        if (date != null){
            dates = date.split("\\.");
            return dates[2] + "-" + dates[1] + "-" + dates[0];
        }
        return  null;
    }
}
