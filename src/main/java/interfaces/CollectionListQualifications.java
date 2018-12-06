package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Qualification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListQualifications implements ListQualification{

    private ObservableList<Qualification> qualificationList = FXCollections.observableArrayList();
    private Connection connection;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void fillData(Integer id) {
        logger.info("fillData");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                            "SELECT * FROM training WHERE idStaff = ?");
            preparedStatement.setInt(1, id);
            processAnswer(preparedStatement.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    @Override
    public void insertNewQualification(Qualification qualification, int idStaff) {
        logger.info("insertNewQualification");
        PreparedStatement insertQualification = null;
        ResultSet resultSet = null;
        String date = null;
        if (qualification.getmDate() != null){
            String[] dateS = qualification.getmDate().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }


        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertQualification = connection.prepareStatement(
                    "INSERT INTO training (idTrai, idStaff, what, date, hours, " +
                            "theme) " +
                            "VALUES(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertQualification.setNull(1, NULL);
            insertQualification.setInt(2, idStaff);
            insertQualification.setString(3, qualification.getmWhat());
            insertQualification.setString(4, date);
            insertQualification.setInt(5, Integer.parseInt(qualification.getmHours()));
            insertQualification.setString(6, qualification.getmTheme());
            insertQualification.executeUpdate();
            resultSet = insertQualification.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                logger.info("lastId = " + resultSet.getString(1));
            }
            qualification.setmIdTrai(lastId);
            qualificationList.add(qualification);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertQualification, connection);
        }
    }

    @Override
    public void updateQualification(Qualification qualification) {
        logger.info("updateQualification");
        PreparedStatement updateQualification = null;
        String date = null;
        if (qualification.getmDate() != null){
            String[] dateS = qualification.getmDate().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateQualification = connection.prepareStatement("UPDATE training "+
                    "SET " +
                    "what = ?, " +
                    "date = ?, " +
                    "hours = ?, " +
                    "theme = ? " +
                    "WHERE idTrai = ?");

            updateQualification.setString(1, qualification.getmWhat());
            updateQualification.setString(2, date);
            updateQualification.setInt(3, Integer.parseInt(qualification.getmHours()));
            updateQualification.setString(4, qualification.getmTheme());
            updateQualification.setString(5, qualification.getmIdTrai());

            updateQualification.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateQualification, connection);
        }
    }

    @Override
    public void delQualification(Qualification qualification) {
        logger.info("delQualification");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM training WHERE idTrai = ?");
            preparedStatement.setString(1, qualification.getmIdTrai());
            preparedStatement.executeUpdate();
            connection.commit();
            qualificationList.remove(qualification);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    @Override
    public void processAnswer(ResultSet rs) throws SQLException {
        logger.info("processAnswer");
        String[] dateS;
        String date;

        while (rs.next()){
            date = null;
            try {
                if (rs.getString("date") != null){
                    dateS = rs.getString("date").split("-");
                    date = dateS[2] + "." + dateS[1] + "." + dateS[0];
                }
                for (int i = 0; i < 100; i++) System.out.print("-");
                String str = "\nidTrai = " + rs.getString("idTrai")
                        + "\nwhat = " + rs.getString("what")
                        +"\ndate = " + date
                        +"\nhours = " + rs.getString("hours")
                        +"\ntheme = " + rs.getString("theme");
                logger.info("\ninfo qualification:" + str);
                qualificationList.add(new Qualification(
                        rs.getString("idTrai"),
                        rs.getString("what"),
                        date,
                        rs.getString("hours"),
                        rs.getString("theme")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }

    }

    @Override
    public ObservableList<Qualification> getQualificationList() {
        logger.info("getQualificationList");
        return qualificationList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        qualificationList.clear();

    }
}
