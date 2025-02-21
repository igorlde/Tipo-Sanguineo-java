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
	public void registerPeople(String name, String gender, Integer age, String address, String bloodType,
			String phoneNumber) {
		String sql = "INSERT INTO people (name, gender, age, address, blood_type, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseFACTORY.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, name);
			stmt.setString(2, gender);
			stmt.setInt(3, age);
			stmt.setString(4, address);
			stmt.setString(5, bloodType);
			stmt.setString(6, phoneNumber);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			if (e.getMessage().contains("Duplicate entry")) {
				throw new DBException("Erro: O nome '" + name + "' já está cadastrado.");
			}
			throw new DBException("Erro ao cadastrar doador: " + e.getMessage());
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

		}
		catch (SQLException e) {
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

	@Override
	public void listDonors() {
		String sql = "SELECT * FROM people";
		try (Connection conn = DatabaseFACTORY.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			System.out.println("Lista de doadores:");
			while (rs.next()) {
				System.out.println("Nome: " + rs.getString("name") + ", Tipo saguineo: " + rs.getString("blood_type")
						+ ", Telefone: " + rs.getString("phone_number"));

			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}

	public void updateDonor(Integer age, String address, String bloodType, String phoneNumber, Integer id) {
		String sql = "UPDATE people SET age = ?, address = ?, blood_type = ?, phone_number = ? WHERE id = ?";
		try (Connection conn = DatabaseFACTORY.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, age);
			stmt.setString(2, address);
			stmt.setString(3, bloodType);
			stmt.setString(4, phoneNumber);
			stmt.setInt(5, id);

			stmt.executeUpdate();
		} 
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public void deletePerson(String name, String address, String phoneNumber) {
		 String sql;
		    boolean hasPhoneNumber = (phoneNumber != null && !phoneNumber.isEmpty());

		    if (hasPhoneNumber) {
		        sql = "DELETE FROM people WHERE name = ? AND address = ? AND phone_number = ?";
		    } else {
		        sql = "DELETE FROM people WHERE name = ? AND address = ? AND phone_number IS NULL";
		    }
		   
			 try (Connection conn = DatabaseFACTORY.getConnection();
			         PreparedStatement stmt = conn.prepareStatement(sql)) {

			        stmt.setString(1, name);
			        stmt.setString(2, address);

			        if (hasPhoneNumber) {
			            stmt.setString(3, phoneNumber);
			        }

			        int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0){
				System.out.println("Pessoa removida com sucesso");
			}
			else {
				System.out.println("Erro na remoção verifique as informações");
			}
		}
		catch(SQLException e) {
			throw new DBException("Erro ao deletar: " + e.getMessage());
		}
	}
	 public String getDonorsList() {
         StringBuilder sb = new StringBuilder();
         String sql = "SELECT name, blood_type, phone_number FROM people";

         try (Connection conn = DatabaseFACTORY.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql);
              ResultSet rs = stmt.executeQuery()) {

             while (rs.next()) {
                 sb.append("Nome: ").append(rs.getString("name"))
                   .append(", Tipo sanguíneo: ").append(rs.getString("blood_type"))
                   .append(", Telefone: ").append(rs.getString("phone_number"))
                   .append("\n");
             }
         } catch (SQLException e) {
             throw new DBException("Erro ao listar doadores: " + e.getMessage());
         }

         return sb.toString();
     }
}
