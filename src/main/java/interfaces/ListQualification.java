package interfaces;

import javafx.collections.ObservableList;
import objects.Qualification;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListQualification {
    void fillData(Integer id);

    void insertNewQualification(Qualification qualification, int idStaff);

    void updateQualification(Qualification qualification);

    void delQualification(Qualification qualification);

    void processAnswer(ResultSet resultSet) throws SQLException;

    ObservableList<Qualification> getQualificationList();

    void clearList();
}
