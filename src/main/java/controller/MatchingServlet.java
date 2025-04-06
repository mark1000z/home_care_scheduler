package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/match")
public class MatchingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDao userDao = DaoFactory.createUserDao();

            // 全職員と全利用者を取得
            List<StaffSchedule> staffSchedules = userDao.getAllSchedules();
            List<ClientRequest> clientRequests = userDao.getAllRequests();

            // マッチング結果を保持するマップ
            Map<StaffSchedule, List<ClientRequest>> matchMap = new HashMap<>();

            // 全職員と利用者を最初は未マッチリストに保持
            List<StaffSchedule> unmatchedStaff = new ArrayList<>(staffSchedules);
            List<ClientRequest> unmatchedClients = new ArrayList<>(clientRequests);

            // マッチングを行う
            // 各職員に対して、全利用者のリクエストと曜日、シフト、スキルの一致をチェック
            // 一致する利用者がいた場合、matchMapに職員とその利用者のリストを追加
            for (StaffSchedule staff : staffSchedules) {
                List<ClientRequest> matchedClients = new ArrayList<>();
                for (ClientRequest client : clientRequests) {
                    // 曜日、シフト、スキルが一致するかどうかでマッチング
                    if (staff.getDayOfWeek().equals(client.getDayOfWeek()) 
                        && staff.getShift().equals(client.getShift())
                        && isSkillMatched(Integer.parseInt(staff.getStaffSkill()), Integer.parseInt(client.getRequiredSkill()))) {
                        matchedClients.add(client);
                    }
                }
                if (!matchedClients.isEmpty()) {
                    matchMap.put(staff, matchedClients);
                }
            }

            // マッチング結果、未マッチ職員、未マッチ利用者をリクエストにセット
            // この後、JSPにデータを渡すために使用
            request.setAttribute("matchMap", matchMap);
            request.setAttribute("unmatchedStaff", unmatchedStaff);
            request.setAttribute("unmatchedClients", unmatchedClients);
            // マッチング結果を表示する役割
            request.getRequestDispatcher("/WEB-INF/view/matchSelection.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベース接続中にエラーが発生しました");
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }

    // 職員のスキルが利用者の要求スキルに一致するかどうかをチェックするメソッド
    private boolean isSkillMatched(int staffSkill, int requiredSkill) {
    	// 利用者がスキル1を要求している場合、職員のスキルが1または3であればtrue
    	// スキル1の職員、またはスキル3（上級スキル）を持つ職員がいればマッチと見なされる
        return (requiredSkill == 1 && (staffSkill == 1 || staffSkill == 3)) ||
        // 利用者がスキル2を要求している場合、職員のスキルが2または3であればtrue
        // スキル2の職員、またはスキル3の職員がいればマッチと見なされる		
               (requiredSkill == 2 && (staffSkill == 2 || staffSkill == 3)) ||
        // 利用者がスキル3を要求している場合、職員のスキルが3であればtrue
        // スキル3（両方のスキル）を持つ職員のみがマッチと見なされる     
               (requiredSkill == 3 && staffSkill == 3);
    }

    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // フォームから送信された行数を取得（不整合の検出）
            int rowCount = Integer.parseInt(request.getParameter("rowCount"));

            // UserDaoを取得
            UserDao userDao = DaoFactory.createUserDao();

            // 重複(duplicate)職員名を保持するリストを作成
            List<String> duplicateStaff = new ArrayList<>();
            
            // 同じ利用者が選択されているかどうかをトラッキングするマップ
            Map<String, String> selectedClientsMap = new HashMap<>(); // 利用者名をキーに、職員名を値にする

            // 各行のデータを処理
            // request.getParameter("parameterName") メソッドを使用して、フォームから送信されたデータを取得
            // rowCountに基づいて繰り返す 
            for (int i = 0; i < rowCount; i++) {
                String staffName = request.getParameter("staffName_" + i);
                String clientName = request.getParameter("clientName_" + i);
                String dayOfWeek = request.getParameter("dayOfWeek_" + i);
                String shift = request.getParameter("shift_" + i);

                // デバッグ出力
                System.out.println("Selected Staff Name: " + staffName);
                System.out.println("Selected Client Name: " + clientName);
                System.out.println("Day of Week: " + dayOfWeek);
                System.out.println("Shift: " + shift);

                // エラーチェック（空の場合はスキップ）
                if (staffName == null || staffName.isEmpty() || clientName == null || clientName.isEmpty()) {
                    continue;
                }

                // 利用者ごとの重複を曜日・シフトごとにチェック
                //  "_"は文字列を結合する時に使う区切り文字clientName,dayOfWeek,shiftの３つを結合
                // keyはこの３つを組み合わせた一意の識別しとして機能する
                // selectedClientsMap というマップにこの key が存在するかをチェックして重複を避ける
                String key = clientName + "_" + dayOfWeek + "_" + shift;
                if (selectedClientsMap.containsKey(key)) {
                    duplicateStaff.add(staffName); // 重複する職員を追加
                } else {
                    selectedClientsMap.put(key, staffName); // 利用者を選択済みとして追加
                }
            }

            // 重複があった場合の処理
            if (!duplicateStaff.isEmpty()) {
                // エラーメッセージをリクエストに設定する
                request.setAttribute("duplicateStaff", duplicateStaff);
                request.setAttribute("errorMessage", "選択された利用者が重複しています。再選択してください。");
                // もう一度マッチング画面に戻る
                doGet(request, response); // マッチング画面を再表示
                return; // 以降の処理を中断
            }

            // 重複がなかった場合はマッチング結果を保存
            for (int i = 0; i < rowCount; i++) {
                String staffName = request.getParameter("staffName_" + i);
                String clientName = request.getParameter("clientName_" + i);
                String dayOfWeek = request.getParameter("dayOfWeek_" + i);
                String shift = request.getParameter("shift_" + i);

                // 再度エラーチェック（空の場合はスキップ）
                if (staffName == null || staffName.isEmpty() || clientName == null || clientName.isEmpty()) {
                    continue;
                }

                // マッチング結果を保存
                MatchingResult result = new MatchingResult(staffName, clientName, dayOfWeek, shift);
                boolean isSaved = userDao.saveMatchingResult(result); // 重複職員リストを引数に渡す必要がある場合は、修正が必要です

                // 既存のマッチングを再確認するためのロジックを追加
                // if (!isSaved) の条件文は、マッチング結果の保存に失敗した場合に実行
                if (!isSaved) {
                    System.out.println("既に存在するため、挿入しません: " + result);
                    duplicateStaff.add(staffName); // 重複職員名を追加
                    request.setAttribute("errorMessage", "選択された利用者が既にマッチングされています。"); // エラーメッセージを設定
                    doGet(request, response); // マッチング画面を再表示
                    return; // 以降の処理を中断
                } else {
                    // マッチング成功後、職員スケジュールと利用者リクエストを削除
                    userDao.deleteScheduleByStaff(staffName, dayOfWeek, shift);
                    userDao.deleteRequestByClient(clientName, dayOfWeek, shift);
                }
            }

            // 成功したらリダイレクト
            response.sendRedirect(request.getContextPath() + "/selectClient");
        } catch (Exception e) {
            e.printStackTrace(); // スタックトレースを出力
            request.setAttribute("errorMessage", "エラーが発生しました。再試行してください");
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}