package main;

import java.sql.*;

public class DbHandler {
    private final String connectionUri = "jdbc:mysql://localhost/przemek"; //przemek to nazwa bazy w phpmyadmin
    private Connection conn;
    public DbHandler(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionUri, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String login, String pass){
        String query = "SELECT id FROM uzytkownicy WHERE Login=? AND Pass=?";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, pass);
            ResultSet set = statement.executeQuery();
            if(set.first()){// jesli set ma pierwszy obiekt to znaczy ze user istnieje
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
