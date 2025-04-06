package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/clientRegister")
public class ClientRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGetが呼ばれた");

        // DAO経由で利用者リストを取得
        UserDao dao = DaoFactory.createUserDao();
        List<Client> clientList = null;

        try {
            clientList = dao.findAllClient(); // 全利用者情報を取得
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // リストをリクエスト属性に設定
        request.setAttribute("clientList", clientList);

        // JSPにフォワード
        request.getRequestDispatcher("/WEB-INF/view/clientRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Received action: " + action); // デバッグメッセージ

        UserDao dao = DaoFactory.createUserDao();
        String errorMessage = null; // エラーメッセージを格納する変数

        if ("register".equals(action)) {
            // 利用者登録処理
            String clientId = request.getParameter("clientId");
            String clientName = request.getParameter("clientName");
            String clientSkill = request.getParameter("clientSkill"); // スキルを取得
            
            // デバッグメッセージ
            System.out.println("Received clientId: " + clientId);
            System.out.println("Received clientName: " + clientName);
            System.out.println("Received clientSkill: " + clientSkill);

            try {
                // スキルの妥当性チェック
                int skill = Integer.parseInt(clientSkill);
                if (skill < 1 || skill > 3) {
                    errorMessage = "スキルは1（生活援助）、2（身体介護）、または3（両方）のいずれかでなければなりません。";
                } else {
                    // Clientオブジェクトを作成
                    Client client = new Client();
                    client.setClientId(Integer.parseInt(clientId));
                    client.setClientName(clientName);
                    client.setClientSkill(skill); // 妥当性が確認されたスキルを設定

                    // 利用者情報をデータベースに追加
                    dao.insertClient(client);
                    // 登録後は clientRegister サーブレットにリダイレクト
                    response.sendRedirect(request.getContextPath() + "/clientRegister"); // リダイレクト
                    return; // 追加処理が成功した場合はここで終了
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorMessage = "無効な利用者IDまたはスキルが指定されました"; // エラーメッセージを設定
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "利用者情報の追加中にエラーが発生しました"; // エラーメッセージを設定
            }
        } else if ("delete".equals(action)) {
            // 利用者削除処理
            String clientId = request.getParameter("clientId"); // 削除する利用者IDを取得

            // デバッグメッセージ
            System.out.println("Deleting client with ID: " + clientId);

            try {
                // 利用者情報をデータベースから削除
                dao.deleteClient(Integer.parseInt(clientId));
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "利用者情報の削除中にエラーが発生しました"; // エラーメッセージを設定
            }
        }

        // エラーメッセージがあればリクエスト属性に設定してJSPにフォワード
        if (errorMessage != null) {
            System.out.println("Error Message: " + errorMessage); // エラーメッセージのデバッグ出力
        }
        request.setAttribute("errorMessage", errorMessage);
        doGet(request, response); // GETメソッドを呼び出してフォームを表示
    }
}
