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

import vn.kms.lp.dao.UserFactory;
import vn.kms.lp.web.utils.Constants;
import vn.kms.lp.model.UserModel;

public class UserFactoryImpl implements UserFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserFactoryImpl.class);
	private List<UserModel> users;
	private UserModel user;

	public UserFactoryImpl() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<UserModel>();
		user = null;
		//fetchData();
	}

	@Override
	public boolean CheckUser(String userName, String passWord) {
		UserModel user = new UserModel(userName, passWord);
		if (users.contains(user)) {
			return true;
		}
		return false;
	}

	public void fetchData() {
		users.clear(); // exclude problem when add or delete into db
		try {
			// Check necessary driver
			Class.forName("org.postgresql.Driver");

			String jdbc = Constants.JDBC;
			Properties userInfo = new Properties();
			userInfo.put("user", "postgres");
			userInfo.put("password", "124356");

			Connection connection = null;
			PreparedStatement statement = null;
			ResultSet result = null;
			try {
				connection = DriverManager.getConnection(jdbc, userInfo);
				statement = connection.prepareStatement("SELECT * FROM USERS");
				result = statement.executeQuery();
				while (result.next()) {
					user = new UserModel(result.getString(1),
											result.getString(2));
					users.add(user);

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
			//
		}
	}
}
