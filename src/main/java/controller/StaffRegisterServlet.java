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

@WebServlet("/staffRegister")
public class StaffRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGetが呼ばれた");

        // DAO経由で職員リストを取得
        UserDao dao = DaoFactory.createUserDao();
        List<Staff> staffList = null;

        try {
            staffList = dao.findAllStaff(); // 全職員情報を取得
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // リストをリクエスト属性に設定
        request.setAttribute("staffList", staffList);

        // JSPにフォワード
        request.getRequestDispatcher("/WEB-INF/view/staffRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Received action: " + action); // デバッグメッセージ

        UserDao dao = DaoFactory.createUserDao();
        String errorMessage = null; // エラーメッセージを格納する変数

        if ("register".equals(action)) {
            // 職員登録処理
            String staffId = request.getParameter("staffId");
            String staffName = request.getParameter("staffName");
            String staffSkill = request.getParameter("staffSkill"); // スキルを取得
            
            // デバッグメッセージ
            System.out.println("Received staffId: " + staffId);
            System.out.println("Received staffName: " + staffName);
            System.out.println("Received staffSkill: " + staffSkill);

            try {
                // スキルの妥当性チェック
                int skill = Integer.parseInt(staffSkill);
                if (skill < 1 || skill > 3) {
                    errorMessage = "スキルは1（生活援助）、2（身体介護）、または3（両方）のいずれかでなければなりません。";
                } else {
                    // Staffオブジェクトを作成
                    Staff staff = new Staff();
                    staff.setStaffId(Integer.parseInt(staffId));
                    staff.setStaffName(staffName);
                    staff.setStaffSkill(skill); // 妥当性が確認されたスキルを設定

                    // 職員情報をデータベースに追加
                    dao.insertStaff(staff);
                    // 登録後は staffRegister サーブレットにリダイレクト
                    response.sendRedirect(request.getContextPath() + "/staffRegister"); // リダイレクト
                    return; // 追加処理が成功した場合はここで終了
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorMessage = "無効な職員IDまたはスキルが指定されました"; // エラーメッセージを設定
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "職員情報の追加中にエラーが発生しました"; // エラーメッセージを設定
            }
        } else if ("delete".equals(action)) {
            // 職員削除処理
            String staffId = request.getParameter("staffId"); // 削除する職員IDを取得

            // デバッグメッセージ
            System.out.println("Deleting staff with ID: " + staffId);

            try {
                // 職員情報をデータベースから削除
                dao.deleteStaff(Integer.parseInt(staffId));
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "職員情報の削除中にエラーが発生しました"; // エラーメッセージを設定
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
