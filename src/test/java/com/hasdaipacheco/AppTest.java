package com.hasdaipacheco;

import org.junit.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static SQLProvider provider;

    //@BeforeClass
    public static void prepare() {
        provider = SQLProvider.getInstance();
        provider.connect("jdbc:mysql://localhost/inventariodb", "root", "");
    }

    //@Test
    public void shouldConnectToDataBaseWithUserAndPassword() {
        Assert.assertNotNull(provider.getConnection());
    }

    //@Test
    public void shouldInsertRecordToDataBase() {
        PreparedStatement st = null;
        try {
             st = provider.getConnection()
                    .prepareStatement("INSERT INTO usuario (nombre, apellido, nacimiento, codigo_postal, ciudad, direccion)" +
                            " values (?, ?, ?, ?, ?, ?)");

            st.setString(1, "Jorge");
            st.setString(2, "Ramirez");
            st.setDate(3, new Date(System.currentTimeMillis()));
            st.setInt(4, 62725);
            st.setString(5, "Ayala");
            st.setString(6, "Calle");

            st.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            if (null != st) {
                try {
                    st.close();
                } catch (SQLException sqe) {

                }
            }
        }
    }

    //@AfterClass
    public static void cleanUp() {
        PreparedStatement deleteAll = null;

        try {
            deleteAll = provider.getConnection().prepareStatement("DELETE FROM usuario");
        } catch (SQLException sqe) {

        }
        provider.disconnect();
    }
}
