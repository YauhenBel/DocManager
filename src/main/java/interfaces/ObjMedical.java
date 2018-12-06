package interfaces;

import objects.Medical;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjMedical {
    void fillData(Integer id);

    void insertNewMedical(Medical medical, int idStaff);

    void updateMedical(Medical medical, int idStaff);

    void processAnswer(ResultSet rs) throws SQLException;

    Medical getObjectMedical();
}
