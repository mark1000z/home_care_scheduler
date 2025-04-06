package controller;//⑤

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;
import domain.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//doGetでログインのフォームページ表示してID・パスワードを入力
		request.getRequestDispatcher("/WEB-INF/view/login.jsp")
				.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//doGetで入力した値をdoPostで受け取ってログインチェック
	String loginId=request.getParameter("id");
	String loginPass=request.getParameter("pass");
	//あとで追加本来ならバリデーション！！！！
	//Daoを使いID,パスをチェック　UserDaoImplのfindByLoginAndPassでデータベースと連動して中身を調べてチェックをする
	//UserDaoImplを使いたいときは直接newせずにDaoFactoryがnewしてくれる
	//DaoFactoryのcreatUserDaoを使う
	UserDao dao=DaoFactory.createUserDao();
	//戻り値があるのでそれを受け取る
	//Userをインポートします（domain)を選択
	User user=dao.findByLoginAndPass(loginId, loginPass); //nullだったらrequest.setAttributeでメッセージをつくる=
			//dao.が持っているfindByLoginAndPassのメソッドでユーザーが入力した
	//loginId loginPassを引数に渡すとfindByLoginAndPassが処理してくれる、裏でデータベースと連動してIDとパスワードをチェックしてくれる
	//チェックしたのちにUserDaoのUser findByLoginAndPassのUserという形で戻り値が返ってくる
	//正しければユーザー情報が入っている正しくなければnullが入っている
	
	//User user＝のuserの中身があるかをチェック、nullならidとパスが間違っているのでもう一度ログインページを再表示でエラーのメッセージを渡す
	//User user＝の中があればログインに成功しているので会員ページへとばしてあげる
	if(user==null) {
	//nullだったらrequest.setAttributeでメッセージをつくる
	//ログイン失敗
		request.setAttribute("error", "ID、パスワードが合いません"); //errorという箱にエラーメッセージを格納
		request.getRequestDispatcher("/WEB-INF/view/login.jsp")      //jspでログインページを再表示
				.forward(request, response);
		return;//いったんここで終わらせる
	}
	//ログイン成功　成功した証をセッションに格納しておく
	request.getSession().setAttribute("user", user);//userという箱の中にuserの情報を入れてログインしているという証にしている
	//会員専用ページへリダイレクト
	response.sendRedirect(request.getContextPath() + "/staffSchedule");//ここではitemの中のlistを会員専用ページとする
	
	
	
		
	}

}
