//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.lp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJDBC {
    private static final Logger LOG = LoggerFactory.getLogger(TestJDBC.class.getCanonicalName());

    @Test
    public void testConnection() {
        try {
            // Check necessary driver
            Class.forName("org.postgresql.Driver");

            String jdbc = "jdbc:postgresql://localhost:5432/WebAppDatabase";
            Properties userInfo = new Properties();
            userInfo.put("user", "postgres");
            userInfo.put("password", "124356");

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet result = null;
            try {
                connection = DriverManager.getConnection(jdbc, userInfo);
                statement = connection.prepareStatement("SELECT * FROM PRODUCTS");
                result = statement.executeQuery();
                while (result.next()) {
                    LOG.info(result.getString(1));
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            } finally {
                close(result);
                close(statement);
                close(connection);
            }

        } catch (ClassNotFoundException e) {
            LOG.error("Missing necessary Driver: " + e.getMessage());
        }
    }

    private void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignore) {
        }
    }
}
