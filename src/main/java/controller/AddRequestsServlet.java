package controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/requests/add")
public class AddRequestsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientId = request.getParameter("clientId");
        String clientName = request.getParameter("clientName");
        String[] availability = request.getParameterValues("availability");
        
        // スキルレベルをリクエストから取得
        String clientSkill = request.getParameter("clientSkill"); // スキルをStringとして取得

        System.out.println("Client ID: " + clientId);
        System.out.println("Client Name: " + clientName);
        System.out.println("Client Skill: " + clientSkill); // スキルレベルのデバッグ出力
        System.out.println("Availability: " + Arrays.toString(availability));
        
        UserDao dao = DaoFactory.createUserDao();

        if (availability != null) {
            for (String timeSlot : availability) {
                String[] parts = timeSlot.split("-");
                if (parts.length == 2) {
                    String dayOfWeek = parts[0];
                    String shift = parts[1];
                    
                    // デバッグ用ログメッセージ
                    System.out.println("Adding client request: clientId=" + clientId + ", clientName=" + clientName + 
                                       ", dayOfWeek=" + dayOfWeek + ", shift=" + shift + ", clientSkill=" + clientSkill);
                    
                    // 実際にリクエストを追加する
                    dao.addRequests(new ClientRequest(null, clientId, clientName, dayOfWeek, shift, clientSkill));
                } else {
                    // 不正なフォーマットの場合
                    System.err.println("Invalid timeSlot format: " + timeSlot);
                }
            }
        } else {
            System.err.println("No availability data provided.");
        }

        response.sendRedirect(request.getContextPath() + "/clientRequests");
    }
}
