package vn.kms.lp.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vn.kms.lp.dao.impl.ProductFactoryImpl;

/**
 * Servlet implementation class UpdateDatabase
 */
@WebServlet("/UpdateDatabase")
public class UpdateDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UpdateDatabase.class);
	private static ProductFactoryImpl productFactory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDatabase() {
        super();
        try {
            productFactory = new ProductFactoryImpl();
        } catch (SQLException e) {
            logger.error("Problem when initialize productFactory");
        }
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    String updateId = request.getParameter("Id");
	    String updateName = request.getParameter("Name");
		String updateCategory = request.getParameter("Category"); 
		String updateDesc = request.getParameter("Desc");
		String updatePrice = request.getParameter("Price");

		if(!updateId.equals("null")) {
		    productFactory.updateProduct(updateId, updateName, updateCategory,
		                                    updateDesc, updatePrice);
		} else {
		    productFactory.addProduct(updateName, updateCategory, updateDesc, updatePrice);
		}
		
		request.getRequestDispatcher("search.jsp").forward(request, response);;
		
	}

}
