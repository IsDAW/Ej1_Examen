/*
 * EJERCICIO 1 DEL EXAMEN
 */
package base_datos;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.SQLException;

/**
 *
 * @author DAW
 */
public class Base_Datos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Scanner lector = new Scanner(System.in);
            String nombre_base = "AGENDA";//BASE DE DATOS A UTILIZAR
            String ip;//IP DESDE LA QUE CONECTAMOS
            String nombre_tabla = "contactos";//TABLA A USAR
            String comando_mysql;//COMANDO A EJECUTAR
            ResultSet consulta;//CONSULTA REALIZADA
            int nuevo_id;//ID DE LA TABLA 
            String nuevo_nombre;//NOMBRE DE LA TABLA
            String nuevo_telefono;//TELEFONO DE LA TABLA
            boolean salida = false;//CONDICION BUCLE
            int filasAfectadas;//FILAS A LAS QUE HA AFECTADO EL COMANDO

            System.out.println("Introduce tu ip");//la actual es 10.230.109.219
            ip = lector.next();

            // Cargamos la clase que implementa el Driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Creamos una nueva conexión a la base de datos elegida
            String url = "jdbc:mysql://" + ip + ":3306/" + nombre_base + "?serverTimezone=UTC";

            while (salida == false) {

                Connection conexion = DriverManager.getConnection(url, "root", "");
                // Obtenemos un Statement de la conexión
                Statement st = conexion.createStatement();

                // MUESTRA LAS OPCIONES QUE TIENE EL MENU
                System.out.println("¿Que accion quieres realizar?");
                System.out.println("1.MOSTRAR TODO");
                System.out.println("2.AÑADIR DATOS");
                System.out.println("3.EDITAR CONTACTO");
                System.out.println("4.ELIMINAR CONTACTO");
                System.out.println("5.SALIR");
                int opciones = lector.nextInt();

                switch (opciones) {
                    case 1://MOSTRAR TODO
                        comando_mysql = "SELECT * FROM " + nombre_tabla;
                        consulta = st.executeQuery(comando_mysql);
                        while (consulta.next()) {//RECORRE LOS DATOS OBTENIDOS Y LOS MUESTRA

                            int id = consulta.getInt("id");
                            String nombre = consulta.getString("nombre");
                            String telefono = consulta.getString("telefono");

                            System.out.println("ID: " + id + " | " + "Nombre: " + nombre + " | " + "Telefono: " + telefono);
                        }//FIN WHILE
                        System.out.println("----------------------------------------------------------------------");
                        st.close();
                        conexion.close();
                        break;

                    case 2://AÑADIR DATOS

                        System.out.println("Introduce la nueva id");
                        nuevo_id = lector.nextInt();
                        System.out.println("Introduce el nuevo nombre");
                        nuevo_nombre = lector.next();
                        System.out.println("Introduce el nuevo telefono");
                        nuevo_telefono = lector.next();

                        comando_mysql = "INSERT INTO contactos (id, nombre, telefono) VALUES (" + nuevo_id + ", '" + nuevo_nombre + "', '" + nuevo_telefono + "')";

                        filasAfectadas = st.executeUpdate(comando_mysql);
                        System.out.println("Número de filas afectadas: " + filasAfectadas);
                        System.out.println("----------------------------------------------------------------------");
                        st.close();
                        conexion.close();

                        break;

                    case 3://EDITAR CONTACTO

                        System.out.println("¿Qué id quieres actualizar?");
                        nuevo_id = lector.nextInt();
                        System.out.println("Introduce el nuevo nombre");
                        nuevo_nombre = lector.next();
                        System.out.println("Introduce el nuevo telefono");
                        nuevo_telefono = lector.next();

                        comando_mysql = "UPDATE " + nombre_tabla + " SET nombre='" + nuevo_nombre + "', telefono='" + nuevo_telefono + "' WHERE id=" + nuevo_id;
                        filasAfectadas = st.executeUpdate(comando_mysql);
                        System.out.println("Número de filas afectadas: " + filasAfectadas);
                        System.out.println("----------------------------------------------------------------------");
                        st.close();
                        conexion.close();
                        break;

                    case 4://ELIMINAR CONTACTO

                        System.out.println("¿Qué id quieres eliminar?");
                        nuevo_id = lector.nextInt();

                        comando_mysql = "DELETE FROM " + nombre_tabla + " WHERE id=" + nuevo_id;
                        filasAfectadas = st.executeUpdate(comando_mysql);
                        System.out.println("Número de filas afectadas: " + filasAfectadas);
                        System.out.println("----------------------------------------------------------------------");
                        st.close();
                        conexion.close();
                        break;

                    case 5://SALIR
                        salida = true;

                }//FIN SWITCH
            }

            //RECOGEN LAS EXCEPCIONES DADAS
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println("Excepcion: " + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
