package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.LoginLogic;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
		// TODO Auto-generated method stub

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	String userIdStr = request.getParameter("userId");
	        String pass = request.getParameter("password");

	        if (userIdStr == null || userIdStr.isEmpty() || pass == null || pass.isEmpty()) {
	            request.setAttribute("errorMessage", "ユーザーIDまたはパスワードが未入力です");
	            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
	            return;
	        }

	        int userId = Integer.parseInt(userIdStr);
	        User user = new User(userId,null,pass); // 名前はまだnull

	        LoginLogic loginLogic = new LoginLogic();
	        User dbUser = loginLogic.execute(user);

	        if (dbUser != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("loginUser", dbUser);
	            session.setAttribute("userName", dbUser.getUserName());
	            session.setAttribute("userId", dbUser.getUserId());

	            //JSP ではなく MyPageServlet にリダイレクト
	            response.sendRedirect("MypageServlet");
	        } else {
	            request.setAttribute("errorMessage", "ユーザーIDまたはパスワードが間違っています");
	            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
	        }

	    }
	}