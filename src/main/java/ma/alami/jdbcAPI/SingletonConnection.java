package ma.alami.jdbcAPI;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnection {
	
	private static Connection connection;
	
	private SingletonConnection() {}

	
	public static synchronized Connection getConnection(String database) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,"root","");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
