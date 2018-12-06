package interfaces;

import javafx.collections.ObservableList;
import objects.Holiday;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListHoliday {

    void fillData(Integer id);

    void insertNewHoliday(Holiday holiday, int idStaff);

    void updateHoliday(Holiday holiday);

    void delHoliday(Holiday holiday);

    void processAnswer(ResultSet resultSet) throws SQLException;

    ObservableList<Holiday> getHolidaysList();

    void clearList();
}
