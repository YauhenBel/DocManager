package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Child;
import objects.Parthneriship;
import objects.RZO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListParthnership implements ListParthnership{

    private ObservableList<Parthneriship> parthnershipList = FXCollections.observableArrayList();
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
                    "SELECT * FROM parthnership WHERE idStaff = ?");
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
    public void insertNewParthnership(Parthneriship parthneriship, int idStaff) {
        logger.info("insertNewParthnership");
        PreparedStatement insertRZO = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertRZO = connection.prepareStatement(
                    "INSERT INTO parthnership (idParth, idStaff, dStart, dFinish, "
                            + "type, position) "
                            + "VALUES(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertRZO.setNull(1, NULL);
            insertRZO.setInt(2, idStaff);

            if (parthneriship.getdStart() == null){
                insertRZO.setString(3, null);
            }else {
                String[] dateS = parthneriship.getdStart().split("\\.");
                insertRZO.setString(3, dateS[2] + "-" + dateS[1] + "-" + dateS[0]);
            }
            if (parthneriship.getdFinish() == null){
                insertRZO.setString(4, null);
            }else {
                String[] dateS1 = parthneriship.getdFinish().split("\\.");
                insertRZO.setString(4, dateS1[2] + "-" + dateS1[1] + "-" + dateS1[0]);
            }

            insertRZO.setInt(5, Integer.parseInt(parthneriship.getType()));
            insertRZO.setString(6, parthneriship.getPosition());
            insertRZO.executeUpdate();
            resultSet = insertRZO.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                System.out.println("lastId = " + resultSet.getString(1));
            }
            parthneriship.setIdParth(lastId);
            parthnershipList.add(parthneriship);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertRZO, connection);
        }
    }

    @Override
    public void updateParthnership(Parthneriship parthneriship) {
        logger.info("updateParthnership");
        PreparedStatement updatetRZO = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updatetRZO = connection.prepareStatement("UPDATE parthnership "+
                    "SET " +
                    "dStart = ?, " +
                    "dFinish = ?, " +
                    "type = ?, " +
                    "position = ?" +
                    "WHERE idParth = ?");

            if (parthneriship.getdStart() == null){
                updatetRZO.setString(1, null);
            }else {
                String[] dateS = parthneriship.getdStart().split("\\.");
                updatetRZO.setString(1, dateS[2] + "-" + dateS[1] + "-" + dateS[0]);
            }
            if (parthneriship.getdFinish() == null){
                updatetRZO.setString(2, null);
            }else {
                String[] dateS1 = parthneriship.getdFinish().split("\\.");
                updatetRZO.setString(2, dateS1[2] + "-" + dateS1[1] + "-" + dateS1[0]);
            }
            updatetRZO.setInt(3, Integer.parseInt(parthneriship.getType()));
            updatetRZO.setString(4, parthneriship.getPosition());
            updatetRZO.setString(5, parthneriship.getIdParth());

            updatetRZO.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updatetRZO, connection);
        }
    }

    @Override
    public void delParthnership(Parthneriship parthneriship) {
        logger.info("delParthnership");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM parthnership WHERE idParth = ?");
            preparedStatement.setString(1, parthneriship.getIdParth());
            preparedStatement.executeUpdate();
            connection.commit();
            parthnershipList.remove(parthneriship);
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
        String[] dateS, dateS1;
        String date = null, date1 = null, type1="";
        while (rs.next()){
            for (int i = 0; i < 100; i++) System.out.print("-");
            try {
                if (rs.getString("dStart") != null){
                    dateS = rs.getString("dStart").split("-");
                    date = dateS[2] + "." + dateS[1] + "." + dateS[0];
                }
                if (rs.getString("dFinish") != null){
                    dateS1 = rs.getString("dFinish").split("-");
                    date1 = dateS1[2] + "." + dateS1[1] + "." + dateS1[0];
                }


                if (rs.getString("type").equals("0")) type1 = "Внутреннее";
                if (rs.getString("type").equals("1")) type1 = "Внешнее";
                if (rs.getString("type").equals("2")) type1 = "Не отмечено";


                String str = "\nidParth = " + rs.getString("idParth")
                        +"\ndStart = " + date
                        +"\ndFinish = " + date1
                        +"\ntype = " + rs.getString("type")
                        +"\ntype1 = " + type1
                        +"\nposition = " + rs.getString("position");

                logger.info("\ninfo parthnership:" + str);

                parthnershipList.add(new Parthneriship(
                        rs.getString("idParth"),
                        date,
                        date1,
                        rs.getString("type"),
                        type1,
                        rs.getString("position")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
    }

    @Override
    public ObservableList<Parthneriship> getParthnerishipList() {
        logger.info("getParthnerishipList");
        return parthnershipList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        parthnershipList.clear();

    }
}
