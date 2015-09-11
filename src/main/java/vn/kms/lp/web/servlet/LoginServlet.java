package vn.kms.lp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vn.kms.lp.dao.UserDao;
import vn.kms.lp.dao.impl.UserDaoImpl;

/**
 * Servlet implementation class LoginControl
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String userName = request.getParameter("UserName");
        String passWord = request.getParameter("Password");
        UserDao userFactory = new UserDaoImpl();
        boolean loginFlag = userFactory.checkUser(userName, passWord);
        if (loginFlag == false) {
            request.setAttribute("loginFlag", loginFlag);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            request.getRequestDispatcher("Authorized/searchWithAuthorized.jsp").forward(request, response);
        }

    }

}
