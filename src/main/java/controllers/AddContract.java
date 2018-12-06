package controllers;

import interfaces.CollectionListContract;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.Contract;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddContract implements Initializable {


    @FXML private ComboBox<String> cbTypeContract;
    @FXML private DatePicker dpStartContract;
    @FXML private DatePicker dpFinishContract;
    @FXML private DatePicker dpDateDischarge;

    private CollectionListContract collectionListContract;

    private int howButtonPressed = 3;
    private int idStaff;

    private Contract contract;

    private Label lengthWork, generalLengthWork;

    ObservableList<String> typeContract =
            FXCollections.observableArrayList(
                    "Контракт",
                    "Договор подряда",
                    "Трудовой договор"
            );

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        cbTypeContract.setItems(typeContract);

    }

    public void setContract(CollectionListContract collectionListContract,
                            Contract contract, Label lengthWork, Label generalLengthWork){
        logger.info("setContract  - 1");

        this.lengthWork = lengthWork;
        this.generalLengthWork = generalLengthWork;

        this.collectionListContract = collectionListContract;
        this.contract = contract;

        dpStartContract.setValue(null);
        dpFinishContract.setValue(null);
        dpDateDischarge.setValue(null);



        cbTypeContract.setValue(contract.getType());

        if (contract.getdStart() != null){
            dpStartContract.setValue(LocalDate.parse(returnDateForViews(contract.getdStart())));
        }
        if (contract.getdFinish() != null){
            dpFinishContract.setValue(LocalDate.parse(returnDateForViews(contract.getdFinish())));
        }




        if (contract.getDischarge() == null){
            dpDateDischarge.setValue(null);
        }else {
            dpDateDischarge.setValue(LocalDate.parse(contract.getDischarge()));
        }

        howButtonPressed = 0;
    }


    public void setContract(CollectionListContract collectionListContract,
                             int idStaff, Label lengthWork, Label generalLengthWork){
        logger.info("setContract  - 0");

        this.lengthWork = lengthWork;
        this.generalLengthWork = generalLengthWork;

        this.collectionListContract = collectionListContract;
        this.idStaff = idStaff;
        this.contract = new Contract();
        cbTypeContract.setValue(null);
        dpStartContract.setValue(null);
        dpFinishContract.setValue(null);
        dpDateDischarge.setValue(null);
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
                    logger.info("updateEd");
                    getDataFromForm();
                    //обновляем данные
                    collectionListContract.updateContract(contract);
                    collectionListContract.lengthWork(lengthWork, generalLengthWork);

                    return;
                }
                if (howButtonPressed == 1){
                    //записываем новые данные
                        logger.info("insertNewEd");
                        getDataFromForm();
                        howButtonPressed = collectionListContract.insertNewContract(contract, idStaff);
                        collectionListContract.lengthWork(lengthWork, generalLengthWork);
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
        contract.setType(cbTypeContract.getValue());
        contract.setdStart(returnDate(dpStartContract.getValue()));
        contract.setdFinish(returnDate(dpFinishContract.getValue()));
        if (dpDateDischarge.getValue() != null) {
            contract.setDischarge(dpDateDischarge.getValue().toString());
        }else {
            contract.setDischarge(null);
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
