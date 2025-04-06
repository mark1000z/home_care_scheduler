<%@ page pageEncoding="UTF-8"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>マッチング決定</title>
    <style>
        table {
            border-collapse: collapse; /* テーブルの境界線を結合 */
            width: 100%; /* テーブルの幅を100%に設定 */
        }
        th, td {
            border: 1px solid black; /* セルに黒い境界線を追加 */
            padding: 8px; /* セル内の余白を8pxに設定 */
            text-align: center; /* セル内のテキストを中央揃え */
        }
        th {
            background-color: #f2f2f2; /* ヘッダーセルの背景色を薄い灰色に設定 */
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
</form>
</br>
<a href="${pageContext.request.contextPath}/match">マッチング・選択</a>
    <h1>マッチング決定</h1>
    
    <!-- エラーメッセージの表示 -->
   <c:if test="${not empty errorMessage}"> <!-- エラーメッセージが存在する場合 -->
        <div style="color:red;"> <!-- エラーメッセージを赤色で表示 -->
            <p>${errorMessage}</p> <!-- エラーメッセージを出力 -->
        </div>
    </c:if>
    <!-- マッチング結果のテーブル -->
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
            <!-- 朝のシフト -->
            <tr>
                <td>朝</td>
                <!-- 月曜日朝のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}"> <!-- matchesリストをループ -->
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '月曜日'}"> <!-- 月曜日の朝シフトをチェック -->
                            <div>${match.staffName} - ${match.clientName}</div> <!-- スタッフ名とクライアント名を表示 -->
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;"> <!-- 削除フォーム -->
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/> <!-- スタッフ名を隠しフィールドに設定 -->
                                <input type="hidden" name="clientName" value="${match.clientName}"/> <!-- クライアント名を隠しフィールドに設定 -->
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/> <!-- 曜日を隠しフィールドに設定 -->
                                <input type="hidden" name="shift" value="${match.shift}"/> <!-- シフトを隠しフィールドに設定 -->
                                <input type="submit" value="削除"/> <!-- 削除ボタン -->
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <!-- 火曜日朝のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '火曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <!-- 水曜日朝のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '水曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 木曜日朝のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '木曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 金曜日朝のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '金曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 土曜日朝のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '土曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 日曜日朝のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '朝' && match.dayOfWeek == '日曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
            </tr>

            <!-- 昼のシフト -->
            <tr>
                <td>昼</td>

                <!-- 月曜日昼のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '月曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                     </c:forEach>
                </td>

 <!-- 火曜日の昼マッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '火曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <!-- 水曜日昼のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '水曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 木曜日昼のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '木曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 金曜日昼のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '金曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 土曜日昼のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '土曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 日曜日昼のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '昼' && match.dayOfWeek == '日曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
   			</tr>
   			
   			<!-- 朝のシフト -->
            <tr>
                <td>夕</td>
                <!-- 月曜日夕のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '月曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <!-- 火曜日夕のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '火曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <!-- 水曜日夕のマッチング結果 -->
                <td>
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '水曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 木曜日夕のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '木曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 金曜日夕のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '金曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                               <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 土曜日夕のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '土曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す --> 
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <!-- 日曜日夕のマッチング結果 -->
                    <c:forEach var="match" items="${matches}">
                        <c:if test="${match.shift == '夕' && match.dayOfWeek == '日曜日'}">
                            <div>${match.staffName} - ${match.clientName}</div>
                            <form action="${pageContext.request.contextPath}/selectClient" method="post" style="display:inline;">
                                 <input type="hidden" name="matchingResultId" value="${match.id}"/> <!-- マッチング結果IDを渡す -->
                                <input type="hidden" name="staffName" value="${match.staffName}"/>
                                <input type="hidden" name="clientName" value="${match.clientName}"/>
                                <input type="hidden" name="dayOfWeek" value="${match.dayOfWeek}"/>
                                <input type="hidden" name="shift" value="${match.shift}"/>
                                <input type="submit" value="削除"/>
                            </form>
                    </c:if>
                </c:forEach>
           
            </td>
        </tr>
    </table>
</body>
</html>










