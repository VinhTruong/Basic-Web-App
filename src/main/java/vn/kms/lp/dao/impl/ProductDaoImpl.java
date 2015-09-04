package vn.kms.lp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

    @Override
    public List<ProductModel> findByName(List<ProductModel> products,String filterName) {
        return products.stream().filter(productModel -> filterName.equals(productModel.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByCategory(List<ProductModel> products, String category) {
        return products.stream().filter(productModel -> productModel.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByPrice(List<ProductModel> products, int fromPrice, int toPrice) {
        int price = 0;
        List<ProductModel> searchResult = new ArrayList<ProductModel>();
        for (int i = 1; i < products.size(); i++) {
            price = products.get(i).getPrice();
            if (price <= toPrice && price >= fromPrice) {
                searchResult.add(products.get(i));
            }
        }
        return searchResult;
    }
    
    @Override
    public List<ProductModel> findByName(String name) {
        List<ProductModel> products = new ArrayList<ProductModel>();
        PreparedStatement stmt = null;
        ResultSet result = null;
        
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
            String selectStmt = "SELECT * FROM PRODUCTS WHERE PRODUCT_NAME = ?";
            stmt = connection.prepareStatement(selectStmt);
            stmt.setString(1, name);
            result = stmt.executeQuery();
            
            while (result.next()) {
                int product_id = Integer.parseInt(result.getString("PRODUCT_ID"));
                String product_name = result.getString("PRODUCT_NAME");
                String product_category = result.getString("PRODUCT_CATEGORY");
                String product_desc = result.getString("PRODUCT_DESC");
                int product_price = Integer.parseInt(result.getString("PRODUCT_PRICE"));
                ProductModel product = new ProductModel(product_id, product_name, product_category, 
                                                          product_desc, product_price);
                products.add(product);
            }
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
        return products;
    }

    @Override
    public List<ProductModel> findByCategory(String category) {
        List<ProductModel> products = new ArrayList<ProductModel>();
        PreparedStatement stmt = null;
        ResultSet result = null;
        
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
            String selectStmt = "SELECT * FROM PRODUCTS WHERE PRODUCT_CATEGORY = ?";
            stmt = connection.prepareStatement(selectStmt);
            stmt.setString(1, category);
            result = stmt.executeQuery();
            
            while (result.next()) {
                int product_id = Integer.parseInt(result.getString("PRODUCT_ID"));
                String product_name = result.getString("PRODUCT_NAME");
                String product_category = result.getString("PRODUCT_CATEGORY");
                String product_desc = result.getString("PRODUCT_DESC");
                int product_price = Integer.parseInt(result.getString("PRODUCT_PRICE"));
                ProductModel product = new ProductModel(product_id, product_name, product_category, 
                                                          product_desc, product_price);
                products.add(product);
            }
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
        return products;
    }

    @Override
    public List<ProductModel> findByPrice(int fromPrice, int toPrice) {
        List<ProductModel> products = new ArrayList<ProductModel>();
        PreparedStatement stmt = null;
        ResultSet result = null;
        
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
            String selectStmt = "SELECT * FROM PRODUCTS WHERE PRODUCT_PRICE >= ? AND PRODUCT_PRICE <= ?";
            stmt = connection.prepareStatement(selectStmt);
            stmt.setLong(1, fromPrice);
            stmt.setLong(2, toPrice);
            result = stmt.executeQuery();
            
            while (result.next()) {
                int product_id = Integer.parseInt(result.getString("PRODUCT_ID"));
                String product_name = result.getString("PRODUCT_NAME");
                String product_category = result.getString("PRODUCT_CATEGORY");
                String product_desc = result.getString("PRODUCT_DESC");
                int product_price = Integer.parseInt(result.getString("PRODUCT_PRICE"));
                ProductModel product = new ProductModel(product_id, product_name, product_category, 
                                                          product_desc, product_price);
                products.add(product);
            }
        } catch (SQLException e) {
            LOG.info(e.toString());
        }
        return products;
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

        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
            String updateStmt = "UPDATE PRODUCTS SET"
                                 + "PRODUCT_NAME = ?"
                                 + "PRODUCT_CATEGORY = ?"
                                 + "PRODUCT_DESC = ?"
                                 + "PRODUCT_PRICE = ?"
                                 + "WHERE PRODUCT_ID = ?";
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
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
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
        
        try (Connection connection = DriverManager.getConnection(Constants.JDBC, userInfo); ) {
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
