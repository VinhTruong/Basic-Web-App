package vn.kms.lp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private Properties userInfo;
    private Connection connection;
    private Statement stmt;
    private ResultSet result;
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
            connection = null;
            stmt = null;
            result = null;
            product = null;
            
            InitConnection();
            fetchData();
    }
    
    public void InitConnection() {
        userInfo.put("user", "postgres");
        userInfo.put("password", "124356");
        
        try {
            // Check necessary driver
            Class.forName(Constants.POSTGRES_DRIVER);
            
            connection = DriverManager.getConnection(Constants.JDBC, userInfo);
        } catch (ClassNotFoundException e) {
            LOG.error("Missing necessary Driver: " + e.getMessage());
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
   
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
        // TODO Auto-generated method stub
        return products.values()
                .stream()
                .filter(productModel -> category.equals(productModel.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductModel> findByPrice(int From, int To) {
        // TODO Auto-generated method stub
        return null;
    }

    public void fetchData() {
        products.clear();       // exclude problem when add or delete into db
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT product_name FROM products;");
            do {
                product = new ProductModel(result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("product_category"),
                        result.getString("product_desc"),
                        result.getInt("product_price"));
                products.put(new Integer(products.size()+1), product);              
            } while (result.next());
            
        } catch (SQLException e) {
            // TODO: handle exception
        }
        
    }
    
    public synchronized final ProductFactory getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductFactoryImpl();
        }
        return instance;
    }

}
