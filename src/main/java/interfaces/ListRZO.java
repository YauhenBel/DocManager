package interfaces;

import javafx.collections.ObservableList;
import objects.RZO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListRZO {
    void fillData(Integer id);

    void insertNewRZO(RZO rzo, int idStaff);

    void updateRZO(RZO rzo);

    void delRZO(RZO rzo);

    void processAnswer(ResultSet rs) throws SQLException;

    ObservableList<RZO> getRZOList();

    void clearList();
}
