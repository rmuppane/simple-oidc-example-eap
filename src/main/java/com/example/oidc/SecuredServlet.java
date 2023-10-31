package com.example.oidc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wildfly.security.http.oidc.AccessToken;
import org.wildfly.security.http.oidc.OidcSecurityContext;

/**
 * A simple secured HTTP servlet.
 *
 */
@WebServlet("/secured")
public class SecuredServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<html>");
            writer.println("  <head><title>Secured Servlet</title></head>");
            writer.println("  <body>");
            writer.println("    <h1>Secured Servlet</h1>");
            writer.println("    <p>");
            writer.print(" Current Principal '");
            Principal user = req.getUserPrincipal();
            writer.print(user != null ? user.getName() : "NO AUTHENTICATED USER");
            if(user != null) {
            	System.out.println("type = " + user.getClass());
            }
            
            OidcSecurityContext securityContext = (OidcSecurityContext) req.getAttribute(OidcSecurityContext.class.getName()); 
            if(securityContext != null) {
	            String token = securityContext.getIDTokenString();
	            System.out.println("auth token is = " + token);
	            
	            AccessToken accessToken = securityContext.getToken();
	            ArrayList<String> roles = accessToken.getRealmAccessClaim().getRoles();
	            System.out.println("roles are = " + roles);
            }
            //GenericPrincipal genericPrincipal = (GenericPrincipal) user;
            // final String[] roles = genericPrincipal.getRoles();
            writer.print("'");
            writer.println("    </p>");
            writer.println("  </body>");
            writer.println("</html>");
        }
    }

}