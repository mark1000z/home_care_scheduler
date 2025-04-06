package dao;//②

import java.sql.SQLException;
import java.util.List;

import controller.Client;
import controller.ClientRequest;
import controller.MatchingResult;
import controller.Staff;
import controller.StaffSchedule;
import domain.User;

//usersテーブルを制御していく設計図
public interface UserDao {
	
	// ユーザーをデータベースに挿入するメソッド	
//やらない　ユーザー一覧取得　管理者がどんなユーザーがいるのか一覧取得をしたい時のもの
//ユーザー登録　ユーザーが新規に自分のアカウントを登録したい時
//登録するだけなので戻り値はいらない
	void insert(User user);//登録する時はユーザーの情報が必要なため引数が必要（Userのuserにしておく）
	          //Userをインポートします（domain)を選択
	
	
	//やらないユーザー更新　ユーザーが自分の情報を更新したい時
//やらない　ユーザー削除　ユーザーがアカウントを消したい時

	
	// ログインIDとパスワードでユーザーを検索するメソッド
	//ログイン認証	
	//正しいIDとパスワードの時はユーザー情報を返す
	//不正の場合はnullを返す
	//入れ物はUserこの中に正しい情報が入っていればOK,nullが入っていたらログインできない
	User findByLoginAndPass(String loginId,String loginPass);//入力が正しいかチェックするメソッドなのでログインidとパスワードを渡す
	//（String loginId,String loginPass)
	//これで設計図が出来たのでこれを実装するImplをつくる


	
	// すべてのスタッフスケジュールを取得するメソッド
	List<StaffSchedule> getAllSchedules();
	// 新しいスケジュールをデータベースに追加するメソッド
	void addSchedule(StaffSchedule schedule);//←(AddScheduleServlet処理②)UserDaoに、スケジュールをデータベースに追加するメソッドを実装する
	// 指定されたスケジュールIDのスケジュールを削除するメソッド
	void deleteSchedule(String scheduleId);//(スタッフスケジュール削除処理②)削除メソッドをUserDaoインターフェースに追加										//インターフェースにメソッドを追加	
	
	
	// すべての利用者リクエストを取得するメソッド
	 List<ClientRequest> getAllRequests();
	// 利用者リクエストを追加するメソッド
	    void addRequests(ClientRequest request);//←一行9/18お試し　テスト処理②番目
	 // 利用者リクエストを削除するメソッド    
	    void deleteRequests(String requestId);//←9/18　テスト処理　削除

	    
	//9/27コメントアウト 職員と利用者のマッチング結果を保存するメソッド
	    //９/２７コメントアウト void saveMatchingResult(String staffName, String ClientName);
	    
	    
	    //MatchingResultを受け取るようにメソッドを定義
	    boolean saveMatchingResult(MatchingResult result);//MatchingResultをインポート


		List<MatchingResult> getMatchingResults() throws Exception;//9/27追加
		
		//10/1追加
		//10/3　統合したのでコメントアウトList<MatchingResult> getAllMatchingResults()throws Exception;
		// UserDao.java
		
		
		//10/1　削除機能を追加
		void deleteMatchingResult(String matchingResultId);


		List<MatchingResult> getAllMatchedResults() throws Exception;


		void removeMatchedClients(List<String> matchedClients);



		//10/3　String getClientNameById(String clientId) throws SQLException;
		// 追加: スケジュール削除メソッド 
		// スケジュール削除メソッド
	    void deleteScheduleByStaff(String staffName, String dayOfWeek, String shift) throws SQLException;

	    // リクエスト削除メソッド
	    void deleteRequestByClient(String clientName, String dayOfWeek, String shift) throws SQLException;

	    // 職員IDで職員情報を取得
	    Staff findByStaffId(int staffId) throws SQLException;

	    // 職員名で職員情報を取得
	    List<Staff> findByStaffName(String staffName) throws SQLException;

	    // Staffオブジェクトを引数とする職員登録メソッド
	    void insertStaff(Staff staff) throws SQLException;
	    
	    
	    boolean saveMatchingResult(MatchingResult result, List<String> duplicateStaff);


			void insertClient(Client client) throws SQLException;


			Client findByClientId(int clientId)throws SQLException;
			List<Client> findByClientName(String clientName) throws SQLException;


			List<Staff> findAllStaff() throws SQLException;


			void deleteStaff(int staffId) throws SQLException;


			List<Staff> findByStaffSkill(int skill) throws SQLException;

			
			

			List<Client> findAllClient() throws SQLException;


			void deleteClient(int clientId)throws SQLException;


			List<Client> findByClientSkill(int skill) throws SQLException;


			


		



	    
	}

    


	
	

