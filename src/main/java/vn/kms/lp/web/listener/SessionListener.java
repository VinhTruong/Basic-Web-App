package vn.kms.lp.web.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Servlet implementation class LoginListener
 */
@WebServlet("/LoginListener")
public class SessionListener extends HttpServlet implements HttpSessionAttributeListener {
    private static final long serialVersionUID = 1L;
    private static int onlineCounting;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionListener() {
        super();
        // TODO Auto-generated constructor stub
        onlineCounting = 0;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        request.setAttribute("onlineCounting", onlineCounting);
        request.getRequestDispatcher(request.getAttribute("currenPage").toString()).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
        if (event.getSession().getAttribute("userName") != null) {
            onlineCounting++;
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
        if (event.getSession().getAttribute("userName") == null) {
            onlineCounting--;
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub

    }
}
