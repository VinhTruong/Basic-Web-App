package vn.kms.lp.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class LoginListeners
 *
 */
@WebListener
public class LoginListeners implements HttpSessionAttributeListener {

    public static int onlineCounting;

    /**
     * Default constructor.
     */
    public LoginListeners() {
        // TODO Auto-generated constructor stub
        onlineCounting = 0;
    }

    /**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getSession().getAttribute("userName") != null) {
            onlineCounting++;
        }
    }

    /**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getSession().getAttribute("userName") == null) {
            onlineCounting--;
        }
    }

    /**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
    }

}
