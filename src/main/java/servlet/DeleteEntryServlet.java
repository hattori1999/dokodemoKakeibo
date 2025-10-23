package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.EntryDeleteLogic;

/**
 * Servlet implementation class DeleteEntryServlet
 */
@WebServlet("/DeleteEntryServlet")
public class DeleteEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEntryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("entryId");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int entryId = Integer.parseInt(idStr);
                EntryDeleteLogic logic = new EntryDeleteLogic();
                boolean success = logic.execute(entryId);

                if (success) {
                    request.getSession().setAttribute("message", "削除しました");
                } else {
                    request.getSession().setAttribute("message", "削除に失敗しました");
                }

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("message", "不正なIDです");
            }
        } else {
            request.getSession().setAttribute("message", "IDを入力してください");
        }

        // 削除後は検索画面(entryDelete.jsp)を表示するサーブレットへ戻す
        response.sendRedirect("EntrySearchServlet");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
