import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conexion = getConnection();
        // buscarClientes(conexion);
        // buscarClientePorCodigo(conexion,1);
        //buscarClientesPorEmpleado(conexion,8);
        // getProductosParaReponer(conexion,16);
        getProductosGama(conexion,"Frutales");
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
    public static void buscarClientes(Connection conexion) {
        String sql = "SELECT nombre_contacto, apellido_contacto, telefono FROM cliente ";
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                String nombre = rs.getString("nombre_contacto");
                String apellido = rs.getString("apellido_contacto");
                String telefono = rs.getString("telefono");
                count++;
                System.out.println(count + " - " + nombre + " " + apellido + " " + telefono);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    public static void buscarClientePorCodigo(Connection conexion, int codigo) {
        String sql = "SELECT nombre_contacto, apellido_contacto, telefono FROM cliente WHERE id_cliente = "+ codigo;
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String nombre = rs.getString("nombre_contacto");
                String apellido = rs.getString("apellido_contacto");
                String telefono = rs.getString("telefono");
                System.out.println("Cliente encontrado: " + nombre + " " + apellido + " - Teléfono: " + telefono);
            }else {
                System.out.println("No se encontró ningún cliente con el código: " + codigo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    public static void buscarClientesPorEmpleado(Connection conexion, int codigo) {
        String sql = "SELECT c.* " +
                "FROM cliente c " +
                "INNER JOIN empleado e ON e.id_empleado = c.id_empleado " +
                "WHERE e.id_empleado = " + codigo;
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                String nombre = rs.getString("nombre_contacto");
                String apellido = rs.getString("apellido_contacto");
                String telefono = rs.getString("telefono");
                count++;
                System.out.println(count + " - Cliente: " + nombre + " " + apellido + " - Teléfono: " + telefono);
            }
            if (count == 0) {
                System.out.println("No se encontraron clientes asociados al empleado con código: " + codigo);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    public static void getProductosParaReponer(Connection conexion, int puntoReposicion) {
        String sql = "SELECT nombre, cantidad_en_stock " +
                "FROM producto p " +
                "WHERE p.cantidad_en_stock < " + puntoReposicion;
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                String nombreProducto = rs.getString("nombre");
                int stock = rs.getInt("cantidad_en_stock");
                count++;
                System.out.println(count + " - Producto: " + nombreProducto + " - Stock " + stock);
            }
            if (count == 0) {
                System.out.println("No hay productos que necesiten reabastecimiento.");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    public static void getProductosGama(Connection conexion, String nombreGama) {
        String sql = "SELECT p.codigo_producto, p.nombre, p.id_gama, g.gama " +
                "FROM producto p " +
                "INNER JOIN gama_producto g ON p.id_gama = g.id_gama " +
                "WHERE g.gama = '" +nombreGama + "'";
        try {
            Statement stmt = conexion.createStatement();
            System.out.println("Consulta SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                String codigoProducto = rs.getString("codigo_producto");
                String nombreProducto = rs.getString("nombre");
                int codigoGama = rs.getInt("id_gama");
                String nombreGamaRetornado = rs.getString("gama");
                count++;
                System.out.println(count + " - Código: " + codigoProducto +
                        ", Producto: " + nombreProducto +
                        ", Código de Gama: " + codigoGama +
                        ", Gama: " + nombreGamaRetornado);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
}
