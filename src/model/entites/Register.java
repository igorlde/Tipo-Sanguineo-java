package model.entites;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import model.db.Factory.DatabaseFACTORY;
import model.exception.DBException;
import model.service.ServiceCad;

public class Register implements ServiceCad {

	@Override
	public void registerPeople(String name, String gender, Integer age, String address, String bloodType) {
		String sql = "INSERT INTO people (name, gender, age, address, blood_type) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseFACTORY.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name);
			stmt.setString(2, gender);
			stmt.setInt(3, age);
			stmt.setString(4, address);
			stmt.setString(5, bloodType);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Integer> countBloodType() {
		 Map<String, Integer> countType = new HashMap<>();
		    String sql = "SELECT blood_type, COUNT(*) AS count FROM people GROUP BY blood_type";

		    try (Connection conn = DatabaseFACTORY.getConnection();
		         PreparedStatement st = conn.prepareStatement(sql);
		         ResultSet rs = st.executeQuery()) {

		        while (rs.next()) {
		            countType.put(rs.getString("blood_type"), rs.getInt("count"));
		        }

		    } catch (SQLException e) {
		        throw new DBException("Erro ao contar tipos sanguíneos: " + e.getMessage());
		    }

		    return countType;
	}

	@Override
	public String bloodTypeInLack() {
		Map<String, Integer> countType = countBloodType();
		String typeManyLack = null;
		int minCount = Integer.MAX_VALUE;

		for (Map.Entry<String, Integer> entry : countType.entrySet()) {
			if (entry.getValue() < minCount) {
				minCount = entry.getValue();
				typeManyLack = entry.getKey();
			}
		}
		return typeManyLack;
	}

	@Override
	public void listbloodTypeAvailable() {

		Map<String, Integer> countType = countBloodType();

		System.out.println("Tipos Sanguíneos Disponíveis e suas Quantidades:");
		for (Map.Entry<String, Integer> entry : countType.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " doadores");
		}
	}

}
