package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGetで入力画面 idとパスワードになるのでほぼlogin.jspと同じ
				//login.jspをコピーしてregister.jspをつくる
		// 入力画面
		request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//doPostでデータベースに登録する
		//doGetで入力した値をdoPostで受け取ってログインチェック
		
		// DBへ登録
		String loginId=request.getParameter("id");
		String loginPass=request.getParameter("pass");
		
		//あとで追加　バリデーション必要！！
		//Daoを使いDBへ登録
		//UserDaoImplを使いたいときは直接newせずにDaoFactoryがnewしてくれる
		//DaoFactoryのcreatUserDaoを使う
		UserDao dao=DaoFactory.createUserDao();
		
		//⑭登録はdaoがもっているinsert
		//会員番号はまだ登録していないのでnull
		//Userをインポートしますは（domain)
		//User(null,loginId,loginPass)のユーザー情報をinsertメソッドに渡してあげる
		dao.insert(new User(null,loginId,loginPass));//insertに情報を渡すやり方としては②のUserDaoを見てみると（User user)のUser型
		
		
		//どこに遷移させるかはリダイレクトでログイン画面に戻してあげる
		//insertは戻り値がないのでそのまま戻してあげる
		//insertを実装する⑮UserDaoImplへ
		response.sendRedirect(request.getContextPath() + "/login");
		
		
		
		
		
	}

}
