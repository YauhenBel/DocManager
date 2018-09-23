package interfaces;

import objects.Staff;

import java.sql.SQLException;

public interface ListStaff {

    void addInDb(Staff staff) throws ClassNotFoundException, SQLException;

    void  update(Staff staff) throws ClassNotFoundException, SQLException;

    void delete(Staff staff) throws SQLException;
}
