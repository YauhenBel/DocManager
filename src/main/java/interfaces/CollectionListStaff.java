/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package interfaces;

import ConnectionPooling.ConnectionPool;

import ConnectionPooling.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import objects.Staff;

import java.sql.*;


import static java.sql.Types.NULL;

/**
 * This class for connects with DB
 */
public class CollectionListStaff implements ListStaff {

    private ObservableList<Staff> staffList =
            FXCollections.observableArrayList();

    /**
     * Add new staff in DB
     */
    @Override
    public void addInDb(Staff staff) throws SQLException {

            //insert data in "staff" table
            PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement(
                    "INSERT INTO staff (idStaff, surname, name, fatherName, " +
                            "typeWork, position, addInfo) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setNull(1, NULL);
            preparedStatement.setString(2, staff.getmSurname());
            preparedStatement.setString(3, staff.getmName());
            preparedStatement.setString(4, staff.getmFathName());
            preparedStatement.setInt(5, staff.getmTypeWork());
            preparedStatement.setString(6, staff.getmPosition());
            preparedStatement.setString(7, staff.getmAddInfo());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                System.out.println(resultSet.getString(1));
            }

            //insert data in "contacts" table
            preparedStatement = DataSource.getConnection().prepareStatement(
                "INSERT INTO contacts (idStaff, address, anyAddress, tel1, tel2) " +
                        "VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(lastId));
            preparedStatement.setString(2, staff.getmAddress());
            preparedStatement.setString(3, staff.getmAnyAddress());
            preparedStatement.setString(4, staff.getmTel1());
            preparedStatement.setString(5, staff.getmTel2());
            preparedStatement.executeUpdate();

            //insert data in "dateofbirth" table
            preparedStatement = DataSource.getConnection().prepareStatement(
                    "INSERT INTO dateofbirth (idStaff, dateOfBirth) " +
                            "VALUES(?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(lastId));
            preparedStatement.setString(2, staff.getmDateOfBirth());
            preparedStatement.executeUpdate();

            //insert data in "documents" table
            preparedStatement = DataSource.getConnection().prepareStatement(
                    "INSERT INTO documents (idStaff, idDoc, privateNum) " +
                            "VALUES(?, ?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(lastId));
            preparedStatement.setString(2, staff.getmIdDoc());
            preparedStatement.setString(3, staff.getmDocPrivetNum());
            preparedStatement.executeUpdate();

            //return a new data
            preparedStatement = DataSource.getConnection().prepareStatement("SELECT `staff`.`idStaff`, " +
                    "`staff`.`surname`, " +
                    "`staff`.`name`, " +
                    "`staff`.`fatherName`, " +
                    "`dateofbirth`.`dateOfBirth`, " +
                    "`documents`.`idDoc`, " +
                    "`documents`.`privateNum`, " +
                    "`contacts`.`address`, " +
                    "`contacts`.`anyAddress`, " +
                    "`contacts`.`tel1`, " +
                    "`contacts`.`tel2`,  " +
                    "`staff`.`addInfo`, " +
                    "`staff`.`typeWork`, " +
                    "`staff`.`position` " +
                    "FROM `staff` JOIN `dateofbirth` ON `dateofbirth`.`idStaff` = `staff`.`idStaff` " +
                    "INNER JOIN `contacts` ON `contacts`.`idStaff` = `staff`.`idStaff` " +
                    "INNER JOIN `documents` ON `documents`.`idStaff` = `staff`.`idStaff` WHERE `staff`.`idStaff` = ?");
            preparedStatement.setString(1, lastId);
            System.out.println();
            for (int i = 0; i < 100; i++) System.out.print("-");
            processAnswer(preparedStatement.executeQuery());
            preparedStatement.close();
    }

    /**
     * Add staf in List
     *
     * @param staff
     */
    public void addS(Staff staff) {
        staffList.add(staff);
    }

    /**
     * Update staff's select in DB
     *
     * @param staff
     */
    @Override
    public void update(Staff staff) {
        System.out.println("Update");
        try {
            PreparedStatement preparedStatement =
                    DataSource.getConnection().prepareStatement("UPDATE staff "+
                            "SET " +
                            "surname = ?, " +
                            "name = ?, " +
                            "fatherName = ?, " +
                            "typeWork = ?, " +
                            "position = ?, " +
                            "addInfo = ? " +
                            "WHERE idStaff = ?");
            preparedStatement.setString(1, staff.getmSurname());
            preparedStatement.setString(2, staff.getmName());
            preparedStatement.setString(3, staff.getmFathName());
            preparedStatement.setInt(4, staff.getmTypeWork());
            preparedStatement.setString(5, staff.getmPosition());
            preparedStatement.setString(6, staff.getmAddInfo());
            preparedStatement.setInt(7, staff.getmIdstaff());


            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Deleted select of staff from db and list
     *
     * @param staff
     */
    @Override
    public void delete(Staff staff) throws SQLException {
        //ConnectionPool connectionPool = new ConnectionPool();
        System.out.print("1: ");
        //connectionPool.printDbStatus();
            PreparedStatement preparedStatement = DataSource.getConnection().prepareStatement("DELETE FROM staff"
                    + " WHERE staff.idStaff = ?");
        System.out.print("2: ");
        //connectionPool.printDbStatus();
            preparedStatement.setInt(1, staff.getmIdstaff());
        System.out.print("3: ");
        //connectionPool.printDbStatus();
            preparedStatement.executeUpdate();
            preparedStatement.close();
            staffList.remove(staff);
    }

    public void clearList() {
        staffList.clear();
    }

    public ObservableList<Staff> getStaffList() {
        return staffList;
    }

    /**
     * Download data from DB
     */
    public void fillData() throws Exception {
                PreparedStatement preparedStatement =
                        DataSource.getConnection().prepareStatement("SELECT `staff`.`idStaff`, " +
                                "`staff`.`surname`, " +
                                "`staff`.`name`, " +
                                "`staff`.`fatherName`, " +
                                "`dateofbirth`.`dateOfBirth`, " +
                                "`documents`.`idDoc`, " +
                                "`documents`.`privateNum`, " +
                                "`contacts`.`address`, " +
                                "`contacts`.`anyAddress`, " +
                                "`contacts`.`tel1`, " +
                                "`contacts`.`tel2`,  " +
                                "`staff`.`addInfo`, " +
                                "`staff`.`typeWork`, " +
                                "`staff`.`position` " +
                                "FROM `staff` JOIN `dateofbirth` ON `dateofbirth`.`idStaff` = `staff`.`idStaff` " +
                                "INNER JOIN `contacts` ON `contacts`.`idStaff` = `staff`.`idStaff` " +
                                "INNER JOIN `documents` ON `documents`.`idStaff` = `staff`.`idStaff`");
                processAnswer(preparedStatement.executeQuery());
                preparedStatement.close();
    }

    /**
     * Processing answer from DB
     *
     * @param rs
     * @throws SQLException
     */
    private void processAnswer(ResultSet rs) throws SQLException {
        while (rs.next()) {
            for (int i = 0; i < 100; i++) System.out.print("-");
            System.out.println();
            String str = "\nidStaff = " + rs.getString("idStaff")
                    + "\nsurname = " + rs.getString("surname")
                    + "\nname = " + rs.getString("name")
                    + "\nfatherName = " + rs.getString("fatherName")
                    + "\ndateOfBirth = " + rs.getString("dateOfBirth")
                    + "\nidDoc = " + rs.getString("idDoc")
                    + "\nprivateNum = " + rs.getString("privateNum")
                    + "\naddress = " + rs.getString("address")
                    + "\nanyAddress = " + rs.getString("anyAddress")
                    + "\ntel1 = " + rs.getString("tel1")
                    + "\ntel2 = " + rs.getString("tel2")
                    + "\naddInfo = " + rs.getString("addInfo")
                    + "\ntypeWork = " + rs.getString("typeWork")
                    + "\nposition = " + rs.getString("position")+"\n";
            System.out.print("\ninfo: " + str);

            staffList.add(new Staff(
                    Integer.parseInt(rs.getString("idStaff")),
                    rs.getString("surname"),
                    rs.getString("name"),
                    rs.getString("fatherName"),
                    rs.getString("dateOfBirth"),
                    rs.getString("idDoc"),
                    rs.getString("privateNum"),
                    rs.getString("address"),
                    rs.getString("anyAddress"),
                    rs.getString("tel1"),
                    rs.getString("tel2"),
                    rs.getString("addInfo"),
                    Integer.parseInt(rs.getString("typeWork")),
                    rs.getString("position")
                    ));

        }
        rs.close();
    }


}
