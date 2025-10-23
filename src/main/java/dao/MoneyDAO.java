package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyDAO {
	
	//DBからお小遣いの金額を取得
	public int getAllowance(int userId) {
		int allowance = 0;
		
		String sql = "SELECT money FROM allowance WHERE user_id = ?";
		
		try (Connection conn = DBManager.getConnection();
	    PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				allowance = rs.getInt("money");
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
		}
		
		return allowance;
	}


	public int getRemainingAllowance(int userId) {
        int allowance = getAllowance(userId);
		int spent = 0;

        try (Connection conn = DBManager.getConnection()) {
            String sqlSpent = "SELECT SUM(price) AS spent " +
                              "FROM entries " +
                              "WHERE user_id = ? " +
                              "AND purpose_flag = 1 " +
                              "AND MONTH(date) = MONTH(CURRENT_DATE()) " +
                              "AND YEAR(date) = YEAR(CURRENT_DATE())";

            try (PreparedStatement ps = conn.prepareStatement(sqlSpent)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    spent = rs.getInt("spent");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allowance - spent;
    }
}