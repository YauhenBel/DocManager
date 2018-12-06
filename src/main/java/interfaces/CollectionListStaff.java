/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package interfaces;


import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import objects.Filter;
import objects.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;

/**
 * This class for connects with DB
 */
public class CollectionListStaff implements ListStaff {

    private ObservableList<Staff> staffList =
            FXCollections.observableArrayList();
    private Connection connection;
    private int colFinishWorkers, colFinishMed;
    private String listWorkers, listmed;
    private Filter filter = null;

    private static Logger logger = LogManager.getLogger();

    /**
     * Add staf in List
     *
     * @param staff
     */
    public Staff addS(Staff staff) {
        logger.info("addS");

        Staff staff1 = new Staff(staff.getmIdstaff(), staff.getmSurname(), staff.getmName(), staff.getmFathName(),
                staff.getmPosition());

        staffList.add(staff1);

        return staff1;
    }

    /**
     * Deleted select of staff from db and list
     *
     * @param staff
     */
    @Override
    public void delete(Staff staff){
        logger.info("delete");
        System.out.println("Id = " + staff.getmIdstaff());
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM staff"
                    + " WHERE staff.idStaff = ?");
            preparedStatement.setInt(1, staff.getmIdstaff());
            preparedStatement.executeUpdate();
            connection.commit();
            staffList.remove(staff);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }

    }

    public void clearList() {
        logger.info("clearList");
        staffList.clear();
    }

    public ObservableList<Staff> getStaffList() {
        logger.info("getStaffList");
        return staffList;
    }

    /**
     * Download data from DB
     */
    public void fillData(){
        logger.info("fillData");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement =
                    connection.prepareStatement("SELECT `staff`.`idStaff`, " +
                            "`staff`.`surname`, " +
                            "`staff`.`name`, " +
                            "`staff`.`fatherName`, " +
                            "`staff`.`position` " +
                            "FROM `staff`");
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

    /**
     * Processing answer from DB
     *
     * @param rs
     * @throws SQLException
     */
    public void processAnswer(ResultSet rs) throws SQLException {
        logger.fatal("processAnswer");
        while (rs.next()) {
            String str = null;
            try {
                str = "\nidStaff = " + rs.getString("idStaff")
                        + "\nsurname = " + rs.getString("surname")
                        + "\nname = " + rs.getString("name")
                        + "\nfatherName = " + rs.getString("fatherName")
                        + "\nposition = " + rs.getString("position")+"\n";

                logger.info("\ninfo:\n" + str+"\n");
                staffList.add(new Staff(
                        Integer.parseInt(rs.getString("idStaff")),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("fatherName"),
                        rs.getString("position")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
        rs.close();
    }

    public void fillFinishWorkers(){
        logger.info("fillFinishWorkers");
        listWorkers = "";
        colFinishWorkers = 0;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement =
                    connection.prepareStatement("SELECT `staff`.`surname`, `staff`.`name`, `staff`.`fatherName`, " +
                            "`staff`.`position`, `contracts`.`dFinish` FROM `staff` JOIN `contracts` " +
                            "ON `staff`.`idStaff` = `contracts`.`IdStaff` " +
                            "WHERE TO_DAYS(`contracts`.`dFinish`) - TO_DAYS(NOW()) >= 30 " +
                            "AND TO_DAYS(`contracts`.`dFinish`) - TO_DAYS(NOW()) <= 40");
            processFinishWorkers(preparedStatement.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }
    }

    public void fillFinishMed(){
        logger.info("fillFinishMed");
        listmed = "";
        colFinishMed = 0;
        PreparedStatement fluoro = null;
        PreparedStatement ads = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            fluoro =
                    connection.prepareStatement("SELECT `staff`.`surname`, `staff`.`name`, `staff`.`fatherName`, " +
                            "`staff`.`position`, `medical`.`fluoro` FROM `staff` JOIN `medical` " +
                            "ON `staff`.`idStaff` = `medical`.`idStaff` " +
                            "WHERE TO_DAYS(`medical`.`fluoro`) - TO_DAYS(NOW()) >= 30 " +
                            "AND TO_DAYS(`medical`.`fluoro`) - TO_DAYS(NOW()) <= 40");

            ads =
                    connection.prepareStatement("SELECT `staff`.`surname`, `staff`.`name`, `staff`.`fatherName`, " +
                            "`staff`.`position`, `medical`.`ads` FROM `staff` JOIN `medical` " +
                            "ON `staff`.`idStaff` = `medical`.`idStaff` " +
                            "WHERE TO_DAYS(`medical`.`ads`) - TO_DAYS(NOW()) >= 30 " +
                            "AND TO_DAYS(`medical`.`ads`) - TO_DAYS(NOW()) <= 40");
            processFinishfluoro(fluoro.executeQuery());
            processFinishads(ads.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(fluoro, connection);
            SetupClearButtonField.closeConnections(ads);
        }
    }
    private void processFinishWorkers(ResultSet rs) throws SQLException {
        logger.info("processFinishWorkers");
        while (rs.next()) {
            for (int i = 0; i < 100; i++) System.out.print("-");
            String str = null;
            try {
                str = "\nsurname = " + rs.getString("surname")
                        + "\nname = " + rs.getString("name")
                        + "\nfatherName = " + rs.getString("fatherName")
                        + "\nposition = " + rs.getString("position")
                        + "\ndFinish = " + returnDate(rs.getString("dFinish")) +"\n";
                logger.info("\ninfo Finish Contract:" + str);
                listWorkers +="ФИО:" + " " + rs.getString("surname") + " " + rs.getString("name") +
                        " " + rs.getString("name") + "\n" +
                        "Должность: " + rs.getString("position") + "\n" +
                        "Окончание контракта/договора: " + returnDate(rs.getString("dFinish")) + "\n\n";
                colFinishWorkers+=1;
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
        rs.close();
    }

    private void processFinishfluoro(ResultSet rs) throws SQLException {
        logger.info("processFinishfluoro");
        while (rs.next()) {
            for (int i = 0; i < 100; i++) System.out.print("-");
            String str = "\nsurname = " + rs.getString("surname")
                    + "\nname = " + rs.getString("name")
                    + "\nfatherName = " + rs.getString("fatherName")
                    + "\nposition = " + rs.getString("position")
                    + "\nfluoro = " + returnDate(rs.getString("fluoro")) +"\n";
            logger.info("\ninfo Finish fluoro:" + str);
            listmed +="Прививка АДС: \nФИО:" + " " + rs.getString("surname") + " " + rs.getString("name") +
                    " " + rs.getString("name") + "\n" +
                    "Должность: " + rs.getString("position") + "\n" +
                    "Окончание: " + returnDate(rs.getString("fluoro")) + "\n\n";
            colFinishMed+=1;

        }
        rs.close();
    }

    private void processFinishads(ResultSet rs) throws SQLException {
        logger.info("processFinishads");
        while (rs.next()) {
            for (int i = 0; i < 100; i++) System.out.print("-");
            String str = "\nsurname = " + rs.getString("surname")
                    + "\nname = " + rs.getString("name")
                    + "\nfatherName = " + rs.getString("fatherName")
                    + "\nposition = " + rs.getString("position")
                    + "\nads = " + returnDate(rs.getString("ads")) +"\n";
            logger.info("\ninfo Finish fluoro:" + str);
            listmed +="Флюорография: \nФИО:" + " " + rs.getString("surname") + " " + rs.getString("name") +
                    " " + rs.getString("name") + "\n" +
                    "Должность: " + rs.getString("position") + "\n" +
                    "Окончание: " + returnDate(rs.getString("ads")) + "\n\n";
            colFinishMed+=1;

        }
        rs.close();
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

    public String getListWorkers(){
        logger.info("getListWorkers");
        return listWorkers;}

    public int getColFinishWorkers() {
        logger.info("getColFinishWorkers");
        return colFinishWorkers;}

    public String getListmed() {
        logger.info("getListmed");
        return listmed;}

    public int getColFinishMed() {
        logger.info("getColFinishMed");
        return colFinishMed;}

    public void toFilter(Filter filter){
        logger.info("toFilter");
        this.filter = filter;
        staffList.clear();
        PreparedStatement filterStaff = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            filterStaff =
                    connection.prepareStatement(getQuery());
            processAnswer(filterStaff.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(filterStaff, connection);
        }
    }

    private String getQuery(){
        logger.info("getQuery");
        int status = 0;
        String query = "SELECT `staff`.`idStaff`, `staff`.`surname`, `staff`.`name`, " +
                "`staff`.`fatherName`, `staff`.`position` FROM `staff` JOIN " ;

        if (!filter.getYearFrom().isEmpty() || !filter.getYearTo().isEmpty()) {
            query+="`dateofbirth` ON `dateofbirth`.`idStaff` = `staff`.`idStaff`";
            status = 1;
        }

        if (!filter.getLengthWorkFrom().isEmpty() || !filter.getLengthWorkTo().isEmpty()){
            if (status == 1) {
                query+=" INNER JOIN ";
            }else {
                status = 1;
            }

            query+="`lengthwork` ON `lengthwork`.`idStaff` = `staff`.`idStaff`";
        }

        if (filter.getFormContract() != null){
            if (status == 1) {
                query+=" INNER JOIN ";
            }else {
                status = 1;
            }

            query+="`contracts` ON `contracts`.`idStaff` = `staff`.`idStaff`";
        }

        if (filter.getLevelCategory() != null){
            if (status == 1) {
                query+=" INNER JOIN ";
            }else {
                status = 1;
            }

            query+="`category` ON `category`.`idStaff` = `staff`.`idStaff`";
        }

        if (filter.getLevelEducation() != null){
            if (status == 1) {
                query+=" INNER JOIN ";
            }else {
                status = 1;
            }

            query+="`education` ON `education`.`idStaff` = `staff`.`idStaff`";
        }

        if (filter.getHoliday() != null){
            if (status == 1) {
                query+=" INNER JOIN ";
            }else {
                status = 1;
            }

            query+="`holidays` ON `holidays`.`idStaff` = `staff`.`idStaff`";
        }

        query+= " WHERE ";

        status = 0;


        if (!filter.getYearFrom().isEmpty()){
            status = 1;
            query+=" TIMESTAMPDIFF(YEAR,`dateOfBirth`,curdate()) >= " + filter.getYearFrom();
        }
        if (!filter.getYearTo().isEmpty()){
            if (status == 1){
                query+= " AND ";
            }
            query+=" TIMESTAMPDIFF(YEAR,`dateOfBirth`,curdate()) <= " + filter.getYearTo();
            status = 1;
        }

        if (!filter.getLengthWorkFrom().isEmpty() && !filter.isGeneralLengthWork()){
            if (status == 1){
                query+= " AND ";
            }
            query+="`lengthwork`.`years` >= " + filter.getLengthWorkFrom();
            status = 1;
        }

        if (!filter.getLengthWorkTo().isEmpty() && !filter.isGeneralLengthWork()){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `lengthwork`.`years` <= " + filter.getLengthWorkTo();
            status = 1;
        }

        if (!filter.getLengthWorkFrom().isEmpty() && filter.isGeneralLengthWork()){
            if (status == 1){
                query+= " AND ";
            }
            query+="`lengthwork`.`generalyears` >= " + filter.getLengthWorkFrom();
            status = 1;
        }

        if (!filter.getLengthWorkTo().isEmpty() && filter.isGeneralLengthWork()){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `lengthwork`.`generalyears` <= " + filter.getLengthWorkTo();
            status = 1;
        }

        if (filter.getFormContract() != null){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `contracts`.`type` = '" + filter.getFormContract() + "'";
            status = 1;
        }

        if (filter.getLevelCategory() != null){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `category`.`levelCategory` = '" + filter.getLevelCategory() + "'";
            status = 1;
        }

        if (filter.getLevelEducation() != null){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `education`.`levelEducation` = '" + filter.getLevelEducation() + "'";
            status = 1;
        }

        if (filter.getHoliday() != null){
            if (status == 1){
                query+= " AND ";
            }
            query+=" `holidays`.`typeHoliday` = '" + filter.getHoliday() + "'";
            status = 1;
        }
        logger.info("Фильтрующий запрос:\n" + query);
        return query;
    }
}
