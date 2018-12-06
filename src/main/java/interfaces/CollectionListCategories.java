package interfaces;

import ConnectionPooling.DataSource;
import Other.SetupClearButtonField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Category;
import objects.Holiday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Types.NULL;

public class CollectionListCategories implements ListCategories{

    private ObservableList<Category> categoriesList = FXCollections.observableArrayList();
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
                    "SELECT * FROM category WHERE idStaff = ?");
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
    public void insertNewCategory(Category category, int idStaff) {
        logger.info("insertNewCategory");
        PreparedStatement insertCategory = null;
        ResultSet resultSet = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            insertCategory = connection.prepareStatement(
                    "INSERT INTO category (idCategory, idStaff, typeCategory, " +
                            "levelCategory, dateCategory)"
                            + "VALUES(?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            insertCategory.setNull(1, NULL);
            insertCategory.setInt(2, idStaff);
            insertCategory.setString(3, category.getTypeCategory());
            insertCategory.setString(4, category.getLevelCategory());
            insertCategory.setString(5, returnDateForDatabase(category.getDateCategory()));
            insertCategory.executeUpdate();
            resultSet = insertCategory.getGeneratedKeys();
            String lastId = null;
            while (resultSet.next()) {
                lastId = resultSet.getString(1);
                System.out.println("lastId = " + resultSet.getString(1));
            }
            category.setIdCategory(lastId);
            categoriesList.add(category);
            connection.commit();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        } finally {
            SetupClearButtonField.closeConnections(insertCategory, connection);
        }
    }

    @Override
    public void updateCategory(Category category) {
        logger.info("updateCategory");

        PreparedStatement updateCategory = null;

        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            updateCategory = connection.prepareStatement("UPDATE category "+
                    "SET " +
                    "typeCategory = ?, " +
                    "levelCategory = ?, " +
                    "dateCategory = ? " +
                    "WHERE idCategory = ?");
            System.out.println(returnDateForDatabase(category.getDateCategory()));
            updateCategory.setString(1,category.getTypeCategory() );
            updateCategory.setString(2, category.getLevelCategory());
            updateCategory.setString(3, returnDateForDatabase(category.getDateCategory()));
            updateCategory.setString(4, category.getIdCategory());

            updateCategory.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            SetupClearButtonField.closeConnections(connection);
        }finally {
            SetupClearButtonField.closeConnections(updateCategory, connection);
        }
    }

    @Override
    public void delCategory(Category category) {
        logger.info("delCategory");
        PreparedStatement preparedStatement = null;
        try {
            connection = DataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM category WHERE idCategory = ?");
            preparedStatement.setString(1, category.getIdCategory());
            preparedStatement.executeUpdate();
            connection.commit();
            categoriesList.remove(category);
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
                str = "\nidCategory = " + rs.getString("idCategory")
                        + "\ntypeCategory = " + rs.getString("typeCategory")
                        +"\nlevelCategory = " + rs.getString("levelCategory")
                        +"\ndateCategory = " + returnDate(rs.getString("dateCategory"));
                logger.info("\n" + str);
                categoriesList.add(new Category(
                        rs.getString("idCategory"),
                        rs.getString("typeCategory"),
                        rs.getString("levelCategory"),
                        returnDate(rs.getString("dateCategory"))
                ));
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        }
    }

    @Override
    public ObservableList<Category> getCategoriesList() {
        logger.info("getCategoriesList");
        return categoriesList;
    }

    @Override
    public void clearList() {
        logger.info("clearList");
        categoriesList.clear();
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
}
