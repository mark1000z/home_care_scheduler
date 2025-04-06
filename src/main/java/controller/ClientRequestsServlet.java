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

@WebServlet("/clientRequests")
public class ClientRequestsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao dao = DaoFactory.createUserDao();
        List<ClientRequest> requests = dao.getAllRequests();
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/WEB-INF/view/clientRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // JSPで送信される hidden フィールド名 "requestsId" に合わせる
        String requestId = request.getParameter("requestsId");

        if (requestId != null && !requestId.isEmpty()) {
            UserDao dao = DaoFactory.createUserDao();
            dao.deleteRequests(requestId);
        }

        // 処理が終わったら一覧ページにリダイレクト
        response.sendRedirect(request.getContextPath() + "/clientRequests");
    }
}
