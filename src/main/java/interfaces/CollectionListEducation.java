package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Education;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListEducation implements ListEducation{

    private ObservableList<Education> educationList = FXCollections.observableArrayList();
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
                    "SELECT * FROM education WHERE idStaff = ?");
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
    public void insertNewEd(Education education, int idStaff) {
        logger.info("insertNewEd");
        logger.info("idStaff = " + idStaff);
        PreparedStatement insertEducation = null;
        ResultSet resultSet = null;
        String date = "";
        if (education.getmDateFinish() == null){
            date = null;
        }else {
            String[] dateS = education.getmDateFinish().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertEducation = connection.prepareStatement(
                    "INSERT INTO education (idEd, idStaff, nameEducation, dateFinish, specialty, " +
                            "qualification, levelEducation) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertEducation.setNull(1, NULL);
            insertEducation.setInt(2, idStaff);
            insertEducation.setString(3, education.getmNameEd());
            insertEducation.setString(4, date);
            insertEducation.setString(5, education.getmSpecialty());
            insertEducation.setString(6, education.getmQualification());
            insertEducation.setString(7, education.getLevelEducation());
            insertEducation.executeUpdate();
            resultSet = insertEducation.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                System.out.println("lastId = " + resultSet.getString(1));
            }
            education.setmIdEd(lastId);
            educationList.add(education);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertEducation, connection);
        }

    }

    @Override
    public void updateEd(Education education) {
        logger.info("updateEd");
        PreparedStatement updatetEducation = null;
        String date = "";
        if (education.getmDateFinish() == null){
            date = null;
        }else {
            String[] dateS = education.getmDateFinish().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updatetEducation = connection.prepareStatement("UPDATE education "+
                    "SET " +
                    "nameEducation = ?, " +
                    "dateFinish = ?, " +
                    "specialty = ?, " +
                    "qualification = ?, " +
                    "levelEducation = ? " +
                    "WHERE idEd = ?");

            updatetEducation.setString(1, education.getmNameEd());
            updatetEducation.setString(2, date);
            updatetEducation.setString(3, education.getmSpecialty());
            updatetEducation.setString(4, education.getmQualification());
            updatetEducation.setString(5, education.getLevelEducation());
            updatetEducation.setString(6, education.getmIdEd());

            updatetEducation.executeUpdate();

            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updatetEducation, connection);
        }

    }

    @Override
    public void delEd(Education education) {
        logger.info("delEd");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM education WHERE idEd = ?");
            preparedStatement.setString(1, education.getmIdEd());
            preparedStatement.executeUpdate();
            connection.commit();
            educationList.remove(education);
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
        String date = "";
        while (rs.next()){

            try {
                if (rs.getString("dateFinish") == null){
                    date = null;
                }else {
                    dateS = rs.getString("dateFinish").split("-");
                    date = dateS[2] + "." + dateS[1] + "." + dateS[0];
                }
                String str = "\nidEd = " + rs.getString("idEd")
                        + "\nnameEducation = " + rs.getString("nameEducation")
                        +"\ndateFinish = " + date
                        +"\nspecialty = " + rs.getString("specialty")
                        +"\nqualification = " + rs.getString("qualification")
                        +"\nlevelEducation = " + rs.getString("levelEducation");
                logger.info("\ninfo education:" + str);
                educationList.add(new Education(
                        rs.getString("idEd"),
                        rs.getString("nameEducation"),
                        date,
                        rs.getString("specialty"),
                        rs.getString("qualification"),
                        rs.getString("levelEducation")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }


        }

    }

    @Override
    public ObservableList<Education> getEducationList() {
        logger.info("getEducationList");
        return educationList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        educationList.clear();
    }
}
