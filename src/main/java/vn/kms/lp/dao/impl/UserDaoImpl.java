package vn.kms.lp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.kms.lp.dao.UserDao;
import vn.kms.lp.web.utils.Constants;
import vn.kms.lp.model.UserModel;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
    Properties userInfo;

    public UserDaoImpl() {
        userInfo = new Properties();
        userInfo.put("user", "postgres");
        userInfo.put("password", "124356");
    }

    public void checkDriver() {
        try {
            Class.forName(Constants.POSTGRES_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.error("Missing Driver");
        }
    }

    @Override
    public boolean checkUser(String userName, String passWord) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean userExisted = false;

        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            statement = connection.prepareStatement("SELECT EXISTS(SELECT 1 FROM USERS WHERE USERNAME = ?)");
            userExisted = statement.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }

        return userExisted;
    }

    public List<UserModel> getAll() {

        UserModel user = null;
        List<UserModel> users = new ArrayList<UserModel>();
        PreparedStatement statement = null;
        ResultSet result = null;

        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            statement = connection.prepareStatement("SELECT * FROM USERS");
            result = statement.executeQuery();
            while (result.next()) {
                user = new UserModel(result.getString("USERNAME"), result.getString("PASSWORD"));
                users.add(user);

            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }

        return users;
    }

    private void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignore) {
            // Ignore Exception
        }
    }

}
