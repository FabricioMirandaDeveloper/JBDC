import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conexion = getConnection();
        cerrarConexion(conexion);
    }

    public static Connection getConnection() {
        String host = "127.0.0.1";
        String port = "3306";
        String name = "root";
        String password = "root";
        String database = "vivero";
        String zona = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String url = "jdbc:mysql://"+host+":"+port+"/"+database+zona;

        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,name,password);
            System.out.println("Conexion exitosa a la base de datos");
        }catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador JDBC: "+ e.getMessage());
        }catch (SQLException e) {
            System.out.println("Error de conexion "+ e.getMessage());
        }
        return conexion;
    }
    public static void cerrarConexion(Connection conexion) {
        if(conexion != null) {
            try {
                conexion.close();
                System.out.println("La conexion a la base fue cerrada de manera correcta");
            }catch (SQLException e) {
                System.out.println("Error al cerrar la conexion: "+ e.getMessage());
            }
        }
    }
}
