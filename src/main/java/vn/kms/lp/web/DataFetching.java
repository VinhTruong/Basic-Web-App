package vn.kms.lp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vn.kms.lp.dao.impl.ProductFactoryImpl;
import vn.kms.lp.model.ProductModel;

/**
 * Servlet implementation class DataControl
 */
@WebServlet("/DataFetching")
public class DataFetching extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductFactoryImpl productFactory;
	private List<ProductModel> products;
    Logger logger = LogManager.getLogger(DataFetching.class);   
    /**
     * @throws SQLException 
     * @see HttpServlet#HttpServlet()
     */
    public DataFetching() throws SQLException {
        super();
        productFactory = new ProductFactoryImpl();
        products = new ArrayList<ProductModel>();
        logger.info("Initialized DataFetching Servlet");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filterName = request.getParameter("Name");
		String filterCategory = request.getParameter("Category");
		String fromPrice = request.getParameter("From");
		String toPrice = request.getParameter("To");
		String Order = request.getParameter("Order");
		
	    if (!filterName.equals("")
	            && fromPrice.equals("")
	    		&& filterCategory.equals("")) {
	    	  products = productFactory.findByName(filterName);
	    } else if (!filterCategory.equals("")
	                    && fromPrice.equals("")
	    				&& filterName.equals("")) {
	    	  products = productFactory.findByCategory(filterCategory);
	    } else if (!fromPrice.equals("")
	                    && !toPrice.equals("")
                        && filterName.equals("")
                        && filterCategory.equals("")) {
	          int fromValue = Integer.parseInt(fromPrice);
	          int toValue = Integer.parseInt(toPrice);
	          products = productFactory.findByPrice(fromValue, toValue);
	    } else if (!filterName.equals("") 
                        && filterCategory.equals("")
                        && !fromPrice.equals("")
                        && !toPrice.equals("")) {
	          products = productFactory.findByName(filterName);
	          removeWrongPrice(fromPrice, toPrice);
	    } else if (filterName.equals("") 
                        && !filterCategory.equals("")
                        && !fromPrice.equals("")
                        && !toPrice.equals("")) {
	        products = productFactory.findByCategory(filterCategory);
            removeWrongPrice(fromPrice, toPrice);
	    }
	    else if (!filterName.equals("") 
	    				&& !filterCategory.equals("")
	    				&& fromPrice.equals("")) {
	    	  products = productFactory.findByName(filterName);
	    	  removeWrongCategory(filterCategory);
	    }
	    
	   logger.info("Prepare To sort");
	   switch (Order) {
	        case "name":
	            Collections.sort(products);
	            logger.info("Sorted");
	            break;
	        case "price":
	            Collections.sort(products, new ProductModel());
	            break;
	    }
	    	
	   request.setAttribute("products", products);
	   request.getRequestDispatcher("search.jsp").forward(request, response);
	    
	}

	protected void removeWrongCategory(String rightCategory) {
	    for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).getCategory().equals(rightCategory)) {
                  products.remove(products.get(i));
                  i--;
            }
      }
	}
	
	protected void removeWrongPrice(String fromRightPrice, String toRightPrice) {
	    int fromValue = Integer.parseInt(fromRightPrice);
        int toValue = Integer.parseInt(toRightPrice);
	    for (int i = 0; i < products.size(); i++) {
            int price = products.get(i).getPrice();
            if (price < fromValue
                    || price > toValue) {
                  products.remove(products.get(i));
                  i--;
            }
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
