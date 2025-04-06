<%@ page pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <title>利用者登録</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        form {
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>

<body>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
</form>
</br>
<!-- クライアントリクエストへのリンク -->
<a href="${pageContext.request.contextPath}/clientRequests">利用者リクエスト</a>
<h1>利用者登録</h1>
<form action="${pageContext.request.contextPath}/clientRegister" method="post">
    <!-- actionパラメータを追加 -->
    <input type="hidden" name="action" value="register">

    <label for="registerClientId">利用者ID:</label>
    <input type="text" name="clientId" id="registerClientId" required placeholder="利用者IDを入力" />
    
    <label for="registerClientName">利用者名:</label>
    <input type="text" name="clientName" id="registerClientName" required placeholder="利用者名を入力" />
    
    <label for="registerClientSkill">スキル:</label>
    <select name="clientSkill" id="registerClientSkill" required>
        <option value="">選択してください</option>
        <option value="1">1.生活援助</option>
        <option value="2">2.身体介護</option>
        <option value="3">3.両方</option>
    </select>
    
    <input type="submit" value="登録" />
</form>


<hr/>

<h1>利用者情報検索</h1>
<form action="${pageContext.request.contextPath}/lookupClient" method="get">
    <label for="lookupClientId">利用者ID:</label>
    <input type="text" name="clientId" id="lookupClientId" placeholder="利用者IDを入力" />
    
    <label for="lookupClientName">利用者名:</label>
    <input type="text" name="clientName" id="lookupClientName" placeholder="利用者名を入力" />
    
    <label for="lookupClientSkill">スキル:</label>
    <select name="clientSkill" id="lookupClientSkill">
        <option value="">選択してください</option>
        <option value="1">1.生活援助</option>
        <option value="2">2.身体介護</option>
        <option value="3">3.両方</option>
    </select>
    
    <input type="submit" value="検索" />
</form> 


 
<hr/>

<table>
    <tr>
        <th>利用者ID</th>
        <th>利用者名</th>
        <th>スキル</th>
        <th></th>
    </tr>
    <c:if test="${not empty errorMessage}">
        <tr>
            <td colspan="4" style="color:red;">${errorMessage}</td>
        </tr>
    </c:if>
    <c:forEach var="client" items="${clientList}">
        <tr>
            <td>${client.clientId}</td>
            <td>${client.clientName}</td>
            <td>${client.clientSkill}</td>
            <td>
                <form action="${pageContext.request.contextPath}/clientRegister" method="post">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="clientId" value="${client.clientId}" />
                    <input type="submit" value="削除" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
