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

@WebServlet("/schedule/add")
public class AddScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffId = request.getParameter("staffId");
        String staffName = request.getParameter("staffName");
        String[] availability = request.getParameterValues("availability");
        
        // スキルレベルをリクエストから取得
        String staffSkill = request.getParameter("staffSkill"); // スキルをStringとして取得

        System.out.println("Staff ID: " + staffId);
        System.out.println("Staff Name: " + staffName);
        System.out.println("Staff Skill: " + staffSkill); // スキルレベルのデバッグ出力
        System.out.println("Availability: " + Arrays.toString(availability));
        
        UserDao dao = DaoFactory.createUserDao();

        if (availability != null) {
            for (String timeSlot : availability) {
                String[] parts = timeSlot.split("-");
                if (parts.length == 2) {
                    String dayOfWeek = parts[0];
                    String shift = parts[1];
                    
                    // デバッグ用ログメッセージ
                    System.out.println("Adding schedule: staffId=" + staffId + ", staffName=" + staffName + 
                                       ", dayOfWeek=" + dayOfWeek + ", shift=" + shift + ", staffSkill=" + staffSkill);
                    
                    // 実際にスケジュールを追加する
                    dao.addSchedule(new StaffSchedule(null, staffId, staffName, dayOfWeek, shift, staffSkill));
                } else {
                    // 不正なフォーマットの場合
                    System.err.println("Invalid timeSlot format: " + timeSlot);
                }
            }
        } else {
            System.err.println("No availability data provided.");
        }

        response.sendRedirect(request.getContextPath() + "/staffSchedule");
    }
}