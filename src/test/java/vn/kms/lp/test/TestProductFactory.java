package vn.kms.lp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import vn.kms.lp.dao.impl.ProductFactoryImpl;
import vn.kms.lp.model.ProductModel;
import vn.kms.lp.web.utils.Constants;

public class TestProductFactory {

	  private Properties userInfo;
	    private Connection connection;
	    private Statement stmt;
	    private ResultSet result;
	    private ProductModel product;
	    private Map<Integer, ProductModel> products;
	
	@Test
	public void productFact() throws SQLException {
       ProductFactoryImpl p = new ProductFactoryImpl();
       List<ProductModel> products = new ArrayList<ProductModel>();
       products = p.findByName("postgres");
       //products.get(0).getCategory();
	}
}
