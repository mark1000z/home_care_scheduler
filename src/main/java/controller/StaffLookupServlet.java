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

@WebServlet("/lookupStaff")
public class StaffLookupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストパラメータを取得
        String staffId = request.getParameter("staffId");
        String staffName = request.getParameter("staffName");
        String staffSkill = request.getParameter("staffSkill");

        // デバッグ: フォームから受け取った値を確認
        System.out.println("Received staffId: " + staffId);
        System.out.println("Received staffName: " + staffName);
        System.out.println("Received staffSkill: " + staffSkill);

        UserDao dao = DaoFactory.createUserDao();
        List<Staff> staffList = null;

        try {
            if (staffId != null && !staffId.isEmpty()) {
                // IDで検索
                Staff staff = dao.findByStaffId(Integer.parseInt(staffId));
                if (staff != null) {
                    staffList = List.of(staff); // 単一の職員をリストに追加
                }
            } else if (staffName != null && !staffName.isEmpty()) {
                // 名前で検索
                staffList = dao.findByStaffName(staffName);
            } else if (staffSkill != null && !staffSkill.isEmpty()) {
                // スキルで検索
                try {
                    int skill = Integer.parseInt(staffSkill);
                    staffList = dao.findByStaffSkill(skill);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid skill specified.");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なスキルが指定されました");
                    return;
                }
            }

            // デバッグ用にstaff情報を出力
            if (staffList != null && !staffList.isEmpty()) {
                for (Staff staff : staffList) {
                    System.out.println("Staff found: ID = " + staff.getStaffId() + ", Name = " + staff.getStaffName() + ", Skill = " + staff.getStaffSkill());
                }
            } else {
                System.out.println("No staff found for the given input.");
            }

            // レスポンスの内容をJSON形式で設定
            response.setContentType("application/json;charset=UTF-8");
            if (staffList != null && !staffList.isEmpty()) {
                StringBuilder jsonResponse = new StringBuilder("[");
                for (Staff staff : staffList) {
                    jsonResponse.append("{\"staffId\": \"")
                               .append(staff.getStaffId())
                               .append("\", \"staffName\": \"")
                               .append(staff.getStaffName())
                               .append("\", \"staffSkill\": \"")
                               .append(staff.getStaffSkill())
                               .append("\"},");
                }
                // 最後のカンマを削除して閉じる
                jsonResponse.setLength(jsonResponse.length() - 1);
                jsonResponse.append("]");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.getWriter().write("[]"); // 職員が見つからなかった場合
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "職員情報の取得中にエラーが発生しました");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効な職員IDが指定されました");
        }
    }
}
