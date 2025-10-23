package logic;

import dao.UserDAO;
import model.User;

public class LoginLogic {

    // User オブジェクトを返すように変更
    public User execute(User user) {
        UserDAO dao = new UserDAO();
        return dao.findById(user.getUserId(), user.getPass());
    }
}
