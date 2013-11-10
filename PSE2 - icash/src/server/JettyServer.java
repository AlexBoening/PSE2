package server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import classes.*;
import client.*;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import java.sql.*;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(9998);

		// JERSEY
		ResourceConfig rc = new PackagesResourceConfig("server");
		ServletContextHandler sh = new ServletContextHandler();
		sh.setContextPath("/rest");
		sh.addServlet(new ServletHolder(new ServletContainer(rc)), "/*");

		// JSP
		WebAppContext webApp = new WebAppContext();
		webApp.setContextPath("/jsp");
		webApp.setResourceBase("jsp");
		webApp.addServlet(AdminServlet.class, "/admin");

		HandlerCollection handlerCollection = new HandlerCollection();
		handlerCollection.setHandlers(new Handler[] { sh, webApp });
        
		server.setHandler(handlerCollection);
		server.start();
		try {
			SQL.getConnection();  
			TestClient tc = new TestClient();
			Account a = tc.getAccount(1);
		}
		catch (SQLException e) {
			System.out.println("Database connection failed!");
		}
	}
}
