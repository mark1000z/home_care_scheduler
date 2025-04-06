package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/staffSchedule")
public class StaffScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // UserDaoのインスタンスを取得
        UserDao dao = DaoFactory.createUserDao();

        // 全ての職員スケジュールを取得して、リクエスト属性に設定
        List<StaffSchedule> schedule = dao.getAllSchedules();
        request.setAttribute("schedule", schedule);

        // JSPにフォワード
        request.getRequestDispatcher("/WEB-INF/view/staffSchedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // JSPで送信されるhiddenフィールド "scheduleId" に合わせる
        String scheduleId = request.getParameter("scheduleId");

        // scheduleIdがnullでなく空でない場合のみ処理を行う
        if (scheduleId != null && !scheduleId.isEmpty()) {
            // UserDaoのインスタンスを取得し、スケジュール削除処理を行う
            UserDao dao = DaoFactory.createUserDao();
            dao.deleteSchedule(scheduleId);
        }

        // 処理後、スケジュールページにリダイレクト
        response.sendRedirect(request.getContextPath() + "/staffSchedule");
    }
}