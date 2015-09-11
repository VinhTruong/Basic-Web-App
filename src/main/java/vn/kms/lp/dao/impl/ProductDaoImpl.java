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

import vn.kms.lp.dao.ProductDao;
import vn.kms.lp.model.ProductModel;
import vn.kms.lp.web.utils.Constants;

public class ProductDaoImpl implements ProductDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDaoImpl.class);
    Properties userInfo;

    public ProductDaoImpl() throws SQLException {
        userInfo = new Properties();
        userInfo.put("user", Constants.DB_USERNAME);
        userInfo.put("password", Constants.DB_PWD);
        checkDriver();

    }

    public void checkDriver() {
        try {
            // Check necessary driver
            Class.forName(Constants.POSTGRES_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.error("Missing necessary Driver: " + e.getMessage());
        }
        LOG.info("Initialized");
    }

    public boolean updateProduct(String id, String name, String category, String desc, String price) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean success = false;
        id = id.trim();
        name = name.trim();
        category = category.trim();
        desc = desc.trim();
        price = price.trim();

        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            String updateStmt = "UPDATE PRODUCTS SET" + "PRODUCT_NAME = ?" + "PRODUCT_CATEGORY = ?" + "PRODUCT_DESC = ?"
                    + "PRODUCT_PRICE = ?" + "WHERE PRODUCT_ID = ?";
            statement = connection.prepareStatement(updateStmt);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, desc);
            statement.setString(4, price);
            statement.setString(5, id);

            LOG.info("Access db:" + statement.toString());
            statement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }
        return success;
    }

    @Override
    public boolean addProduct(String name, String category, String desc, String price) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean success = false;
        name = name.trim();
        category = category.trim();
        desc = desc.trim();
        price = price.trim();
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            String insertStmt = "INSERT INTO PRODUCTS (PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_DESC,PRODUCT_PRICE)"
                    + "VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(insertStmt);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, desc);
            statement.setString(4, price);
            LOG.info("Access db:" + statement.toString());
            statement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }
        return success;
    }

    @Override
    public boolean deleteProduct(String id) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            String delStmt = "DELETE FROM PRODUCTS WHERE PRODUCT_ID = ?";
            statement = connection.prepareStatement(delStmt);
            statement.setString(1, id);
            statement.executeUpdate();
            LOG.info("Delete completed");
            success = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }
        return success;
    }

    @Override
    public List<ProductModel> search(String name, String category, String fromCost, String toCost) {
        List<ProductModel> searchResult = new ArrayList<ProductModel>();
        ProductModel product = null;
        String searchStmt = prepareSearchStmt(name, category, fromCost, toCost);
        
        PreparedStatement statement = null;
        ResultSet result = null;
        
        int parameterIndex = 1;
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo);) {
            statement = connection.prepareStatement(searchStmt);
            
            if (!name.equals("")) {
                statement.setString(parameterIndex, name);
                parameterIndex++;
            }

            if (!category.equals("")) {
                statement.setString(parameterIndex, category);
                parameterIndex++;
            }
            
            if (!fromCost.equals("")) {
                statement.setString(parameterIndex, fromCost);
                parameterIndex++;
            }
            
            if (!toCost.equals("")) {
                statement.setString(parameterIndex, toCost);
                parameterIndex++;
            }
            
            result = statement.executeQuery();
            while (result.next()) {
                product = new ProductModel(result.getInt("product_id"), 
                                             result.getString("product_name"), 
                                             result.getString("product_category"), 
                                             result.getString("product_desc"),
                                             result.getInt("product_price"));
                searchResult.add(product); 
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
        }
        
        return searchResult;
    }

    public String prepareSearchStmt(String name, String category, String fromCost, String toCost) {
        int parameterCounter = 0;
        StringBuilder searchStmt = new StringBuilder();
        searchStmt.append("SELECT * FROM PRODUCTS WHERE ");

        if (!name.equals("")) {
            searchStmt.append("PRODUCT_NAME = ? ");
            parameterCounter++;
        }

        if (!category.equals("")) {
            if (parameterCounter == 0) {
                searchStmt.append("LOWER(PRODUCT_CATEGORY) = ? ");
            } else {
                searchStmt.append("AND LOWER(PRODUCT_CATEGORY) = ? ");
            }            
            parameterCounter++;
        }
        
        if (!fromCost.equals("")) {
            if (parameterCounter == 0) {
                searchStmt.append("PRODUCT_PRICE <= ? ");
            } else {
                searchStmt.append("AND PRODUCT_PRICE <= ? ");
            }            
            parameterCounter++;
        }
        
        if (!toCost.equals("")) {
            if (parameterCounter == 0) {
                searchStmt.append("PRODUCT_PRICE >= ? ");
            } else {
                searchStmt.append("AND PRODUCT_PRICE >= ? ");
            }            
            parameterCounter++;
        }
        
        return searchStmt.toString();      
    }
    
    private void close(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception ignore) {
            // Ignore all
        }
    }

}
