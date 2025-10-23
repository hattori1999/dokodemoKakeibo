package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.EntryLogic;
import model.Entry;

@WebServlet("/EntrySearchServlet")
public class EntrySearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EntrySearchServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 記帳IDをリクエストから取得
        String entryIdStr = request.getParameter("entryId");
        int entryId = 0;

        try {
            entryId = Integer.parseInt(entryIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "記帳IDが不正です");
            // 同じ JSP に戻す
            request.getRequestDispatcher("WEB-INF/jsp/entryDelete.jsp").forward(request, response);
            return;
        }


        // EntryLogicで検索
        EntryLogic logic = new EntryLogic();
        Entry entry = logic.getEntryById(entryId);

        // 検索結果をリクエストにセット
        request.setAttribute("entry", entry);

        // JSPにフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/entryDelete.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POSTでもGETの処理を呼ぶ
        doGet(request, response);
    }
}
