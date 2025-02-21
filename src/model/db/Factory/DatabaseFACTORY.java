package model.db.Factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import model.exception.DBException;

public class DatabaseFACTORY {

	public static Connection getConnection() {
		try {
			Properties prop = loadProperties();
			String url = prop.getProperty("dburl");
			return DriverManager.getConnection(url, prop);
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} 
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closePreparedStatement(PreparedStatement st) {
		if (st != null) {
			try {
				st.close();
			} 
			catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (InputStream input = DatabaseFACTORY.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				throw new DBException("Arquivo db.properties não encontrado!");
			}
			Properties props = new Properties();
			props.load(input);
			return props;
		}
		catch (IOException e) {
			throw new DBException("Erro ao carregar propriedades do banco de dados: " + e.getMessage());
		}
	}
}
