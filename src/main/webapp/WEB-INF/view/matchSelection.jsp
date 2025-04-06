<%@ page pageEncoding="UTF-8"%>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>マッチング・選択</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: center; /* セルを中央に整列 */
        }
        .divider {
            border-top: 2px solid #000; /* 区切り線 */
            margin: 20px 0; /* 上下のマージン */
        }

        .staff-client-container {
            display: flex;
            justify-content: space-between; /* 左右に均等に配置 */
            align-items: center; /* 縦方向の中央揃え */
        }

        .staff-name {
            text-align: left; /* 左側に揃える */
            margin-right: 10px; /* 右側に少しスペース */
        }

        .client-name {
            text-align: right; /* 右側に揃える */
        }
    </style>
</head>
<body>
<!-- エラーメッセージの表示 --> 
<c:if test="${not empty errorMessage}">
    <div style="color: red; font-weight: bold;">
        ${errorMessage}
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/staffSchedule">職員スケジュール</a>
<br>
<a href="${pageContext.request.contextPath}/selectClient">マッチング決定</a>
<h1>マッチング・選択 (1.生活援助 2.身体介護 3.両方)</h1>

<!-- フォームが送信されたときに match エンドポイント(リクエストを受け取りフォームデータを処理するための場所）POSTリクエストを送信する -->
<form action="match" method="post"> 
    <c:if test="${not empty matchMap}">
        <table>
            <tr>
                <th>職員名</th>
                <th>曜日</th>
                <th>時間</th>
                <th>利用者を選択</th>
            </tr>

            <c:set var="order" value="${['月曜日の朝', '月曜日の昼', '月曜日の夕', '火曜日の朝', '火曜日の昼', '火曜日の夕', '水曜日の朝', '水曜日の昼', '水曜日の夕', '木曜日の朝', '木曜日の昼', '木曜日の夕', '金曜日の朝', '金曜日の昼', '金曜日の夕', '土曜日の朝', '土曜日の昼', '土曜日の夕', '日曜日の朝', '日曜日の昼', '日曜日の夕']}" />

            <c:forEach var="timeSlot" items="${order}">
                <c:forEach var="entry" items="${matchMap}" varStatus="status">
                    <c:set var="combinedKey" value="${entry.key.dayOfWeek}の${entry.key.shift}" />
                    <c:if test="${combinedKey eq timeSlot}">
                        <tr>
                            <td>${entry.key.staffName}</td>
                            <td>${entry.key.dayOfWeek}</td>
                            <td>${entry.key.shift}</td>
                            <td>
                            <!--${status.index}は、現在のループのインデックスを表すこの値が0から始まるため、0, 1, 2,...のように連番が振られる  -->
                                <select name="clientName_${status.index}">
                                    <option value="">選択しない</option>
                                    <c:forEach var="client" items="${entry.value}">
                                        <option value="${client.clientName}">${client.clientName}</option>
                                    </c:forEach>
                                </select>
                                <!-- 隠しフィールドで職員名、曜日、シフトを保持 -->
                                <input type="hidden" name="staffName_${status.index}" value="${entry.key.staffName}" />
                                <input type="hidden" name="dayOfWeek_${status.index}" value="${entry.key.dayOfWeek}" />
                                <input type="hidden" name="shift_${status.index}" value="${entry.key.shift}" />
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </table>

        <!-- テーブルの行数を送信 -->
        <input type="hidden" name="rowCount" value="${fn:length(matchMap)}" />
    </c:if>

    <input type="submit" value="選択" />
</form>

<!-- 職員と利用者のテーブルの間に区切りを追加 -->
<div class="divider"></div>

<!-- 未マッチ職員・利用者のリスト -->
<h2>未マッチ表（左職員・右利用者）</h2>
<table>
    <thead>
        <tr>
            <th>シフト</th>
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
        <c:forEach var="shift" items="${['朝', '昼', '夕']}">
            <tr>
                <td>${shift}</td>

                <c:forEach var="day" items="${['月', '火', '水', '木', '金', '土', '日']}">
                    <td class="split-cell">
                        <c:set var="staffNames" value="" />
                        <c:set var="clientNames" value="" />

                        <!-- 未マッチ職員のループ -->
                        <c:forEach var="staff" items="${unmatchedStaff}">
                            <c:if test="${staff.shift == shift && fn:substring(staff.dayOfWeek, 0, 1) == day}">
                                <c:set var="staffNames" value="${staffNames}${staff.staffName} ${staff.staffSkill}, " /> <!-- スキルの値を追加 -->
                            </c:if>
                        </c:forEach>

                        <!-- 未マッチ利用者のループ -->
                        <c:forEach var="client" items="${unmatchedClients}">
                            <c:if test="${client.shift == shift && fn:substring(client.dayOfWeek, 0, 1) == day}">
                                <c:set var="clientNames" value="${clientNames}${client.clientName} ${client.clientSkill}, " /> <!-- スキルの値を追加 -->
                            </c:if>
                        </c:forEach>

                        <div class="staff-client-container">
                            <!-- 職員の表示 -->
                            <div class="staff-name">
                                <c:choose>
                                    <c:when test="${fn:trim(staffNames) != ''}">
                                        ${fn:trim(staffNames.substring(0, staffNames.length() - 2))}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- 利用者の表示 -->
                            <div class="client-name">
                                <c:choose>
                                    <c:when test="${fn:trim(clientNames) != ''}">
                                        ${fn:trim(clientNames.substring(0, clientNames.length() - 2))}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- デバッグ情報: 未マッチ職員と利用者の全体 -->
<h2>未マッチリスト</h2>
<ul>
    <li><strong>未マッチ職員:</strong></li>
    <c:forEach var="staff" items="${unmatchedStaff}">
        <li>${staff.staffName} ( ${staff.dayOfWeek},  ${staff.shift},  ${staff.staffSkill})</li> <!-- スキルの値を表示 -->
    </c:forEach>
</ul>

<ul>
    <li><strong>未マッチ利用者:</strong></li>
    <c:forEach var="client" items="${unmatchedClients}">
        <li>${client.clientName} ( ${client.dayOfWeek},  ${client.shift},  ${client.clientSkill})</li> <!-- スキルの値を表示 -->
    </c:forEach>
</ul>

</body>
</html>
