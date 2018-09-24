/*
 *By Yauheni Uzhakhau
 *Minsk city, Belarus
 *2018 year
 *For School #83 name's G.K.Shukowa
 */
package interfaces;

import ConnectionPooling.DataSource;

import java.sql.*;

/**
 * Class for send query on server for find users or admins which input these data
 */
public class LogIn implements Authorization{

    /**
     * Send query on server
     * @param login
     * @param passw
     * @return
     * @throws Exception
     */
    @Override
    public Boolean sendQuery(String login, String passw){


        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(getSQLQuery(login, passw));
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.first()) {
                while (resultSet.next()) {
                    String str = "id_users = " + resultSet.getString("id_users") +
                            "; id_staff = " + resultSet.getString("id_staff")
                            + "; login = " + resultSet.getString("login") +
                            "; passw = " + resultSet.getString("passw") +
                            "; lvl_access = " + resultSet.getString("lvl_access");
                    System.out.println("info: " + str);
                }
                resultSet.close();
                stmt.close();
                connection.close();
                //connectionPool.closePool();
                return true;
            }
            if (!resultSet.first()) {
                return false;
            }

            resultSet.close();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get query
     */
    /**Create and get correct query*/
    private String getSQLQuery(String login, String password){
        if (login.equals("root")
                || login.equals("admin")){
            return "SELECT * FROM `root_admins` WHERE `login`='" + login
                    + "' && `passw`='" + password+"'";
        }
        return  "SELECT * FROM `users` WHERE `login`='" + login
                + "' && `passw`='" + password+"'";
    }
}
