package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import objects.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static java.sql.Types.NULL;

public class GetPersonalInformation implements PersonalInformation {
    Staff staff;
    private Connection connection;
    private static Logger logger = LogManager.getLogger();
    @Override
    public void downloadInformation(Integer id) {
        logger.info("downloadInformation");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement =
                    connection.prepareStatement("SELECT `staff`.`idStaff`, " +
                            "`staff`.`surname`, " +
                            "`staff`.`name`, " +
                            "`staff`.`fatherName`, " +
                            "`dateofbirth`.`dateOfBirth`, " +
                            "`documents`.`idNum`, " +
                            "`documents`.`passpNum`, " +
                            "`contacts`.`address`, " +
                            "`contacts`.`anyAddress`, " +
                            "`contacts`.`isSameAddress`, " +
                            "`contacts`.`tel1`, " +
                            "`contacts`.`tel2`,  " +
                            "`staff`.`addInfo`, " +
                            "`staff`.`typeWork`, " +
                            "`staff`.`position` " +
                            "FROM `staff` JOIN `dateofbirth` ON `dateofbirth`.`idStaff` = `staff`.`idStaff` " +
                            "INNER JOIN `contacts` ON `contacts`.`idStaff` = `staff`.`idStaff` " +
                            "INNER JOIN `documents` ON `documents`.`idStaff` = `staff`.`idStaff` WHERE `staff`.`idStaff` = " + id);
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
    public void processAnswer(ResultSet rs) throws SQLException {
        logger.info("processAnswer");
        while (rs.next()) {
            String str = null;
            try {
                str = "idStaff = " + rs.getString("idStaff")
                        + "\nsurname = " + rs.getString("surname")
                        + "\nname = " + rs.getString("name")
                        + "\nfatherName = " + rs.getString("fatherName")
                        + "\ndateOfBirth = " + rs.getString("dateOfBirth")
                        + "\nidNum = " + rs.getString("idNum")
                        + "\npasspNum = " + rs.getString("passpNum")
                        + "\naddress = " + rs.getString("address")
                        + "\nanyAddress = " + rs.getString("anyAddress")
                        + "\nisSameAddress = " + rs.getBoolean("isSameAddress")
                        + "\ntel1 = " + rs.getString("tel1")
                        + "\ntel2 = " + rs.getString("tel2")
                        + "\naddInfo = " + rs.getString("addInfo")
                        + "\ntypeWork = " + rs.getString("typeWork")
                        + "\nposition = " + rs.getString("position")+"\n";

                logger.info("\nGetPersonalInformation: \n" + str);


                staff = new Staff(
                        Integer.parseInt(rs.getString("idStaff")),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("fatherName"),
                        rs.getString("dateOfBirth"),
                        rs.getString("idNum"),
                        rs.getString("passpNum"),
                        rs.getString("address"),
                        rs.getString("anyAddress"),
                        rs.getBoolean("isSameAddress"),
                        rs.getString("tel1"),
                        rs.getString("tel2"),
                        rs.getString("addInfo"),
                        Integer.parseInt(rs.getString("typeWork")),
                        rs.getString("position"));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
        rs.close();
    }

    @Override
    public void addInDb(Staff staff) {
        logger.info("addInDb");
        PreparedStatement insertStaff = null;
        PreparedStatement insertContacts = null;
        PreparedStatement insertDateOfBirth = null;
        PreparedStatement insertDocuments = null;
        PreparedStatement selectStaff = null;
        PreparedStatement insertLengthWork = null;
        ResultSet resultSet = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertStaff = connection.prepareStatement(
                    "INSERT INTO staff (idStaff, surname, name, fatherName, " +
                            "typeWork, position, addInfo, status) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertContacts = connection.prepareStatement(
                    "INSERT INTO contacts (idStaff, address, anyAddress, isSameAddress, tel1, tel2) " +
                            "VALUES(?, ?, ?, ?, ?, ?)");

            insertDateOfBirth = connection.prepareStatement(
                    "INSERT INTO dateofbirth (idStaff, dateOfBirth) " +
                            "VALUES(?, ?)");

            insertDocuments = connection.prepareStatement(
                    "INSERT INTO documents (idStaff, idNum, passpNum) " +
                            "VALUES(?, ?, ?)");

            insertLengthWork = connection.prepareStatement("INSERT INTO lengthwork (id, idStaff, years, " +
                    "generalyears, outsidelength) " +
                    "VALUES(?, ?, ?, ?, ?)");

            selectStaff = connection.prepareStatement("SELECT `staff`.`idStaff`, " +
                    "`staff`.`surname`, " +
                    "`staff`.`name`, " +
                    "`staff`.`fatherName`, " +
                    "`dateofbirth`.`dateOfBirth`, " +
                    "`documents`.`idNum`, " +
                    "`documents`.`passpNum`, " +
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


            insertStaff.setNull(1, NULL);
            insertStaff.setString(2, staff.getmSurname());
            insertStaff.setString(3, staff.getmName());
            insertStaff.setString(4, staff.getmFathName());
            insertStaff.setInt(5, staff.getmTypeWork());
            insertStaff.setString(6, staff.getmPosition());
            insertStaff.setString(7, staff.getmAddInfo());
            insertStaff.setInt(8,1);
            insertStaff.executeUpdate();
            resultSet = insertStaff.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                logger.info("lastId = " + resultSet.getString(1));
            }

            //insert data in "contacts" table
            insertContacts.setInt(1, Integer.parseInt(lastId));
            insertContacts.setString(2, staff.getmAddress());
            insertContacts.setString(3, staff.getmAnyAddress());
            insertContacts.setBoolean(4, staff.getIsIsSameAddress());
            insertContacts.setString(5, staff.getmTel1());
            insertContacts.setString(6, staff.getmTel2());
            insertContacts.executeUpdate();

            //insert data in "dateofbirth" table

            insertDateOfBirth.setInt(1, Integer.parseInt(lastId));
            insertDateOfBirth.setString(2, staff.getmDateOfBirth());


            insertDateOfBirth.executeUpdate();

            //insert data in "documents" table

            insertDocuments.setInt(1, Integer.parseInt(lastId));
            insertDocuments.setString(2, staff.getmIdNum());
            insertDocuments.setString(3, staff.getmPasspNum());
            insertDocuments.executeUpdate();

            //return a new data

            selectStaff.setString(1, lastId);

            insertLengthWork.setNull(1, NULL);
            insertLengthWork.setInt(2, Integer.parseInt(lastId));
            insertLengthWork.setInt(3, 0);
            insertLengthWork.setInt(4, 0);
            insertLengthWork.setString(5, "0-0-0");

            insertLengthWork.executeUpdate();
            processAnswer(selectStaff.executeQuery());
            connection.commit();
            staff.setmIdstaff(Integer.parseInt(lastId));
            //resultSet.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {

            try {
                SetupClearButtonField.closeConnections(insertStaff, connection);
                SetupClearButtonField.closeConnections(insertContacts);
                SetupClearButtonField.closeConnections(insertDocuments);
                SetupClearButtonField.closeConnections(insertDateOfBirth);
                SetupClearButtonField.closeConnections(selectStaff);
                SetupClearButtonField.closeConnections(insertLengthWork);
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

    }

    public void updateOutsideYears(String outsideYears, int idStaff){

        logger.info("updateOutsideYears");
        logger.info("outsideYears = " + outsideYears);

        PreparedStatement updateGeneralYears = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateGeneralYears = connection.prepareStatement("UPDATE lengthwork "+
                    "SET " +
                    "outsidelength = ? " +
                    "WHERE idStaff = ?");

            updateGeneralYears.setString(1, outsideYears);
            updateGeneralYears.setInt(2, idStaff);
            updateGeneralYears.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(updateGeneralYears, connection);
        }
    }

    public String returnDate(Staff staff){
        logger.info("returnDate");
        if (staff.getmDateOfBirth() == null){
            System.out.println("Return null");
            return "null";
        }
        return staff.getmDateOfBirth();
    }

    /**
     * Update staff's select in DB
     *
     * @param staff
     */
    @Override
    public void update(Staff staff) {
        logger.info("update");

        PreparedStatement updateStaff = null;
        PreparedStatement updateContacts = null;
        PreparedStatement updateDateOfBirth = null;
        PreparedStatement updateDocuments = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateStaff = connection.prepareStatement("UPDATE staff "+
                    "SET " +
                    "surname = ?, " +
                    "name = ?, " +
                    "fatherName = ?, " +
                    "typeWork = ?, " +
                    "position = ?, " +
                    "addInfo = ? " +
                    "WHERE idStaff = ?");

            updateContacts = connection.prepareStatement("UPDATE contacts "+
                    "SET " +
                    "address = ?, " +
                    "anyAddress = ?, " +
                    "isSameAddress = ?, " +
                    "tel1 = ?, " +
                    "tel2 = ? " +
                    "WHERE idStaff = ?");

            updateDateOfBirth = connection.prepareStatement("UPDATE dateofbirth "+
                    "SET " +
                    "dateOfBirth = ? " +
                    "WHERE idStaff = ?");

            updateDocuments = connection.prepareStatement("UPDATE documents "+
                    "SET " +
                    "idNum = ?, " +
                    "passpNum = ? " +
                    "WHERE idStaff = ?");

            updateStaff.setString(1, staff.getmSurname());
            updateStaff.setString(2, staff.getmName());
            updateStaff.setString(3, staff.getmFathName());
            updateStaff.setInt(4, staff.getmTypeWork());
            updateStaff.setString(5, staff.getmPosition());
            updateStaff.setString(6, staff.getmAddInfo());
            updateStaff.setInt(7, staff.getmIdstaff());

            updateContacts.setString(1, staff.getmAddress());
            updateContacts.setString(2, staff.getmAnyAddress());
            updateContacts.setBoolean(3, staff.getIsIsSameAddress());
            updateContacts.setString(4, staff.getmTel1());
            updateContacts.setString(5, staff.getmTel2());
            updateContacts.setInt(6, staff.getmIdstaff());

            updateDateOfBirth.setString(1, staff.getmDateOfBirth());
            updateDateOfBirth.setInt(2, staff.getmIdstaff());

            updateDocuments.setString(1, staff.getmIdNum());
            updateDocuments.setString(2, staff.getmPasspNum());
            updateDocuments.setInt(3, staff.getmIdstaff());

            updateStaff.executeUpdate();
            updateContacts.executeUpdate();
            updateDateOfBirth.executeUpdate();
            updateDocuments.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(updateStaff, connection);
            SetupClearButtonField.closeConnections(updateContacts);
            SetupClearButtonField.closeConnections(updateDateOfBirth);
            SetupClearButtonField.closeConnections(updateDocuments);
        }
    }

    @Override
    public Staff getStaff() {
        logger.info("getStaff");
        return staff;
    }
}
