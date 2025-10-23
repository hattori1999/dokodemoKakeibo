package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Entry;

public class EntryDAO {
	
    public void saveEntryData(Entry entry) throws SQLException {

        String sql = "INSERT INTO entries(user_id, categories_id, entries_name, price, date, credit_flag, purpose_flag) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entry.getUserId());
            stmt.setInt(2, entry.getCategoryId());
            stmt.setString(3, entry.getName());
            stmt.setInt(4, entry.getPrice());
            stmt.setDate(5, java.sql.Date.valueOf(entry.getDate()));
            stmt.setBoolean(6, entry.isCreditFlag());
            stmt.setBoolean(7, entry.isPurposeFlag());

            stmt.executeUpdate(); // ← ここが正しい！

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 呼び出し元にも通知
        }
    }
    
    public boolean deleteById(int entryId) {
        String sql = "DELETE FROM entries WHERE entries_id = ?";
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, entryId);
            int result = pStmt.executeUpdate();
            return result > 0; // 1件以上削除できたら true
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public Entry findById(int entryId) {
        Entry entry = null; // 見つからなければ null を返す
        
        String sql = "SELECT user_id, date, entries_name, categories_id, credit_flag, purpose_flag, price " +
                     "FROM entries " +
                     "WHERE entries_id = ?"; // 主キーのカラム名
        
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            
            pStmt.setInt(1, entryId);
            
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) { // 1件だけ取得
                    entry = new Entry();
                    entry.setUserId(rs.getInt("user_id"));
                    entry.setDate(rs.getDate("date").toLocalDate()); // DATE型をLocalDateに変換
                    entry.setName(rs.getString("entries_name"));
                    entry.setCategoryId(rs.getInt("categories_id"));
                    entry.setCreditFlag(rs.getBoolean("credit_flag"));
                    entry.setPurposeFlag(rs.getBoolean("purpose_flag"));
                    entry.setPrice(rs.getInt("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entry;
    }
}
