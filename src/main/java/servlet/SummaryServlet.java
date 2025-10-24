package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import dao.SummaryDAO;
import dao.UserDAO;
import model.Category;
import model.Summary;
import model.User;

@WebServlet("/SummaryServlet")
public class SummaryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.findAll();
        request.setAttribute("categoryList", categoryList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/summary.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String userIdStr = request.getParameter("userId");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String categoryIdStr = request.getParameter("categoryId");
        String creditFlagStr = request.getParameter("creditFlag");
        String purposeFlagStr = request.getParameter("purposeFlag");

        Integer userId = parseIntOrNull(userIdStr);
        Integer categoryId = parseIntOrNull(categoryIdStr);
        Integer creditFlag = parseIntOrNull(creditFlagStr);
        Integer purposeFlag = parseIntOrNull(purposeFlagStr);

        LocalDate startDate = parseDateOrNull(startDateStr);
        LocalDate endDate = parseDateOrNull(endDateStr);

        SummaryDAO summaryDAO = new SummaryDAO();
        List<Summary> summaryList = summaryDAO.getSummary(userId, startDate, endDate, categoryId, creditFlag, purposeFlag);

        Map<Integer, String> userNameMap = new HashMap<>();
        UserDAO userDAO = new UserDAO();
        for (Summary s : summaryList) {
            int uid = s.getUserId();
            if (!userNameMap.containsKey(uid)) {
                User user = userDAO.findById(uid);
                if (user != null) userNameMap.put(uid, user.getUserName());
                else userNameMap.put(uid, "不明");
            }
        }

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.findAll();

        request.setAttribute("summaryList", summaryList);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("userNameMap", userNameMap);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/summary.jsp");
        dispatcher.forward(request, response);
    }

    private Integer parseIntOrNull(String value) {
        try { return (value == null || value.isEmpty()) ? null : Integer.parseInt(value); }
        catch (NumberFormatException e) { return null; }
    }

    private LocalDate parseDateOrNull(String value) {
        try { return (value == null || value.isEmpty()) ? null : LocalDate.parse(value); }
        catch (DateTimeParseException e) { return null; }
    }
}
