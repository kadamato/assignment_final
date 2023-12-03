import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    static  Connection conn;
    static Connection connect (String url , String user ,String  password) {
        try {
            conn =  DriverManager.getConnection(url , user , password);
        }
        catch (SQLException  ex) {
            ex.printStackTrace();
        }
        finally {
            return conn;

        }
    }
}


