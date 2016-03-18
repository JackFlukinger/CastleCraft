package net.castlecraftmc.sql;

 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class MySQL extends Database {
	static String user = "";
	static String database = "";
	static String password = "";
	static String port = "";
	static String hostname = "";
	static Connection c = null;

	@SuppressWarnings("static-access")
	public MySQL(String hostname, String portnmbr, String database, String username, String password) {
		this.hostname = hostname;
		this.port = portnmbr;
		this.database = database;
		this.user = username;
		this.password = password;
	}

	public Connection open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, user, password);
			return c;
		}
		catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		}
		catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
		return c;
	}

	@SuppressWarnings("static-access")
	public boolean checkConnection() {
		if (this.c != null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-access")
	public Connection getConn() {
		return this.c;
	}

	public static Connection closeConnection(Connection c) {
		return c = null;
	}

	public ResultSet querySQL(String query) {
		Connection c = null;
		if (checkConnection()) {
			c = getConn();
		} else {
			c = open();
		}
		Statement s = null;
		try {
			s = c.createStatement();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		ResultSet ret = null;
		try {
			ret = s.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		closeConnection(c);

		return ret;
	}

	public void updateSQL(String update) {
		Connection c = null;
		if (checkConnection()) {
			c = getConn();
		} else {
			c = open();
		}
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate(update);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		closeConnection(c);
	}
}
