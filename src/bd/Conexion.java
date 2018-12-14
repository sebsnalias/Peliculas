package bd;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Sebastián
 */
public class Conexion {
    private String db = "peliculas";
    private String url = "jdbc:mysql://pi1509.ddns.net:3306/" + db;
    private String user = "Gamasys";
    private String pass = "gamasys";
    Connection con = null;
   
    public Conexion(){
        try{
            con = DriverManager.getConnection(url, user, pass);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Mensaje 001 " +e);         
        }
    }
    public Connection getConnection(){
        return con;
    }
    
    public void desconectar(){
        con = null;
    }
}
