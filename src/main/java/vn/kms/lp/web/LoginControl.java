package vn.kms.lp.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.kms.lp.dao.UserFactory;
import vn.kms.lp.dao.impl.UserFactoryImpl;

/**
 * Servlet implementation class LoginControl
 */
@WebServlet("/LoginControl")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("UserName");
		String passWord = request.getParameter("Password");
		UserFactory userFactory = new UserFactoryImpl();
		boolean loginFlag = userFactory.CheckUser(userName, passWord);
		if (loginFlag==false) {
			request.setAttribute("loginFlag", loginFlag);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
		    HttpSession session = request.getSession();
		    session.setAttribute("userName", userName);
			request.getRequestDispatcher("search.jsp").forward(request, response);
		}
	}

}
