<%@ page pageEncoding="UTF-8"%> 

<html>
<head>
    <title>エラー</title>
</head>
<body>
    <h1>重複しているようです</h1>
    <p>${errorMessage}</p><!--エラーメッセージを表示-->

    <!-- スタックトレースを表示する場合（デバッグ用） -->
    <pre>
    <%
    if (request.getAttribute("stackTrace") != null) {
        out.println(request.getAttribute("stackTrace").toString());
    }
    %>
    </pre>

    <!-- ユーザーが選択肢を選べるように -->
    <a href="${pageContext.request.contextPath}/match">マッチ・選択へ戻る</a><br/>
    <a href="${pageContext.request.contextPath}/selectClient">マッチング決定へ</a><br/>
</body>
</html>
