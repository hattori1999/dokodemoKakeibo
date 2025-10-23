package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.EntryLogic;

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
        if (idStr != null) {
            try {
                int entryId = Integer.parseInt(idStr);
                EntryLogic logic = new EntryLogic();
                boolean success = logic.execute(entryId);

                if (success) {
                    response.sendRedirect("EntrySearchServlet");
                } else {
                    response.getWriter().write("削除できませんでした");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("不正なIDです");
            }
        } else {
            response.getWriter().write("IDが指定されていません");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
