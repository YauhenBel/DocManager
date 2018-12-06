package interfaces;

import javafx.collections.ObservableList;
import objects.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ListContract {
    void fillData(Integer id);

    int insertNewContract(Contract contract, int idStaff);

    void updateContract(Contract contract);

    void delContract(Contract contract);

    void processAnswer(ResultSet rs) throws SQLException;

    ObservableList<Contract> getListContract();

    void clearList();
}
