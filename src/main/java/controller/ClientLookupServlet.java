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

@WebServlet("/lookupClient")
public class ClientLookupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストパラメータを取得
        String clientId = request.getParameter("clientId");
        String clientName = request.getParameter("clientName");
        String clientSkill = request.getParameter("clientSkill");

        // デバッグ: フォームから受け取った値を確認
        System.out.println("Received clientId: " + clientId);
        System.out.println("Received clientName: " + clientName);
        System.out.println("Received clientSkill: " + clientSkill);

        UserDao dao = DaoFactory.createUserDao();
        List<Client> clientList = null;

        try {
            if (clientId != null && !clientId.isEmpty()) {
                // IDで検索
                Client client = dao.findByClientId(Integer.parseInt(clientId));
                if (client != null) {
                    clientList = List.of(client); // 単一の利用者をリストに追加
                }
            } else if (clientName != null && !clientName.isEmpty()) {
                // 名前で検索
                clientList = dao.findByClientName(clientName);
            } else if (clientSkill != null && !clientSkill.isEmpty()) {
                // スキルで検索
                try {
                    int skill = Integer.parseInt(clientSkill);
                    clientList = dao.findByClientSkill(skill);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid skill specified.");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なスキルが指定されました");
                    return;
                }
            }

            // デバッグ用にclient情報を出力
            if (clientList != null && !clientList.isEmpty()) {
                for (Client client : clientList) {
                    System.out.println("Client found: ID = " + client.getClientId() + ", Name = " + client.getClientName() + ", Skill = " + client.getClientSkill());
                }
            } else {
                System.out.println("No client found for the given input.");
            }

            // レスポンスの内容をJSON形式で設定
            response.setContentType("application/json;charset=UTF-8");
            if (clientList != null && !clientList.isEmpty()) {
                StringBuilder jsonResponse = new StringBuilder("[");
                for (Client client : clientList) {
                    jsonResponse.append("{\"clientId\": \"")
                               .append(client.getClientId())
                               .append("\", \"clientName\": \"")
                               .append(client.getClientName())
                               .append("\", \"clientSkill\": \"")
                               .append(client.getClientSkill())
                               .append("\"},");
                }
                // 最後のカンマを削除して閉じる
                jsonResponse.setLength(jsonResponse.length() - 1);
                jsonResponse.append("]");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.getWriter().write("[]"); // 利用者が見つからなかった場合
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "利用者情報の取得中にエラーが発生しました");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効な利用者IDが指定されました");
        }
    }
}
