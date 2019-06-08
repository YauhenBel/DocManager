package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import objects.Contract;
import objects.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static java.sql.Types.NULL;

public class CollectionListContract implements ListContract {
    private Connection connection;
    private Contract contract;
    private ObservableList<Contract> contractsList = FXCollections.observableArrayList();
    int year = 0, month = 0, day = 0;
    int idStaff = 0;
    int outsideLengthDays = 0;
    long days = 0;
    String[] yearmonthday;

    private static Logger logger = LogManager.getLogger();

    @Override
    public void fillData(Integer id) {
        logger.info("fillData");
        idStaff = id;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM contracts WHERE idStaff = ?");
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
    public int insertNewContract(Contract contract, int idStaff) {
        logger.info("insertNewContract");
        PreparedStatement insertContract = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertContract = connection.prepareStatement(
                    "INSERT INTO contracts (idContract, idStaff, type, dStart, dFinish, discharge) "
                            + "VALUES(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertContract.setNull(1, NULL);
            insertContract.setInt(2, idStaff);
            insertContract.setString(3, contract.getType());
            insertContract.setString(4, returnDateForDatabase(contract.getdStart()));
            insertContract.setString(5, returnDateForDatabase(contract.getdFinish()));
            insertContract.setString(6, returnDateForDatabase(contract.getDischarge()));
            insertContract.executeUpdate();

            this.contract = contract;
            connection.commit();
            contract.setIdStaff(idStaff);
            contractsList.add(contract);

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
            return 1;
        } finally {
            SetupClearButtonField.closeConnections(insertContract, connection);
        }

        return 0;
    }

    @Override
    public void updateContract(Contract contract) {
        logger.info("updateContract");
        PreparedStatement updateContract = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateContract = connection.prepareStatement("UPDATE contracts "+
                    "SET " +
                    "type = ?, " +
                    "dStart = ?, " +
                    "dFinish = ?, " +
                    "discharge = ? " +
                    "WHERE idContract = ?");

            updateContract.setString(1, contract.getType());
            updateContract.setString(2, returnDateForDatabase(contract.getdStart()));
            updateContract.setString(3, returnDateForDatabase(contract.getdFinish()));
            updateContract.setString(4, contract.getDischarge());
            updateContract.setInt(5, contract.getIdContract());

            updateContract.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateContract, connection);
        }

    }

    @Override
    public void delContract(Contract contract) {
        logger.info("delContract");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM contracts WHERE idContract = ?");
            preparedStatement.setInt(1, contract.getIdContract());
            preparedStatement.executeUpdate();
            connection.commit();
            contractsList.remove(contract);
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
                str = "\nidContract = " + rs.getInt("idContract")
                        + "\nidStaff = " + rs.getInt("idStaff")
                        + "\ntype = " + rs.getString("type")
                        + "\ndStart = " + returnDate(rs.getString("dStart"))
                        + "\ndFinish = " + returnDate(rs.getString("dFinish"))
                        + "\ndischarge = " + rs.getString("discharge");
                logger.info("\ninfo contract:" + str);
                contractsList.add(new Contract(
                        rs.getInt("idContract"),
                        rs.getInt("idStaff"),
                        rs.getString("type"),
                        returnDate(rs.getString("dStart")),
                        returnDate(rs.getString("dFinish")),
                        rs.getString("discharge")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
    }

    @Override
    public ObservableList<Contract> getListContract() {
        logger.info("getListContract");
        return contractsList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        contractsList.clear();}

    public void lengthWork(Label lengthWork, Label generalLengthWork){
        logger.info("lengthWork");
        getLengthWork(idStaff);
        getFromDBOutsideLengthWork(idStaff);
        updateLengthWork(idStaff);
        lengthWork.setText(calculateLengthWork((int) days));
        generalLengthWork.setText(calculateLengthWork((int) days + outsideLengthDays));
        year = 0;
        month = 0;
        day = 0;
        days = 0;
        outsideLengthDays = 0;


    }

    public void lengthWork(ObservableList<Staff> staffList){
        logger.info("lengthWork1");
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM contracts");

            processAnswer(preparedStatement.executeQuery());
            connection.commit();


            for (Staff staff:staffList) {
                getFromDBOutsideLengthWork(staff.getmIdstaff());
                System.out.println("\nProccess data for user with id = " + staff.getmIdstaff());
                getLengthWork(staff.getmIdstaff());
                updateLengthWork(staff.getmIdstaff());


                year = 0;
                month = 0;
                day = 0;
                days = 0;
                outsideLengthDays = 0;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    private void updateLengthWork(int id){
        logger.info("updateLengthWork");
        PreparedStatement updateLengthWork = null;
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);

            updateLengthWork = connection.prepareStatement("UPDATE lengthwork "+
                    "SET " +
                    "years = ?, " +
                    "generalyears = ?  " +
                    "WHERE idStaff = ?");

                updateLengthWork.setInt(1, calculateLengthYear((int) days));
                updateLengthWork.setInt(2, calculateLengthYear((int) days + outsideLengthDays));
                updateLengthWork.setInt(3, id);
                updateLengthWork.executeUpdate();
                connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateLengthWork, connection);
        }
    }

    public ArrayList<String> getLengthContract(){
        logger.info("getLengthContract");
        logger.info("idStaff = " + idStaff);
        ArrayList<String> arrayList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `lenghtcontract` WHERE idstaff = ?");
            preparedStatement.setInt(1, idStaff);
            ResultSet rs = preparedStatement.executeQuery();
            connection.commit();
                while (rs.next()) {

                    logger.info("datecontractfrom = " + rs.getString("datecontractfrom"));
                    logger.info("datecontrctto = " + rs.getString("datecontrctto"));
                    arrayList.add(rs.getString("datecontractfrom"));
                    arrayList.add(rs.getString("datecontrctto"));
            }

            rs.close();


        } catch (SQLException e) {
            logger.info("catch");
            SetupClearButtonField.closeConnections(connection);
            e.printStackTrace();
        }
        finally {
            logger.info("finally");
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }

        return arrayList;
    }

    public void updateLengthContract(String dateFrom, String dateTo){

        PreparedStatement updateLengthContract = null;
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);

           updateLengthContract = connection.prepareStatement("SELECT * FROM lenghtcontract " +
                   "WHERE `idstaff` =" + idStaff);

           ResultSet resultSet = updateLengthContract.executeQuery();
           //connection.commit();
           updateLengthContract = null;
           if (resultSet.isFirst()){
               logger.info("resultSet != null");
               updateLengthContract = connection.prepareStatement(
                       "UPDATE lenghtcontract SET datecontractfrom =?, datecontrctto=?  WHERE idstaff=?");
               updateLengthContract.setString(1, dateFrom);
               updateLengthContract.setString(2, dateTo);
               updateLengthContract.setInt(3, idStaff);
               updateLengthContract.executeUpdate();
           }else  {
               logger.info("resultSet == null");
               updateLengthContract = connection.prepareStatement(
                       "INSERT INTO lenghtcontract (idstaff, datecontractfrom, datecontrctto) VALUES(?, ?, ?)");
               updateLengthContract.setInt(1, idStaff);
               updateLengthContract.setString(2, dateFrom);
               updateLengthContract.setString(3, dateTo);
               updateLengthContract.executeUpdate();

           }
           connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        }
        finally {
            SetupClearButtonField.closeConnections(updateLengthContract, connection);
        }
    }

    private void getFromDBOutsideLengthWork(int id){
        logger.info("getFromDBOutsideLengthWork - id:" + id);
        logger.info("getFromDBOutsideLengthWork");
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String outsideLength = "";
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "SELECT outsidelength FROM lengthwork WHERE idStaff = ?");
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            connection.commit();
            while (rs.next()){
                outsideLength = rs.getString("outsidelength");
            }
            System.out.println("outsidelength" + outsideLength);
            yearmonthday = outsideLength.split("-");
            outsideLengthDays = Integer.parseInt(yearmonthday[0])*12*30 + Integer.parseInt(yearmonthday[1])*30
                    + Integer.parseInt(yearmonthday[2]);
            System.out.println("Количество дней работы до текущей организации: " + outsideLength);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    private void getLengthWork(int id){
        logger.info("getLengthWork");

        for (Contract contract: contractsList) {
            logger.info("contract.getIdContract() = " + contract.getIdContract());
            logger.info("id = " + id);

            if (contract.getIdStaff() != id){
                logger.info("Идем дальше");
                continue;
            }
            if (contract.getdStart() == null)  continue;

            if(LocalDate.now().isBefore(LocalDate.parse(returnDateForDatabase(contract.getdStart())))){
                logger.info("Действие контракта еще не началось: " + contract.getdStart());
                continue;
            }

            if (contract.getdStart() != null && contract.getdFinish() != null && contract.getDischarge() == null){
                if (LocalDate.now().isAfter(LocalDate.parse(returnDateForDatabase(contract.getdFinish())))) {
                    logger.info("Контракт закончился");
                    days += proceccDate(LocalDate.parse(returnDateForDatabase(contract.getdStart())),
                            LocalDate.parse(returnDateForDatabase(contract.getdFinish())));


                }else {
                    logger.info("Контракт действует в настоящее время");
                    days += proceccDate(LocalDate.parse(returnDateForDatabase(contract.getdStart())),
                            LocalDate.now());
                }
            }

            if (contract.getdStart() != null && contract.getDischarge() != null){
                logger.info("Контракт прерван");
                days += proceccDate(LocalDate.parse(returnDateForDatabase(contract.getdStart())),
                        LocalDate.parse(contract.getDischarge()));
            }

            if (contract.getdStart() != null && contract.getdFinish() == null && contract.getDischarge() == null){
                logger.info("Бессрочный контракт действует в настоящее время");
                days += proceccDate(LocalDate.parse(returnDateForDatabase(contract.getdStart())),
                        LocalDate.now());
            }

        }


        /*year += (int) days / 30 / 12;
        month += (int) days / 30 % 12;
        day +=  (int) days % 30;

        lengthWork +="Годы -" + year + ", Месяцы - " + month + ", Дни - " + day;
        System.out.println("lengthWork" + lengthWork);*/
    }



    private String calculateLengthWork(int days){
        logger.info("calculateLengthWork");

        year += (int) days / 30 / 12;
        month += (int) days / 30 % 12;
        day +=  (int) days % 30;
        String length = "Годы -" + year + ", Месяцы - " + month + ", Дни - " + day;

        year = 0;
        month = 0;
        day = 0;
        return length;


    }

    private int calculateLengthYear(int days){
        logger.info("calculateLengthYear");
        int lengthyear =0;
        lengthyear += days / 30 / 12;

        year = 0;
        return lengthyear;


    }





    private long proceccDate(LocalDate localDate, LocalDate localDateNow){
        logger.info("proceccDate");

        long days = ChronoUnit.DAYS.between(localDate, localDateNow);

        /*long years  = YEARS.between(localDate, localDateNow);
        long months = MONTHS.between(localDate.plusYears(years), localDateNow);
        long days   = DAYS.between(localDate.plusYears(years).plusMonths(months),
                localDateNow);

        System.out.println("Years1 - " + years + ", month1 - " + months + ", days - " + days);

        Period period = Period.of((int) years, (int) months, (int) days);
        System.out.printf("%d year%s, %d month%s, and %d day%s%n",
                years,  pluralize(years),
                months, pluralize(months),
                days,   pluralize(days));
        //System.out.println("Years - " + years + ", month - " + months + ", days - " + days );
        //Period period = Period.between(localDate, localDateNow);
        //System.out.println("Periods:" + period.getYears() + " " + period.getMonths() + " " + period.getDays());*/

        return days;

    }

    private static String pluralize(long num) {
        logger.info("pluralize");
        return num == 1 ? "" : "s";
    }

    public String[] getYearmonthday() {
            logger.info("getYearmonthday");
        return yearmonthday;}

    private String returnDate(String date){
        logger.info("returnDate = "  + date);
        String[] dates;
        if (date != null){
            dates = date.split("-");
            logger.info("Result = " + dates[2] + "." + dates[1] + "." + dates[0]);
            return dates[2] + "." + dates[1] + "." + dates[0];
        }
        return  null;
    }

    private String returnDateForDatabase(String date){
        String[] dates;
        if (date != null){
            dates = date.split("\\.");
            return dates[2] + "-" + dates[1] + "-" + dates[0];
        }
        return  null;
    }
}
