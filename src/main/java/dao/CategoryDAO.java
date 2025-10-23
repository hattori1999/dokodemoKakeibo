package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Category;

public class CategoryDAO {

    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();

        // ✅ DBManagerを使って接続を取得
        String sql = "SELECT categories_id, categories_name FROM categories";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category cat = new Category();
                cat.setCategoryId(rs.getInt("categories_id"));
                cat.setCategoryName(rs.getString("categories_name"));
                list.add(cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
