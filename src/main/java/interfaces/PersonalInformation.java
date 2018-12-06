package interfaces;

import objects.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PersonalInformation {

    void downloadInformation(Integer id);

    void processAnswer(ResultSet rs) throws SQLException;

    void addInDb(Staff staff);

    void update(Staff staff);

    Staff getStaff();
}
