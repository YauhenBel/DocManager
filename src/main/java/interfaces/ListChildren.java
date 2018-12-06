package interfaces;

import javafx.collections.ObservableList;
import objects.Child;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListChildren {

    void fillData(Integer id);

    void insertNewChild(Child child, int idStaff);

    void updateChild(Child child);

    void delChild(Child child);

    void processAnswer(ResultSet resultSet) throws SQLException;

    ObservableList<Child> getChildList();

    void clearList();
}
