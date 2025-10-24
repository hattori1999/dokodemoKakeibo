package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

    // 既存メソッド（パスワードあり）
    public User findById(int userId, String password) {
        String sql = "SELECT * FROM dokodemokakeibo.user WHERE user_id = ? AND password = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, userId);
            pStmt.setString(2, password);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String pass = rs.getString("password");
                return new User(id ,name ,pass);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 追加メソッド：ユーザーIDだけで取得
    public User findById(int userId) {
        String sql = "SELECT * FROM dokodemokakeibo.user WHERE user_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, userId);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String pass = rs.getString("password"); // 必要に応じて
                return new User(id, name, pass);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
