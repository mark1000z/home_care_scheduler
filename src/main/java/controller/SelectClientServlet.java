package controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;

// 利用者選択を管理するサーブレット
@WebServlet("/selectClient")
public class SelectClientServlet extends HttpServlet {

    // GETメソッドの処理
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GETリクエストに対する処理
        System.out.println("GET request received");

        // 利用者のマッチング結果を取得
        try {
            UserDao userDao = DaoFactory.createUserDao();
            List<MatchingResult> results = userDao.getMatchingResults();
            request.setAttribute("matches", results);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "エラーが発生しました。再試行してください");
        }

        // マッチング結果表示ページにフォワード
        request.getRequestDispatcher("/WEB-INF/view/matchResults.jsp").forward(request, response);
    }

    // POSTメソッドの処理
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームから送信された職員名と利用者名を取得
        String staffName = request.getParameter("staffName");
        String clientName = request.getParameter("clientName"); // 利用者名の取得
        String dayOfWeek = request.getParameter("dayOfWeek");
        String shift = request.getParameter("shift");

        // デバッグ用に選択された情報を出力
        System.out.println("Selected Staff Name: " + staffName);
        System.out.println("Selected Client Name: " + clientName);
        System.out.println("Day of Week: " + dayOfWeek);
        System.out.println("Shift: " + shift);

        // 職員名が null または空文字列の場合のエラーハンドリング
        if (staffName == null || staffName.isEmpty()) {
            request.setAttribute("errorMessage", "職員名が入力されていません");
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
            return;
        }

        // 利用者名が null または空の場合のエラーハンドリング
        if (clientName == null || clientName.isEmpty()) {
            request.setAttribute("errorMessage", "利用者が選択されていません");
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
            return;
        }

        // デバッグ用にリクエストパラメータを出力
        System.out.println("Request Parameters:");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        try {
            // DaoFactoryからUserDaoインスタンスを取得
            UserDao userDao = DaoFactory.createUserDao();

            // 削除処理のチェック
            String matchingResultId = request.getParameter("matchingResultId");
            if (matchingResultId != null && !matchingResultId.isEmpty()) {
                // 削除処理を実行
                userDao.deleteMatchingResult(matchingResultId); // 削除メソッドを呼び出す

                // 削除後のリダイレクト
                response.sendRedirect(request.getContextPath() + "/selectClient");
                return; // 処理を終了
            }

            // ここで選択された職員と利用者のマッチング処理を行う
            MatchingResult result = new MatchingResult(staffName, clientName, dayOfWeek, shift); // clientNameを使用
            userDao.saveMatchingResult(result); // マッチング結果を保存

            // マッチング結果を取得してリクエストにセット
            List<MatchingResult> results = userDao.getMatchingResults();
            request.setAttribute("matches", results);
            
            // マッチング結果の表示ページにフォワード
            request.getRequestDispatcher("/WEB-INF/view/matchResults.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // エラーメッセージをリクエストにセット
            request.setAttribute("errorMessage", "エラーが発生しました。再試行してください");
            // エラーメッセージ表示ページにフォワード
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}
