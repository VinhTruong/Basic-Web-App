//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.lp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import vn.kms.lp.web.utils.Constants;

/**
 * @author thanhtran
 *
 */
@WebFilter(urlPatterns = "/forbidden")
public class TestFilter implements Filter {
    private static final String SECRET = "LaunchProgram";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        boolean ok = SECRET.equals(request.getParameter("secret"));
        if (ok) {
            chain.doFilter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println(Constants.HTML_START + "<h2>Rejected !!!" + Constants.HTML_END);
        }
    }

    @Override
    public void destroy() {
    }

}
