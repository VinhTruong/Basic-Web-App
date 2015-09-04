package vn.kms.lp.web.servlet;

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

import vn.kms.lp.dao.impl.ProductDaoImpl;
import vn.kms.lp.model.ProductModel;

/**
 * Servlet implementation class DataControl
 */
@WebServlet("/DataFetching")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final Logger logger = LogManager.getLogger(SearchServlet.class);

    /**
     * @throws SQLException
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() throws SQLException {

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean gotData = false;
        String filterName = request.getParameter("Name");
        String filterCategory = request.getParameter("Category");
        String fromPrice = request.getParameter("From");
        String toPrice = request.getParameter("To");
        String Order = request.getParameter("Order");

        try {
            ProductDaoImpl productDao = new ProductDaoImpl();
            List<ProductModel> products = new ArrayList<ProductModel>();
            
            if (!filterName.equals("") && gotData==false) {
                products = productDao.findByName(filterName);
                gotData = true;
            }
            
            if (!filterCategory.equals("")) {
                if (gotData==false) {
                    products = productDao.findByCategory(filterCategory);
                    gotData = true;
                } else {
                    products = productDao.findByCategory(products, filterCategory);
                }
                
            } 
            
            if (!fromPrice.equals("")) {
                if (gotData==false) {
                    products = productDao.findByPrice(Integer.parseInt(fromPrice),Integer.parseInt(toPrice));
                    gotData = true;
                } else {
                    products = productDao.findByPrice(products, Integer.parseInt(fromPrice), Integer.parseInt(toPrice));
                }
                
            }
            
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
            return;
        } catch (ServletException | IOException e) {
            logger.error(e);
        } catch (SQLException e) {
            logger.error(e);
        }
        
        //response a error page 
        // ...
        // ...
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
    }

}
