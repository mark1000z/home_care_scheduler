<%@ page pageEncoding="UTF-8"%><!--⑥ -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html><!--⑭  -->
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>職員登録</h1><!-- ⑭ -->
	<!-- errorメッセージ -->
	<c:if test="${!empty error }"><!--もしエラーがからじゃなかったら=エラー  -->
	<p><c:out value="${error }"/></p><!--出力系に関してはｃ:out これは丁寧な書き方 -->
	</c:if>
	
	
	<form action="" method="post"><!--doPostで受け取る  -->

			<p>ログインID: <input type="text" name="id">
			</p>                       
							<!--textはpassword  -->	
			<p>パスワード: <input type="password" name="pass">
			</p>
			<p>
				<input type="submit" value="登録する"><!--登録に変更  -->
			</p>
		</form>
		<p><!--⑭新規登録  -->
		<a href="login">ログインページ</a>
		</p>
</body>
</html>

