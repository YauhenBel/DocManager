package Other;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetupClearButtonField {

    private static Logger logger = LogManager.getLogger();

    public static void SetupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public static void SetupClearButtonField(CustomPasswordField customPasswordField) {
        try
        {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customPasswordField, customPasswordField.rightProperty());

        }
        catch(NoSuchMethodException |IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }



    public static void closeConnections(PreparedStatement preparedStatement, Connection connection){
        logger.info("closeConnections");
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
    public static void closeConnections(PreparedStatement preparedStatement){

        logger.info("closeConnections");

        if (preparedStatement != null) try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
    public static void closeConnections(Connection connection){
        logger.info("closeConnections");
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
            }
        }
    }





}
