package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import objects.Medical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectMedical implements ObjMedical {
    private Connection connection;
    private Medical medical;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void fillData(Integer id) {
        logger.info("fillData");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM medical WHERE idStaff = ?");
            preparedStatement.setInt(1, id);
            processAnswer(preparedStatement.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    @Override
    public void insertNewMedical(Medical medical, int idStaff) {
        logger.info("insertNewMedical");
        PreparedStatement insertMedical = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertMedical = connection.prepareStatement(
                    "INSERT INTO medical (idStaff, fluoro, ads) "
                            + "VALUES(?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertMedical.setInt(1, idStaff);
            insertMedical.setString(2, medical.getFluoro());
            insertMedical.setString(3, medical.getAds());
            insertMedical.executeUpdate();
            this.medical = medical;
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertMedical, connection);
        }
    }

    @Override
    public void updateMedical(Medical medical, int idStaff) {
        logger.info("updateMedical");
        PreparedStatement updatetMedical = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updatetMedical = connection.prepareStatement("UPDATE medical "+
                    "SET " +
                    "fluoro = ?, " +
                    "ads = ? " +
                    "WHERE idStaff = ?");

            updatetMedical.setString(1, medical.getFluoro());
            updatetMedical.setString(2, medical.getAds());
            updatetMedical.setInt(3, idStaff);

            updatetMedical.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updatetMedical, connection);
        }
    }

    @Override
    public void processAnswer(ResultSet rs) throws SQLException {
        logger.debug("processAnswer");
        while (rs.next()){

            String str = null;
            try {
                str = "\nfluoro = " + rs.getString("fluoro")
                        +"\nads = " + rs.getString("ads");
                logger.info("\ninfo medical:" + str);
                this.medical = new Medical(
                        rs.getString("fluoro"),
                        rs.getString("ads")
                );
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
    }

    private String returnDateForDatabase(String date){
        logger.info("returnDateForDatabase");
        String[] dates;
        if (date != null){
            dates = date.split("\\.");
            return dates[2] + "-" + dates[1] + "-" + dates[0];
        }
        return  null;
    }

    @Override
    public Medical getObjectMedical() {
        logger.info("getObjectMedical");
        return medical;
    }
}
