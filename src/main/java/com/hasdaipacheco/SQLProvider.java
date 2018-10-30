package com.hasdaipacheco;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLProvider implements DataProvider {
    private static final Logger LOG = Logger.getLogger(SQLProvider.class.getName());
    private static SQLProvider instance;
    private static Connection connection;
    private boolean driverLoaded = false;

    /**
     * Obtiene una instancia de SQLProvider
     * @return SQLProvider
     */
    public static SQLProvider getInstance() {
        if (instance == null) {
            instance = new SQLProvider();
        }
        return instance;
    }

    private SQLProvider() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            driverLoaded = true;
        } catch (Exception cnfe) {
            LOG.log(Level.SEVERE, "Error al realizar la conexión", cnfe);
        }
    }

    /**
     * Crea una conexión a la base de datos MySQL
     * @param url URL de conexión a la base de datos
     * @param user Usuario
     * @param pass Password
     * @return true si la conexión se ha realizado
     */
    public boolean connect(String url, String user, String pass) {
        if (driverLoaded) {
            try {
                connection = DriverManager.getConnection(url, user, pass);
                return true;
            } catch (SQLException sqe) {
                LOG.log(Level.SEVERE, "Error al realizar la conexión", sqe);
            }
        }
        return false;
    }

    /**
     * Se desconecta de la base de datos MySQL
     * @return true si se ha podido desconectar
     */
    public boolean disconnect() {
        if (null != connection) {
            try {
                connection.close();
                return true;
            } catch (SQLException sqe) {
                LOG.log(Level.SEVERE, "Error al realizar la desconexión", sqe);
            }
        }
        return false;
    }

    /**
     * Ejecuta una consulta tipo select a la base de datos.
     * @param st Sentencia SQL a ejecutar.
     * @return ResultSet con los resultados de la consulta.
     */
    public ResultSet executeSelectQuery(PreparedStatement st) {
        try {
            return st.executeQuery();
        } catch (SQLException sqe) {
            LOG.log(Level.SEVERE, "Error al realizar la consulta", sqe);
        }
        return null;
    }

    /**
     * Ejecuta una consulta de tipo actualización a la base de datos.
     * @param st Sentencia SQL a ejecutar.
     * @return número de registros afectados por la actualización.
     */
    public int executeUpdateQuery(PreparedStatement st) {
        try {
            return st.executeUpdate();
        } catch (SQLException sqe) {
            LOG.log(Level.SEVERE, "Error al realizar la actualización", sqe);
        }
        return 0;
    }

    /**
     * Devuelve la conexión asociada al DataProvider.
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }
}
