<%@ page pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <title>職員スケジュール</title>
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
    // 職員IDから名前とスキルを補完するためのAJAX関数
    function lookupStaffById() {
        var staffId = document.getElementById("staffId").value;
        if (staffId) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "${pageContext.request.contextPath}/lookupStaff?staffId=" + staffId, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);

                    if (response.length > 0) {
                        // データをセット (複数の職員を考慮)
                        document.getElementById("staffName").value = response[0].staffName || "";
                        document.getElementById("staffSkill").value = response[0].staffSkill || "";
                    } else {
                        document.getElementById("staffName").value = "";
                        document.getElementById("staffSkill").value = "";
                    }

                    // デバッグ確認
                    console.log("Staff Name:", document.getElementById("staffName").value);
                    console.log("Staff Skill:", document.getElementById("staffSkill").value);

                    // 必須フィールドを有効にする
                    document.getElementById("staffName").setAttribute("required", "true");
                    document.getElementById("staffSkill").setAttribute("required", "true");
                }
            };
            xhr.send();

            // 一時的にバリデーションを無効化
            document.getElementById("staffName").removeAttribute("required");
            document.getElementById("staffSkill").removeAttribute("required");
        } else {
            document.getElementById("staffName").value = "";
            document.getElementById("staffSkill").value = "";
        }
    }

    // 名前から職員IDとスキルを補完するためのAJAX関数
    function lookupStaffByName() {
        var staffName = document.getElementById("staffName").value;
        if (staffName) {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "${pageContext.request.contextPath}/lookupStaff?staffName=" + encodeURIComponent(staffName), true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);

                    if (response.length > 0) {
                        // データをセット (複数の職員を考慮)
                        document.getElementById("staffId").value = response[0].staffId || "";
                        document.getElementById("staffSkill").value = response[0].staffSkill || "";
                    } else {
                        document.getElementById("staffId").value = "";
                        document.getElementById("staffSkill").value = "";
                    }

                    // デバッグ確認
                    console.log("Staff ID:", document.getElementById("staffId").value);
                    console.log("Staff Skill:", document.getElementById("staffSkill").value);
                }
            };
            xhr.send();
        } else {
            document.getElementById("staffId").value = ""; // 名前が空ならIDもクリア
            document.getElementById("staffSkill").value = ""; // スキルもクリア
        }
    }
</script>

</head>
<body>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
</form>

<a href="${pageContext.request.contextPath}/staffRegister">職員データベース登録・検索</a>
<br/>
<a href="${pageContext.request.contextPath}/selectClient">マッチング決定</a>
<br/>
<!-- MatchingServletへのリンク -->
<a href="${pageContext.request.contextPath}/match">マッチング・選択</a>
<br/>

<!-- クライアントリクエストへのリンク -->
<a href="${pageContext.request.contextPath}/clientRequests">利用者ページ</a>
<h1>職員スケジュール</h1>

<!-- エラーメッセージ表示 -->
<c:if test="${not empty errorMessage}">
    <div style="color: red;">${errorMessage}</div>
</c:if>

<!-- スケジュール入力フォーム -->
<form action="${pageContext.request.contextPath}/schedule/add" method="post">
    <table>
        <tr>
            <td>職員ID:</td>
            <td><input type="text" name="staffId" id="staffId" required onkeyup="lookupStaffById()"/></td>
        </tr>
        <tr>
            <td>名前:</td>
            <td><input type="text" name="staffName" id="staffName" required onkeyup="lookupStaffByName()"/></td>
        </tr>
        <tr>
            <td>スキル:</td>
            <td><input type="text" name="staffSkill" id="staffSkill" required/></td> <!-- スキルの入力 -->
        </tr>
        <tr>
            <td>勤務可能曜日・時間:</td>
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
    <input type="submit" value="スケジュールを追加"/> 
</form>
<hr>

<!-- 既存のスケジュール表示 -->
<table border="1">
    <thead>
        <tr>
            <th>職員ID</th>
            <th>名前</th>
            <th>曜日</th>
            <th>時間</th>
            <th>スキル</th> <!-- スキル列を追加 -->
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="entry" items="${schedule}">
            <tr>
                <td><c:out value="${entry.staffId}"/></td>
                <td><c:out value="${entry.staffName}"/></td>
                <td><c:out value="${entry.dayOfWeek}"/></td>
                <td><c:out value="${entry.shift}"/></td> 
                <td><c:out value="${(entry.staffSkill != null) ? entry.staffSkill : '未設定'}"/></td> 
                <td>
                    <form action="${pageContext.request.contextPath}/staffSchedule" method="post" style="display:inline;">
                        <input type="hidden" name="scheduleId" value="${entry.id}"/>
                        <input type="submit" value="削除"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
