package interfaces;

import javafx.collections.ObservableList;
import objects.Parthneriship;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListParthnership {
    void fillData(Integer id);

    void insertNewParthnership(Parthneriship parthneriship, int idStaff);

    void updateParthnership(Parthneriship parthneriship);

    void delParthnership(Parthneriship parthneriship);

    void processAnswer(ResultSet rs) throws SQLException;

    ObservableList<Parthneriship> getParthnerishipList();

    void clearList();
}
