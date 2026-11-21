package org.example;


import java.sql.*;
import java.util.Scanner;

public class GestionRecetas {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n===== GESTOR DE RECETAS =====");
            System.out.println("1. Mostrar todas las recetas");
            System.out.println("2. Buscar receta por ID");
            System.out.println("3. Añadir receta");
            System.out.println("4. Modificar receta");
            System.out.println("5. Eliminar receta");
            System.out.println("6. Buscar receta por nombre");
            System.out.println("7. Añadir receta con transacción");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    mostrarRecetas();
                    break;

                case 2:
                    buscarPorId();
                    break;

                case 3:
                    anadirReceta();
                    break;

                case 4:
                    modificarReceta();
                    break;

                case 5:
                    eliminarReceta();
                    break;

                case 6:
                    buscarPorNombre();
                    break;

                case 7:
                    anadirRecetaTransaccion();
                    break;

                case 0:
                    System.out.println("Cerrando el programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // 1. MOSTRAR TODAS LAS RECETAS
    public static void mostrarRecetas() {

        String sql = "SELECT * FROM recetas";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n===== LISTADO DE RECETAS =====");

            while (rs.next()) {

                Receta receta = new Receta(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("tiempo_preparacion"),
                        rs.getString("dificultad"),
                        rs.getInt("raciones")
                );

                System.out.println(receta);
            }

        } catch (SQLException e) {
            System.out.println("Error al mostrar las recetas: " + e.getMessage());
        }
    }

    // 2. BUSCAR RECETA POR ID
    public static void buscarPorId() {

        System.out.print("Introduce el ID de la receta: ");
        int id = sc.nextInt();

        String sql = "SELECT * FROM recetas WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Receta receta = new Receta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getInt("tiempo_preparacion"),
                            rs.getString("dificultad"),
                            rs.getInt("raciones")
                    );

                    System.out.println(receta);

                } else {
                    System.out.println("No se ha encontrado la receta.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }

    // 3. AÑADIR RECETA
    public static void anadirReceta() {

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Categoría: ");
        String categoria = sc.nextLine();

        System.out.print("Tiempo de preparación: ");
        int tiempo = sc.nextInt();
        sc.nextLine();

        System.out.print("Dificultad: ");
        String dificultad = sc.nextLine();

        System.out.print("Número de raciones: ");
        int raciones = sc.nextInt();

        Receta receta = new Receta(
                nombre, categoria, tiempo, dificultad, raciones
        );

        String sql = """
                INSERT INTO recetas
                (nombre, categoria, tiempo_preparacion, dificultad, raciones)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, receta.getNombre());
            pstmt.setString(2, receta.getCategoria());
            pstmt.setInt(3, receta.getTiempoPreparacion());
            pstmt.setString(4, receta.getDificultad());
            pstmt.setInt(5, receta.getRaciones());

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Receta añadida correctamente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al añadir receta: " + e.getMessage());
        }
    }

    // 4. MODIFICAR RECETA
    public static void modificarReceta() {

        System.out.print("Introduce el ID de la receta: ");
        int id = sc.nextInt();

        System.out.print("Nuevo tiempo de preparación: ");
        int tiempo = sc.nextInt();

        System.out.print("Nuevo número de raciones: ");
        int raciones = sc.nextInt();

        String sql = """
                UPDATE recetas
                SET tiempo_preparacion = ?, raciones = ?
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, tiempo);
            pstmt.setInt(2, raciones);
            pstmt.setInt(3, id);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Receta modificada correctamente.");
            } else {
                System.out.println("No existe una receta con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
        }
    }

    // 5. ELIMINAR RECETA
    public static void eliminarReceta() {

        System.out.print("Introduce el ID de la receta: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM recetas WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Receta eliminada correctamente.");
            } else {
                System.out.println("No existe una receta con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    // 6. BUSCAR RECETA POR NOMBRE MEDIANTE PROCEDIMIENTO ALMACENADO
    public static void buscarPorNombre() {

        System.out.print("Introduce el nombre de la receta: ");
        String nombre = sc.nextLine();

        String sql = "{CALL buscarReceta(?)}";

        try (Connection con = ConexionBD.getConexion();
             CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setString(1, nombre);

            try (ResultSet rs = cstmt.executeQuery()) {

                boolean encontrada = false;

                while (rs.next()) {

                    encontrada = true;

                    Receta receta = new Receta(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getInt("tiempo_preparacion"),
                            rs.getString("dificultad"),
                            rs.getInt("raciones")
                    );

                    System.out.println(receta);
                }

                if (!encontrada) {
                    System.out.println("No se ha encontrado la receta.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }

    // 7. AÑADIR RECETA Y ACTUALIZAR ESTADÍSTICAS MEDIANTE TRANSACCIÓN
    public static void anadirRecetaTransaccion() {

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Categoría: ");
        String categoria = sc.nextLine();

        System.out.print("Tiempo de preparación: ");
        int tiempo = sc.nextInt();
        sc.nextLine();

        System.out.print("Dificultad: ");
        String dificultad = sc.nextLine();

        System.out.print("Número de raciones: ");
        int raciones = sc.nextInt();

        String sqlReceta = """
                INSERT INTO recetas
                (nombre, categoria, tiempo_preparacion, dificultad, raciones)
                VALUES (?, ?, ?, ?, ?)
                """;

        String sqlEstadisticas = """
                UPDATE estadisticas
                SET total_recetas = total_recetas + 1
                WHERE id = 1
                """;

        Connection con = null;

        try {

            con = ConexionBD.getConexion();

            // Desactivamos el autocommit
            con.setAutoCommit(false);

            // Insertamos la receta
            try (PreparedStatement pstmt =
                         con.prepareStatement(sqlReceta)) {

                pstmt.setString(1, nombre);
                pstmt.setString(2, categoria);
                pstmt.setInt(3, tiempo);
                pstmt.setString(4, dificultad);
                pstmt.setInt(5, raciones);

                pstmt.executeUpdate();
            }

            // Actualizamos las estadísticas
            try (PreparedStatement pstmt =
                         con.prepareStatement(sqlEstadisticas)) {

                int filas = pstmt.executeUpdate();

                if (filas == 0) {
                    throw new SQLException(
                            "No se ha encontrado el registro de estadísticas."
                    );
                }
            }

            // Confirmamos las dos operaciones
            con.commit();

            System.out.println("Receta añadida y estadísticas actualizadas.");

        } catch (SQLException e) {

            System.out.println("Error en la transacción: " + e.getMessage());

            try {
                if (con != null) {
                    // Deshacemos todos los cambios
                    con.rollback();

                    System.out.println("Transacción cancelada.");
                }

            } catch (SQLException ex) {
                System.out.println("Error en rollback: " + ex.getMessage());
            }

        } finally {

            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}

