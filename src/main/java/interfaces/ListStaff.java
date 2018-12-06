package interfaces;

import objects.Staff;

import java.sql.SQLException;

public interface ListStaff {

    void delete(Staff staff) throws SQLException;
}
