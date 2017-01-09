package store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DBConnection {
	CONNECTION;
	
	Connection con;
	DBConnection(){
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			String url = "jdbc:sqlserver://gditsn033\\SqlProd";
			Properties prop = new Properties();
			prop.setProperty("databaseName", "ProdigiousContainer");
			prop.setProperty("user", "sa");
			prop.setProperty("password", "sgrh@2016");
			con = DriverManager.getConnection(url,prop);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		return con;
	}
}
