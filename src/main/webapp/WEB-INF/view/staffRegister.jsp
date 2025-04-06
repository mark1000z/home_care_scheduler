<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <title>職員登録</title>
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
<!-- 職員スケジュールへのリンク -->
<a href="${pageContext.request.contextPath}/staffSchedule">職員スケジュール</a>
<h1>職員登録</h1>
<form action="${pageContext.request.contextPath}/staffRegister" method="post">
    <!-- actionパラメータを追加 -->
    <input type="hidden" name="action" value="register">

    <label for="registerStaffId">職員ID:</label>
    <input type="text" name="staffId" id="registerStaffId" required placeholder="職員IDを入力" />
    
    <label for="registerStaffName">職員名:</label>
    <input type="text" name="staffName" id="registerStaffName" required placeholder="職員名を入力" />
    
    <label for="registerStaffSkill">スキル:</label>
    <select name="staffSkill" id="registerStaffSkill" required>
        <option value="">選択してください</option>
        <option value="1">1.生活援助</option>
        <option value="2">2.身体介護</option>
        <option value="3">3.両方</option>
    </select>
    
    <input type="submit" value="登録" />
</form>


<hr/>

<h1>職員情報検索</h1>
<form action="${pageContext.request.contextPath}/lookupStaff" method="get">
    <label for="lookupStaffId">職員ID:</label>
    <input type="text" name="staffId" id="lookupStaffId" placeholder="職員IDを入力" />
    
    <label for="lookupStaffName">職員名:</label>
    <input type="text" name="staffName" id="lookupStaffName" placeholder="職員名を入力" />
    
    <label for="lookupStaffSkill">スキル:</label>
    <select name="staffSkill" id="lookupStaffSkill">
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
        <th>職員ID</th>
        <th>職員名</th>
        <th>スキル</th>
        <th></th>
    </tr>
    <c:if test="${not empty errorMessage}">
        <tr>
            <td colspan="4" style="color:red;">${errorMessage}</td>
        </tr>
    </c:if>
    <c:forEach var="staff" items="${staffList}">
        <tr>
            <td>${staff.staffId}</td>
            <td>${staff.staffName}</td>
            <td>${staff.staffSkill}</td>
            <td>
                <form action="${pageContext.request.contextPath}/staffRegister" method="post">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="staffId" value="${staff.staffId}" />
                    <input type="submit" value="削除" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
