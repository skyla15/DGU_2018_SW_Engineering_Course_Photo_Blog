package dbControl;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    public DBConnect() {
    }

    public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/insta";
        String id = "sophist";
        String pw = "1234";

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,id,pw);
        }catch(Exception e) {
            System.out.println(e);
        }
        return con;
    }
    
}
