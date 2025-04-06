<%@ page pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <title>利用者リクエスト</title>
    <style>
        table {
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 5px;
            text-align: center;
        }
    </style>
   <script>
    // 利用者IDから名前とスキルを補完するためのAJAX関数
    function lookupClientById() {
        var clientId = document.getElementById("clientId").value;
        if (clientId) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "${pageContext.request.contextPath}/lookupClient?clientId=" + clientId, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);

                    if (response.length > 0) {
                        // データをセット (複数の利用者を考慮)
                        document.getElementById("clientName").value = response[0].clientName || "";
                        document.getElementById("clientSkill").value = response[0].clientSkill || "";
                    } else {
                        document.getElementById("clientName").value = "";
                        document.getElementById("clientSkill").value = "";
                    }

                    // デバッグ確認
                    console.log("Client Name:", document.getElementById("clientName").value);
                    console.log("Client Skill:", document.getElementById("clientSkill").value);

                    // 必須フィールドを有効にする
                    document.getElementById("clientName").setAttribute("required", "true");
                    document.getElementById("clientSkill").setAttribute("required", "true");
                }
            };
            xhr.send();

            // 一時的にバリデーションを無効化
            document.getElementById("clientName").removeAttribute("required");
            document.getElementById("clientSkill").removeAttribute("required");
        } else {
            document.getElementById("clientName").value = "";
            document.getElementById("clientSkill").value = "";
        }
    }

    // 名前から利用者IDとスキルを補完するためのAJAX関数
    function lookupClientByName() {
        var clientName = document.getElementById("clientName").value;
        if (clientName) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "${pageContext.request.contextPath}/lookupClient?clientName=" + encodeURIComponent(clientName), true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);

                    if (response.length > 0) {
                        // データをセット (複数の利用者を考慮)
                        document.getElementById("clientId").value = response[0].clientId || "";
                        document.getElementById("clientSkill").value = response[0].clientSkill || "";
                    } else {
                        document.getElementById("clientId").value = "";
                        document.getElementById("clientSkill").value = "";
                    }

                    // デバッグ確認
                    console.log("Client ID:", document.getElementById("clientId").value);
                    console.log("Client Skill:", document.getElementById("clientSkill").value);
                }
            };
            xhr.send();
        } else {
            document.getElementById("clientId").value = ""; // 名前が空ならIDもクリア
            document.getElementById("clientSkill").value = ""; // スキルもクリア
        }
    }
</script>


</head>
<body>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
</form>
<a href="${pageContext.request.contextPath}/clientRegister">利用者データベース登録・検索</a>
<br/>
<a href="${pageContext.request.contextPath}/selectClient">マッチング決定</a>
<br/>
<!-- MatchingServletへのリンク -->
<a href="${pageContext.request.contextPath}/match">マッチング・選択</a>
<br/>

<!-- 職員スケジュールへのリンク -->
<a href="${pageContext.request.contextPath}/staffSchedule">職員ページ</a>
<h1>利用者リクエスト</h1>

<!-- エラーメッセージ表示 -->
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>

<!-- スケジュール入力フォーム -->
<form action="${pageContext.request.contextPath}/requests/add" method="post">
    <table>
        <tr>
            <td>利用者ID:</td>
            <td><input type="text" name="clientId" id="clientId" required onkeyup="lookupClientById()"/></td>
        </tr>
        <tr>
            <td>名前:</td>
            <td><input type="text" name="clientName" id="clientName" required onkeyup="lookupClientByName()"/></td>
        </tr>
        <tr>
            <td>スキル:</td>
            <td><input type="text" name="clientSkill" id="clientSkill" required/></td> <!-- スキルの入力 -->
        </tr>
        <tr>
            <td>希望曜日・時間:</td>
            <td>
                <table>
                    <thead>
                        <tr>
                            <th>時間</th>
                            <th>月</th>
                            <th>火</th>
                            <th>水</th>
                            <th>木</th>
                            <th>金</th>
                            <th>土</th>
                            <th>日</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>朝</td>
                            <td><input type="checkbox" name="availability" value="月曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="火曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="水曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="木曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="金曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="土曜日-朝"></td>
                            <td><input type="checkbox" name="availability" value="日曜日-朝"></td>
                        </tr>
                        <tr>
                            <td>昼</td>
                            <td><input type="checkbox" name="availability" value="月曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="火曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="水曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="木曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="金曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="土曜日-昼"></td>
                            <td><input type="checkbox" name="availability" value="日曜日-昼"></td>
                        </tr>
                        <tr>
                            <td>夕</td>
                            <td><input type="checkbox" name="availability" value="月曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="火曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="水曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="木曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="金曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="土曜日-夕"></td>
                            <td><input type="checkbox" name="availability" value="日曜日-夕"></td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
    <input type="submit" value="リクエストを追加"/> 
</form>
<hr>

<!-- 既存のスケジュール表示 -->
<table border="1">
    <thead>
        <tr>
            <th>利用者ID</th>
            <th>名前</th>
            <th>曜日</th>
            <th>時間</th>
            <th>スキル</th> <!-- スキル列を追加 -->
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="entry" items="${requests}">
            <tr>
                <td><c:out value="${entry.clientId}"/></td>
                <td><c:out value="${entry.clientName}"/></td>
                <td><c:out value="${entry.dayOfWeek}"/></td>
                <td><c:out value="${entry.shift}"/></td>
                <td><c:out value="${(entry.clientSkill != null) ? entry.clientSkill : '未設定'}"/></td> <!-- スキル表示 -->
                <td>
                    <form action="${pageContext.request.contextPath}/clientRequests" method="post" style="display:inline;">
                        <input type="hidden" name="requestsId" value="${entry.id}"/>
                        <input type="submit" value="削除"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
