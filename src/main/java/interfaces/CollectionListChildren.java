package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Child;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListChildren implements ListChildren {

    private ObservableList<Child> mChildList = FXCollections.observableArrayList();
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
                    "SELECT * FROM children WHERE idStaff = ?");
            preparedStatement.setInt(1, id);
            processAnswer(preparedStatement.executeQuery());
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(preparedStatement, connection);
        }

    }

    @Override
    public void insertNewChild(Child child, int idStaff) {
        logger.info("insertNewChild");
        PreparedStatement insertChild = null;
        ResultSet resultSet = null;
        String date = "";
        if (child.getmDateOfBirth() == null) {
            date = null;
        }else {
            String[] dateS = child.getmDateOfBirth().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertChild = connection.prepareStatement(
                    "INSERT INTO children (idChild, idStaff, surname, name, fathName, "
                            + "dateOfBirth) "
                            + "VALUES(?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertChild.setNull(1, NULL);
            insertChild.setInt(2, idStaff);
            insertChild.setString(3, child.getmSurname());
            insertChild.setString(4, child.getmName());
            insertChild.setString(5, child.getmFathName());
            insertChild.setString(6, date);
            insertChild.executeUpdate();
            resultSet = insertChild.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                logger.info("lastId = " + resultSet.getString(1));
                System.out.println("lastId = " + resultSet.getString(1));
            }
            child.setmIdChild(lastId);
            mChildList.add(child);
            connection.commit();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertChild, connection);
        }

    }

    @Override
    public void updateChild(Child child) {
        logger.info("updateChild");
        PreparedStatement updatetChild = null;
        String date = "";
        if (child.getmDateOfBirth() == null) {
            date = null;
        }else {
            String[] dateS = child.getmDateOfBirth().split("\\.");
            date = dateS[2] + "-" + dateS[1] + "-" + dateS[0];
        }

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updatetChild = connection.prepareStatement("UPDATE children "+
                    "SET " +
                    "name = ?, " +
                    "surname = ?, " +
                    "fathName = ?, " +
                    "dateOfBirth = ? " +
                    "WHERE idChild = ?");

            updatetChild.setString(1, child.getmName());
            updatetChild.setString(2, child.getmSurname());
            updatetChild.setString(3, child.getmFathName());
            updatetChild.setString(4, date);
            updatetChild.setString(5, child.getmIdChild());

            updatetChild.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updatetChild, connection);
        }
    }

    @Override
    public void delChild(Child child) {
        logger.info("delChild");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM children WHERE idChild = ?");
            preparedStatement.setString(1, child.getmIdChild());
            preparedStatement.executeUpdate();
            connection.commit();
            mChildList.remove(child);
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
                if (rs.getString("dateOfBirth") == null){
                    date = null;
                } else  {
                    dateS = rs.getString("dateOfBirth").split("-");
                    date = dateS[2] + "." + dateS[1] + "." + dateS[0];
                }
                String str = null;
                str = "\nidChild = " + rs.getString("idChild")
                        + "\nsurname = " + rs.getString("surname")
                        +"\nname = " + rs.getString("name")
                        +"\nfathName = " + rs.getString("fathName")
                        +"\ndateOfBirth = " + date;
                logger.info("\ninfo child:" + str);
                mChildList.add(new Child(
                        rs.getString("idChild"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("fathName"),
                        date
                ));
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    @Override
    public ObservableList<Child> getChildList() {
        logger.info("getChildList");
        return mChildList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        mChildList.clear();

    }
}
