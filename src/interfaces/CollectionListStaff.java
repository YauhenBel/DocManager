package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CollectionListStaff implements ListStaff{

    private ObservableList<Staff> staffList =
            FXCollections.observableArrayList();
    @Override
    public void add(Staff staff) {
        staffList.add(staff);
    }

    @Override
    public void update(Staff staff) {

    }

    @Override
    public void delete(Staff staff) {
        staffList.remove(staff);
    }

    public ObservableList<Staff> getStaffList() {
     return staffList;
    }

    public void filltestData()
    {

        //staffList.add(new Staff("gfgfgfg", "fgfg", "fzgg", "zgfdga"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/office_datas";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT * FROM `staff`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_staff = " + rs.getString("id_staff")
                            + ",\nsurname = " + rs.getString("surname")
                            + ",\nname = " + rs.getString("name")
                            + ",\nfather_name = " + rs.getString("father_name")
                            + ",\ntd_of_birth = "+ rs.getString("d_of_birth")
                            + ",\nnum_passp = " + rs.getString("num_passp")
                            + ",\npassp_private_num = " + rs.getString("passp_private_num")
                            + ",\naddress = " + rs.getString("address")
                            + ",\ntel_1 = "+ rs.getString("tel_1")
                            + ",\ntel_2 = " + rs.getString("tel_2")
                            + ",\nadd_info = " + rs.getString("add_info")
                            + ",\ntype_work = " + rs.getString("type_work")
                            + ",\nposition = "+ rs.getString("position") + ";\n}";
                    System.out.println("info: " + str);
                    String xId =  rs.getString("id_staff");
                    String xSurname =rs.getString("surname");
                    String xName = rs.getString("name");
                    String xFathName = rs.getString("father_name");
                    String xDateOfBirth = rs.getString("d_of_birth");
                    String xNumPassp = rs.getString("num_passp");
                    String xPasspPrivateNum = rs.getString("passp_private_num");
                    String xAddress = rs.getString("address");
                    String xTel1 = rs.getString("tel_1");
                    String xTel2 = rs.getString("tel_2");
                    String xAddInfo = rs.getString("add_info");
                    String xTypeWork = "";
                    if (rs.getString("type_work").equals("1"))
                    {
                        xTypeWork = "Педагог";
                    } else if (rs.getString("type_work").equals("2")){
                        xTypeWork = "Технический сотрудник";
                    }
                    String xPosition = rs.getString("position");
                    staffList.add(new Staff(xId, xSurname, xName, xFathName,
                            xDateOfBirth, xNumPassp, xPasspPrivateNum, xAddress,
                            xTel1, xTel2, xAddInfo, xTypeWork, xPosition));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print()
    {
        System.out.println("Check dates in staffList\n");
        for (Staff staff: staffList)
        {
            System.out.println("{"
                    + "\nId = " + staff.getID_staff()
                    + ",\nSurname = " + staff.getSurname()
                    + ",\nName = " + staff.getName()
                    + ",\nFatherName = " + staff.getFathName()
                    + ",\nDateOfBirth = " + staff.getDateOfBirth()
                    + ",\nNumPassp = " + staff.getNummPass()
                    + ",\nNumPrivate = " + staff.getNumPrivate()
                    + ",\nAddress = " + staff.getAddress()
                    + ",\nTel1 = " + staff.getTel1()
                    + ",\nTel2 = " + staff.getTel2()
                    + ",\nAddInfo = " + staff.getAddInfo()
                    + ",\nTypeWork = " + staff.getTypeWork()
                    + ",\nPosition = " + staff.getPosition()
                    + "\n}");


        }
    }


}
