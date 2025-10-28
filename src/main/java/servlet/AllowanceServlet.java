package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.AllowanceDAO;
import model.Allowance;

@WebServlet("/allowance")
public class AllowanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AllowanceServlet() {
        super();
    }

    // GET: お小遣い画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションから userId を取得
        int userId = (int) request.getSession().getAttribute("userId");

        // DBから最新のお小遣いを取得
        AllowanceDAO dao = new AllowanceDAO();
        int currentAmount = dao.findByUserId(userId);

        // JSPに渡す
        request.setAttribute("currentAmount", currentAmount);
        request.getRequestDispatcher("/WEB-INF/jsp/allowanceSettings.jsp").forward(request, response);
    }

    // POST: お小遣い更新
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        int amount = Integer.parseInt(request.getParameter("amount"));

        // 更新
        AllowanceDAO dao = new AllowanceDAO();
        dao.update(new Allowance(userId, amount));

        // 更新後、最新値を再取得してJSPに表示
        int currentAmount = dao.findByUserId(userId);
        request.setAttribute("currentAmount", currentAmount);
        request.getRequestDispatcher("/WEB-INF/jsp/allowanceSettings.jsp").forward(request, response);
    }
}
