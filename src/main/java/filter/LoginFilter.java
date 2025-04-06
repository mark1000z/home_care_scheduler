package filter;//⑮

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// /item/list , /item/detail , /item/cart , /user/mypage , /user/history などここだけに適応したい
@WebFilter(urlPatterns= {"/item/*","/user/*"})//itemに紐づいている全てのページ　userに紐づいて全てのページ
public class LoginFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//セッションにログインした証があるかを調べる
		HttpServletRequest req=(HttpServletRequest) request;//requestの形に変換してあげる　キャストする
		HttpServletResponse res=(HttpServletResponse) response;
		//これでサーブレットでもrequest responseが使えるようになる
		
		HttpSession session=req.getSession();//requestが持っているgetSession
		if(session.getAttribute("user")==null) {//sessionの中に入っているgetAttributeでuserがセットされているか？されていなければurlPatterns
	//でセットしたurlにいけない		
			res.sendRedirect(req.getContextPath() + "/login");//filterのリクエストなのでreqに変更 loginページへリダイレクト
			return;//止めておく
		}
		
		
		//これだけは絶対に消してはいけない！！
		chain.doFilter(request, response);
	}

}
