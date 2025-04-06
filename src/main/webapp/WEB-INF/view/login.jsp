<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">

<title>ログイン</title>
<style>
body {
	background-image:
		url("<%=request.getContextPath()%>/images/Austria.png");
	background-size: cover; /* 画像を画面全体にカバー */
	background-position: center; /* 中央に配置 */
	background-repeat: no-repeat; /* 画像を繰り返さない */
	background-attachment: fixed; /* 背景画像をスクロールに固定 */
}

form {
	padding: 20px; /* フォーム全体に余白を追加 */
	border-radius: 10px; /* 角を丸くする */
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 少し影を付ける */
}

label, input, select, button {
	background-color: rgba(255, 255, 255, 0.8); /* 半透明の背景を文字や入力フィールドに適用 */
	padding: 10px; /* 各要素に適度な余白 */
	border-radius: 5px; /* 角を少し丸く */
	display: block; /* 要素を縦に並べる */
	margin-bottom: 10px; /* 要素間のスペース */
}

input[type="submit"], button {
	background-color: rgba(0, 123, 255, 0.8); /* ボタンにも半透明の色 */
	color: white; /* テキスト色を白に */
	border: none; /* ボタンの枠線を無くす */
	cursor: pointer; /* マウスホバー時にカーソル変更 */
}

input[type="submit"]:hover, button:hover {
	background-color: rgba(0, 123, 255, 1); /* ボタンをホバーした時に透明度をなくす */
}

h1 {
	color: black;
}

form {
	color: red;
}

a {
	color: red;
}

h1 {
	color: orange;
	animation: move 3s infinite; /* 3秒かけて動き、無限に繰り返す */
}

@
keyframes move { 0% {
	transform: translateX(0); /* 初期位置 */
}
50
%
{
transform
:
translateX(
50px
); /* 水平方向に50px動かす */
}
100
%
{
transform
:
translateX(
0
); /* 元の位置に戻す */
}
 }
</style>






<link rel="stylesheet" href="">
</head>
<body>
	<h1>シフト管理システム</h1>
	<c:if test="${!empty error }">
	</c:if>

	<p>
		<c:out value="${error }" />
	</p>




	<form action="" method="post">
		<p>
			<strong>ログインID：</strong><input type="text" name="id">
		</p>
		<!-- 開発中にtext　公開時にはpasswordに変更 -->
		<p>
			<strong>パスワード：</strong><input type="password" name="pass">
		</p>
		<p>
			<input type="submit" value="ログイン">
		</p>




	</form>

	<p>
		<a href="register">職員登録</a>
	</p>







</body>
</html>