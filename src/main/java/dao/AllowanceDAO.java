package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Allowance;

public class AllowanceDAO {
	public int findByUserId(int userId) {
		String sql = "SELECT money FROM Allowance WHERE user_id = ?";
		int amount = 0;
		
		try(Connection conn = DBManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				amount = rs.getInt("money");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			
		}return amount;
	}
	
	// お小遣いを更新する
	public void update(Allowance allowance) {
	    String sql = "UPDATE allowance SET money = ? WHERE user_id = ?";
	    
	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, allowance.getAmount());
	        stmt.setInt(2, allowance.getUserId());
	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
