package vn.kms.lp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.kms.lp.dao.ProductFactory;
import vn.kms.lp.model.ProductModel;
import vn.kms.lp.web.utils.Constants;

public class ProductFactoryImpl implements ProductFactory {

    private ProductModel product;
    private Map<Integer, ProductModel> products;
    
    public Map<Integer, ProductModel> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, ProductModel> products) {
        this.products = products;
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProductFactoryImpl.class);
    private static ProductFactory instance;


    public ProductFactoryImpl() throws SQLException {                  
            products = new HashMap<Integer, ProductModel>();
            product = null;            
            fetchData();
    }

    @Override
    public List<ProductModel> findByName(String filterName) {
        return products.values()
                .stream()
                .filter(productModel -> filterName.equals(productModel.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByCategory(String category) {
        return products.values()
                .stream()
                .filter(productModel -> category.equals(productModel.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByPrice(int fromPrice, int toPrice) {
        int price = 0;
        List<ProductModel> searchResult = new ArrayList<ProductModel>();
        for (int i=1 ; i<products.size(); i++) {
              price = products.get(i).getPrice();
              if (price <= toPrice && price >= fromPrice) {
                    searchResult.add(products.get(i));
              }
        }
        return searchResult;
    }

    public void fetchData() {
        products.clear();       // exclude problem when add or delete into db
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
                statement = connection.prepareStatement("SELECT * FROM PRODUCTS");
                result = statement.executeQuery();
                while (result.next()) {
                    product = new ProductModel(result.getInt(1),
                                                  result.getString(2),
                    		                   	  result.getString(3),
                    		                   	  result.getString(4),
                    		                   	  result.getInt(5));
                    products.put(new Integer(products.size()+1), product);
                    
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
            //Ignore all
        }
    }   
}
