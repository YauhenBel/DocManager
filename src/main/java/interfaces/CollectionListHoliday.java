package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Holiday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListHoliday implements ListHoliday {

    private ObservableList<Holiday> holidaysList = FXCollections.observableArrayList();
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
                    "SELECT * FROM holidays WHERE idStaff = ?");
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
    public void insertNewHoliday(Holiday holiday, int idStaff) {
        logger.info("insertNewHoliday");
        PreparedStatement insertHoliday = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertHoliday = connection.prepareStatement(
                    "INSERT INTO holidays (idHoliday, idStaff, year, colDays, dStartWork, "
                            + "dFinishWork, dStart, dFinish, dStart1, dFinish1," +
                            "dStart2, dFinish2, typeHoliday, saveWage)"
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertHoliday.setNull(1, NULL);
            insertHoliday.setInt(2, idStaff);
            insertHoliday.setString(3, holiday.getYear());
            insertHoliday.setString(4, holiday.getColDays());
            insertHoliday.setString(5, returnDateForDatabase(holiday.getdStartWork()));
            insertHoliday.setString(6, returnDateForDatabase(holiday.getdFinishWork()));
            insertHoliday.setString(7, returnDateForDatabase(holiday.getdStart()));
            insertHoliday.setString(8, returnDateForDatabase(holiday.getdFinish()));
            insertHoliday.setString(9, returnDateForDatabase(holiday.getdStart1()));
            insertHoliday.setString(10, returnDateForDatabase(holiday.getdFinish1()));
            insertHoliday.setString(11, holiday.getdStart2());
            insertHoliday.setString(12, holiday.getdFinish2());
            insertHoliday.setString(13, holiday.getTypeHoliday());
            insertHoliday.setString(14, holiday.getSaveWage());

            insertHoliday.executeUpdate();
            resultSet = insertHoliday.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                System.out.println("lastId = " + resultSet.getString(1));
            }
            holiday.setIdHoliday(lastId);
            holidaysList.add(holiday);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertHoliday, connection);
        }
    }

    @Override
    public void updateHoliday(Holiday holiday) {
        logger.info("updateHoliday");
        PreparedStatement updateHoliday = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateHoliday = connection.prepareStatement("UPDATE holidays "+
                    "SET " +
                    "year = ?, " +
                    "colDays = ?, " +
                    "dStartWork = ?, " +
                    "dFinishWork = ?," +
                    "dStart = ?," +
                    "dFinish = ?, " +
                    "dStart1 = ?, " +
                    "dFinish1 = ?, " +
                    "dStart2 = ?," +
                    "dFinish2 = ?," +
                    "typeHoliday = ?, " +
                    "saveWage = ? " +
                    "WHERE idHoliday = ?");

            updateHoliday.setString(1,holiday.getYear() );
            updateHoliday.setString(2, holiday.getColDays());
            updateHoliday.setString(3, returnDateForDatabase(holiday.getdStartWork()));
            updateHoliday.setString(4, returnDateForDatabase(holiday.getdFinishWork()));
            updateHoliday.setString(5, returnDateForDatabase(holiday.getdStart()));
            updateHoliday.setString(6, returnDateForDatabase(holiday.getdFinish()));
            updateHoliday.setString(7, returnDateForDatabase(holiday.getdStart1()));
            updateHoliday.setString(8, returnDateForDatabase(holiday.getdFinish1()));
            updateHoliday.setString(9, holiday.getdStart2());
            updateHoliday.setString(10, holiday.getdFinish2());
            updateHoliday.setString(11, holiday.getTypeHoliday());
            updateHoliday.setString(12, holiday.getSaveWage());
            updateHoliday.setString(13, holiday.getIdHoliday());

            updateHoliday.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateHoliday, connection);
        }
    }

    @Override
    public void delHoliday(Holiday holiday) {
        logger.info("delHoliday");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM holidays WHERE idHoliday = ?");
            preparedStatement.setString(1, holiday.getIdHoliday());
            preparedStatement.executeUpdate();
            connection.commit();
            holidaysList.remove(holiday);
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
            String str = null;
            try {
                str = "\nidHoliday = " + rs.getString("idHoliday")
                        + "\nyear = " + rs.getString("year")
                        +"\ncolDays = " + rs.getString("colDays")
                        +"\ndStartWork = " + returnDate(rs.getString("dStartWork"))
                        +"\ndFinishWork = " + returnDate(rs.getString("dFinishWork"))
                        +"\ndStart = " + returnDate(rs.getString("dStart"))
                        +"\ndFinish = " + returnDate(rs.getString("dFinish"))
                        +"\ndStart1 = " + returnDate(rs.getString("dStart1"))
                        +"\ndFinish1 = " + returnDate(rs.getString("dFinish1"))
                        +"\ndStart2 = " + rs.getString("dStart2")
                        +"\ndFinish2 = " + rs.getString("dFinish2")
                        +"\ntypeHoliday = " + rs.getString("typeHoliday")
                        +"\nsaveWage = " + rs.getString("saveWage");

                logger.info("\ninfo holiday:" + str);

                holidaysList.add(new Holiday(
                        rs.getString("idHoliday"),
                        rs.getString("year"),
                        rs.getString("colDays"),
                        returnDate(rs.getString("dStartWork")),
                        returnDate(rs.getString("dFinishWork")),
                        returnDate(rs.getString("dStart")),
                        returnDate(rs.getString("dFinish")),
                        returnDate(rs.getString("dStart1")),
                        returnDate(rs.getString("dFinish1")),
                        rs.getString("dStart2"),
                        rs.getString("dFinish2"),
                        rs.getString("typeHoliday"),
                        rs.getString("saveWage")
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
    public ObservableList<Holiday> getHolidaysList() {
        logger.info("getHolidaysList");
        return holidaysList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        holidaysList.clear();

    }
}
