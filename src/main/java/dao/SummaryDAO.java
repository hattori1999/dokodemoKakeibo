package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Summary;

public class SummaryDAO {

    public List<Summary> getSummary(Integer userId, LocalDate startDate, LocalDate endDate,
                                    Integer categoryId, Integer creditFlag, Integer purposeFlag) {

        List<Summary> summaryList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DATE(date) AS entry_date, ");
        sql.append("GROUP_CONCAT(entries_id) AS entry_ids, ");
        sql.append("user_id, categories_id, credit_flag, purpose_flag, SUM(price) AS total ");
        sql.append("FROM entries WHERE 1=1 ");

        if (userId != null) sql.append("AND user_id = ? ");
        if (startDate != null && endDate != null) sql.append("AND date BETWEEN ? AND ? ");
        if (categoryId != null) sql.append("AND categories_id = ? ");
        if (creditFlag != null) sql.append("AND credit_flag = ? ");
        if (purposeFlag != null) sql.append("AND purpose_flag = ? ");

        sql.append("GROUP BY DATE(date), user_id, categories_id, credit_flag, purpose_flag ");
        sql.append("ORDER BY DATE(date), user_id, categories_id");

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (userId != null) stmt.setInt(index++, userId);
            if (startDate != null && endDate != null) {
                stmt.setDate(index++, java.sql.Date.valueOf(startDate));
                stmt.setDate(index++, java.sql.Date.valueOf(endDate));
            }
            if (categoryId != null) stmt.setInt(index++, categoryId);
            if (creditFlag != null) stmt.setInt(index++, creditFlag);
            if (purposeFlag != null) stmt.setInt(index++, purposeFlag);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Summary s = new Summary();
                s.setDate(rs.getDate("entry_date").toLocalDate());
                s.setEntryIds(rs.getString("entry_ids"));
                s.setUserId(rs.getInt("user_id"));
                s.setCategoryId(rs.getInt("categories_id"));
                s.setCreditFlag(rs.getInt("credit_flag"));
                s.setPurposeFlag(rs.getInt("purpose_flag"));
                s.setTotal(rs.getInt("total"));
                summaryList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summaryList;
    }
}
