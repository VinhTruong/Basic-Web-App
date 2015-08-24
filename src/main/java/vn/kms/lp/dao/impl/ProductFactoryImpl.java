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

import vn.kms.lp.dao.ProductFactory;
import vn.kms.lp.model.ProductModel;
import vn.kms.lp.web.utils.Constants;

public class ProductFactoryImpl implements ProductFactory {

    private List<ProductModel> products;

    private static final Logger LOG = LoggerFactory.getLogger(ProductFactoryImpl.class);
    private static ProductModel product;
    private static Properties userInfo;
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet result;

    public ProductFactoryImpl() throws SQLException {
        products = new ArrayList<ProductModel>();
        product = null;

        userInfo = new Properties();
        userInfo.put("user", Constants.DB_USERNAME);
        userInfo.put("password", Constants.DB_PWD);

        initialConnection();

    }

    public void initialConnection() {

        try {
            // Check necessary driver
            Class.forName(Constants.POSTGRES_DRIVER);
            connection = DriverManager.getConnection(Constants.JDBC, userInfo);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOG.error("Missing necessary Driver: " + e.getMessage());
        }
        LOG.info("Initialized");

    }

    @Override
    public List<ProductModel> findByName(String filterName) {
        return products.stream().filter(productModel -> filterName.equals(productModel.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByCategory(String category) {
        return products.stream().filter(productModel -> productModel.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByPrice(int fromPrice, int toPrice) {
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

    public void fetchData() {
        LOG.info("Start Fetching data");
        products.clear(); // exclude problem when add or delete into db
        products = new ArrayList<ProductModel>();
        try {
            if (connection.isClosed()) {
                LOG.info("Connect Again");
                connection = DriverManager.getConnection(Constants.JDBC, userInfo);
            }
            statement = connection.prepareStatement("SELECT * FROM PRODUCTS");
            result = statement.executeQuery();
            while (result.next()) {
                product = new ProductModel(result.getInt(1), result.getString(2), result.getString(3),
                        result.getString(4), result.getInt(5));
                products.add(product);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
            close(connection);
        }

    }

    public void updateProduct(String id, String name, String category, String desc, String price) {
        id = id.trim();
        name = name.trim();
        category = category.trim();
        desc = desc.trim();
        price = price.trim();
        
        try {
            if (connection.isClosed()) {
                LOG.info("Connect Again");
                connection = DriverManager.getConnection(Constants.JDBC, userInfo);
            }
            statement = connection.prepareStatement("UPDATE PRODUCTS SET " + "PRODUCT_NAME = '" + name
                    + "', PRODUCT_CATEGORY = '" + category + "', PRODUCT_DESC = '" + desc + "', PRODUCT_PRICE = " + price
                    + " WHERE PRODUCT_ID = " + id);
            LOG.info("" + statement.toString());
            statement.executeUpdate();     
            LOG.info("Finish Update");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
            close(connection);
        }
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

    @Override
    public void addProduct(String name, String category, String desc, String price) {
        name = name.trim();
        category = category.trim();
        desc = desc.trim();
        price = price.trim();
        
        String values = "'" + name + "','" + category + "','" + desc + "'," + price;
        try {
            if (connection.isClosed()) {
                LOG.info("Connect Again");
                connection = DriverManager.getConnection(Constants.JDBC, userInfo);
            }
            statement = connection.prepareStatement("INSERT INTO PRODUCTS "
                    + "(PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_DESC,PRODUCT_PRICE) " + "VALUES(" + values + ")");
            statement.executeUpdate();
            LOG.info("Adding completed");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            close(result);
            close(statement);
            close(connection);
        }

    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
    
}
