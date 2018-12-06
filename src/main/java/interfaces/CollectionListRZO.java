package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Child;
import objects.RZO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListRZO implements ListRZO {

    private ObservableList<RZO> rzoList = FXCollections.observableArrayList();
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
                    "SELECT * FROM tech_staff WHERE idStaff = ?");
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
    public void insertNewRZO(RZO rzo, int idStaff) {
        logger.info("insertNewRZO");
        PreparedStatement insertRZO = null;
        ResultSet resultSet = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertRZO = connection.prepareStatement(
                    "INSERT INTO tech_staff (idTech, idStaff, dateStart, dateFinish, "
                            + "percent, proff, dateOrder, numOrder) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertRZO.setNull(1, NULL);
            insertRZO.setInt(2, idStaff);
            insertRZO.setString(3, returnDateForDatabase(rzo.getDateStart()));
            insertRZO.setString(4, returnDateForDatabase(rzo.getDateFinish()));
            insertRZO.setInt(5, Integer.parseInt(rzo.getPercent()));
            insertRZO.setString(6, rzo.getProff());
            insertRZO.setString(7, returnDateForDatabase(rzo.getDateOrder()));
            insertRZO.setString(8, rzo.getNumOrder());
            insertRZO.executeUpdate();
            resultSet = insertRZO.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                logger.info("lastId = " + resultSet.getString(1));
            }
            rzo.setIdTech(lastId);
            rzoList.add(rzo);
            connection.commit();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertRZO, connection);
        }
    }

    @Override
    public void updateRZO(RZO rzo) {
        logger.info("updateRZO");
        PreparedStatement updatetRZO = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updatetRZO = connection.prepareStatement("UPDATE tech_staff "+
                    "SET " +
                    "dateStart = ?, " +
                    "dateFinish = ?, " +
                    "percent = ?, " +
                    "proff = ?," +
                    "dateOrder = ?," +
                    "numOrder = ? " +
                    "WHERE idTech = ?");

            updatetRZO.setString(1, returnDateForDatabase(rzo.getDateStart()));
            updatetRZO.setString(2, returnDateForDatabase(rzo.getDateFinish()));
            updatetRZO.setInt(3, Integer.parseInt(rzo.getPercent()));
            updatetRZO.setString(4, rzo.getProff());
            updatetRZO.setString(5, returnDateForDatabase(rzo.getDateOrder()));
            updatetRZO.setString(6, rzo.getNumOrder());
            updatetRZO.setString(7, rzo.getIdTech());

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
    public void delRZO(RZO rzo) {
        logger.info("delRZO");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM tech_staff WHERE idTech = ?");
            preparedStatement.setString(1, rzo.getIdTech());
            preparedStatement.executeUpdate();
            connection.commit();
            rzoList.remove(rzo);
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
        while (rs.next()){
            for (int i = 0; i < 100; i++) System.out.print("-");

            String str = null;
            try {
                str = "\nidTech = " + rs.getString("idTech")
                        +"\ndateStart = " + returnDate(rs.getString("dateStart"))
                        +"\ndateFinish = " + returnDate(rs.getString("dateFinish"))
                        +"\npercent = " + rs.getString("percent")
                        +"\nproff = " + rs.getString("proff")
                        +"\ndateOrder = " + returnDate(rs.getString("dateFinish"))
                        +"\nnumOrder = " + rs.getString("numOrder");
                logger.info("\ninfo RZO:" + str);
                rzoList.add(new RZO(
                        rs.getString("idTech"),
                        returnDate(rs.getString("dateStart")),
                        returnDate(rs.getString("dateFinish")),
                        rs.getString("percent"),
                        rs.getString("proff"),
                        returnDate(rs.getString("dateFinish")),
                        rs.getString("numOrder")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
    }

    private String returnDate(String date){
        logger.info("returnDate");
        String[] dates;
        if (date != null){
            dates = date.split("-");
            return dates[2] + "." + dates[1] + "." + dates[0];
        }
        return  null;
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
    public ObservableList<RZO> getRZOList() {
        logger.info("getRZOList");
        return rzoList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        rzoList.clear();

    }
}
