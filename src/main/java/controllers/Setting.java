package controllers;

import Other.SetupClearButtonField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Setting implements Initializable {

    public CustomTextField tfAddressOfServer;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        SetupClearButtonField.SetupClearButtonField(tfAddressOfServer);
        tfAddressOfServer.clear();
        getAddress();


    }

    private void getAddress(){
        logger.info("getAddress");
        String path = "/files/address.txt";
        logger.info("Path = " + path);
        String address = "";
        ClassLoader loader = Setting.class.getClassLoader();
        File file = new File("address.txt");
        //"src\\main\\resources\\files\\address.txt"
        try(FileReader reader = new FileReader(file))
        {
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
                address+=(char)c;

            }
            tfAddressOfServer.setText(address);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        logger.info("getAddress = " + address);
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

                try(FileWriter writer = new FileWriter("address.txt", false))
                {
                    // запись всей строки
                    String text = tfAddressOfServer.getText();
                    writer.write(text);

                    writer.flush();
                }
                catch(IOException ex){
                    logger.error(ex.getMessage());
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


}
