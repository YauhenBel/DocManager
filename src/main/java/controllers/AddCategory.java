package controllers;

import interfaces.CollectionListCategories;
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
import objects.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCategory implements Initializable {
    @FXML public ComboBox<String> cbTypeCategory;
    @FXML public ComboBox<String> cbLevelCategory;
    @FXML public DatePicker dpDateCategory;
    @FXML public Button btnSave;

    private CollectionListCategories collectionListCategories;
    private Category category;

    private static Logger logger = LogManager.getLogger();

    private int idStaff, howButtonPressed;
    ObservableList<String> typesCategory =
            FXCollections.observableArrayList(
                    "Педагога социального",
                    "Библиотекаря",
                    "Воспитателя",
                    "Концертмейстер",
                    "Мастера",
                    "Методиста",
                    "Музыкального руководителя",
                    "Педагога-организатора",
                    "Педагога дополнительного образования",
                    "Педагога-психолога",
                    "Преподавателя",
                    "Психолога",
                    "Руководителя физического воспитания",
                    "Учителя-дефектолога",
                    "Учительская"

            );
    ObservableList<String> levelCategories =
            FXCollections.observableArrayList(
                    "Без категории",
                    "Вторая",
                    "Высшая",
                    "Первая",
                    "Учитель методист"
            );
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        cbTypeCategory.setItems(typesCategory);
        cbLevelCategory.setItems(levelCategories);
    }

    public void setCategory(CollectionListCategories collectionListCategories,
                           Category category, int rootLvl){
        logger.info("setCategory - 1");

        if (rootLvl == 2) btnSave.setDisable(true);
        else btnSave.setDisable(false);


        dpDateCategory.setValue(null);
        this.collectionListCategories = collectionListCategories;
        this.category = category;

        cbTypeCategory.setValue(category.getTypeCategory());
        cbLevelCategory.setValue(category.getLevelCategory());

        if (category.getDateCategory() != null){
            dpDateCategory.setValue(LocalDate.parse(returnDateForViews(category.getDateCategory())));
        }

        howButtonPressed = 0;
    }

    public void setCategory(CollectionListCategories collectionListCategories,
                           int idStaff){
        logger.info("setCategory - 0");
        this.collectionListCategories = collectionListCategories;
        this.idStaff = idStaff;
        this.category = new Category();

        cbTypeCategory.setValue(null);
        cbLevelCategory.setValue(null);
        dpDateCategory.setValue(null);
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
                logger.info("btnSave");
                if (howButtonPressed == 0){
                    logger.info("btnSave - 0");
                    getDataFromForm();
                    collectionListCategories.updateCategory(category);

                    return;
                }
                if (howButtonPressed == 1){
                    //записываем новые данные
                    logger.info("btnSave - 1");
                    getDataFromForm();
                    collectionListCategories.insertNewCategory(category, idStaff);
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
        category.setTypeCategory(cbTypeCategory.getValue());
        category.setLevelCategory(cbLevelCategory.getValue());
        category.setDateCategory(returnDate(dpDateCategory.getValue()));
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
