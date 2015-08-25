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

        String id = request.getParameter("Id");
        String name = request.getParameter("Name");
        String category = request.getParameter("Category");
        String desc = request.getParameter("Desc");
        String price = request.getParameter("Price");
        String action = request.getParameter("Action");

        switch (action) {
            case "Save":
                if (id != null) {
                    productFactory.updateProduct(id, name, category, desc, price);
                } else {
                    productFactory.addProduct(name, category, desc, price);
                }
                break;
            case "Delete":
                productFactory.deleteProduct(id);
                break;
        }

        request.getRequestDispatcher("search.jsp").forward(request, response);

    }

}
