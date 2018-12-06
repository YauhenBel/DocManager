package interfaces;

import javafx.collections.ObservableList;
import objects.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListCategories {
    void fillData(Integer id);

    void insertNewCategory(Category category, int idStaff);

    void updateCategory(Category category);

    void delCategory(Category category);

    void processAnswer(ResultSet resultSet) throws SQLException;

    ObservableList<Category> getCategoriesList();

    void clearList();
}
