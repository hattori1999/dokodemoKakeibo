package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CategoryDAO;
import dao.EntryDAO;
import model.Category;
import model.Entry;

@WebServlet("/EntryServlet")
public class EntryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // カテゴリーリストを取得してJSPにセット
        CategoryDAO categoryDao = new CategoryDAO();
        List<Category> categoryList = categoryDao.findAll();
        request.setAttribute("categoryList", categoryList);

        request.getRequestDispatcher("/WEB-INF/jsp/entry.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // --- カテゴリーリストを毎回セット（フォーム再表示用） ---
        CategoryDAO categoryDao = new CategoryDAO();
        List<Category> categoryList = categoryDao.findAll();
        request.setAttribute("categoryList", categoryList);

        try {
            // --- 入力取得 ---
            String dateStr = request.getParameter("entryDate");
            String name = request.getParameter("entryName");
            String categoryStr = request.getParameter("categoryId");
            String amountStr = request.getParameter("amount");
            String paymentMethod = request.getParameter("paymentMethod");
            String purposeParam = request.getParameter("purpose");

            // --- 入力チェック ---
            StringBuilder errorMessage = new StringBuilder();

            // 日付
            LocalDate date = null;
            if (dateStr != null && !dateStr.isEmpty()) {
                try {
                    date = LocalDate.parse(dateStr);
                } catch (Exception e) {
                    errorMessage.append("日付の形式が不正です。<br>");
                }
            } else {
                errorMessage.append("日付を入力してください。<br>");
            }

            // 支出内容
            if (name == null || name.trim().isEmpty()) {
                errorMessage.append("支出内容を入力してください。<br>");
            }

            // カテゴリー
            int categoryId = 0;
            if (categoryStr != null && !categoryStr.isEmpty()) {
                try {
                    categoryId = Integer.parseInt(categoryStr);
                } catch (NumberFormatException e) {
                    errorMessage.append("カテゴリーが不正です。<br>");
                }
            } else {
                errorMessage.append("カテゴリーを選択してください。<br>");
            }

            // 金額
            int price = 0;
            if (amountStr != null && !amountStr.isEmpty()) {
                try {
                    price = Integer.parseInt(amountStr);
                    if (price <= 0) {
                        errorMessage.append("金額は0より大きく入力してください。<br>");
                    }
                } catch (NumberFormatException e) {
                    errorMessage.append("金額は数値で入力してください。<br>");
                }
            } else {
                errorMessage.append("金額を入力してください。<br>");
            }

            // 決済方法
            boolean creditFlag = "credit".equals(paymentMethod);
            if (paymentMethod == null || paymentMethod.isEmpty()) {
                errorMessage.append("決済方法を選択してください。<br>");
            }

            // 目的
            boolean purposeFlag = "allowance".equals(purposeParam);
            if (purposeParam == null || purposeParam.isEmpty()) {
                errorMessage.append("目的を選択してください。<br>");
            }

            // --- エラーがあればフォームに戻す ---
            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage.toString());
                request.getRequestDispatcher("/WEB-INF/jsp/entry.jsp").forward(request, response);
                return;
            }

            // --- セッションからユーザーID取得 ---
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                request.setAttribute("errorMessage", "ログイン情報がありません。再度ログインしてください。");
                request.getRequestDispatcher("/WEB-INF/jsp/entry.jsp").forward(request, response);
                return;
            }

            // --- DAOに保存 ---
            Entry entry = new Entry(userId, categoryId, name, price, date, creditFlag, purposeFlag);
            EntryDAO dao = new EntryDAO();
            dao.saveEntryData(entry);

            request.setAttribute("successMessage", "登録が完了しました！");
            request.getRequestDispatcher("/WEB-INF/jsp/entry.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データの登録に失敗しました。");
            request.getRequestDispatcher("/WEB-INF/jsp/entry.jsp").forward(request, response);
        }
    }
}
