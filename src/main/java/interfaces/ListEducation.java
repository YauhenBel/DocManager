package interfaces;

import javafx.collections.ObservableList;
import objects.Education;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListEducation {

    void fillData(Integer id);

    void insertNewEd(Education education, int idStaff);

    void updateEd(Education education);

    void delEd(Education education);

    void processAnswer(ResultSet resultSet) throws SQLException;

    ObservableList<Education> getEducationList();

    void clearList();
}
