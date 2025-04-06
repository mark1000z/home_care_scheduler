package dao;//③

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import controller.Client;
import controller.ClientRequest;
import controller.MatchingResult;
import controller.Staff;
import controller.StaffSchedule;
import domain.User;

//usersテーブルを制御する実装クラス
public class UserDaoImpl implements UserDao{
//implements UserDaoと記載して設計図を読み込む
	//実装されていないメソッドの追加で↓＠Overrideが記載される

	//⑩DaoFactoryの⑨を受け取る
	//dsをいろいろなところで使いまわしたいためにprivate DataSource ds;を作る
	//insert　findByLoginAndPass　update でも使いたいためいったんここで作る
	//⑪dsに中身を入れるやり方はUserDaoImplをnewしたときに渡ってきた⑨のgetDataSource()を
	//コンストラクタを使って受け取ってdsに入れる
	private DataSource ds;//⑩
	
	
	// コンストラクタでDataSourceを注入
	public UserDaoImpl(DataSource ds) {//⑪渡ってくるのはDetaSource ds型なのでそれを受け取る
		this.ds=ds;//ds=ds;だとDataSource dsもdsで（DataSource ds）もdsこの書き方だとds=ds;は両方（DataSource ds）のdsをみる
	//↑このdsはUserDaoImplのDataSource dsのdsの意味になりそこに代入することになる
	//渡ってきたデータベースのデータをdsに入れてあげる⑫へ	
	}
	//javax SQLをインポート
	
	@Override//ログイン用executeUpdate
	public void insert(User user) {//⑭
		//SQLの準備
		String sql="insert into users_login(login_id,login_pass)values(?,?)";//usersテーブル　会員番号のIdはいらないのでカラム指定をしていく(login_id,login_pass)
		//valuesには(login_id,login_pass)の２つを渡すので(?,?)
		//login_passは？にそのまま入れてはパスワードはダメハッシュ化する
		String loginPass=user.getLoginPass();//userの中からパスワードを引っこ抜く
		String hashed=//これ↓をStringで受け取る
				BCrypt.hashpw(loginPass, BCrypt.gensalt());//第1引数にハッシュ化したいパスワードを設定、第２引数にBCryptが持っているgensalt
	try(Connection con=ds.getConnection()){
		PreparedStatement stmt=con.prepareStatement(sql);//受け取る　ここまでだと上のSQLが不完全な状態
		stmt.setString(1, user.getLoginId());//なので値を入れていく
		stmt.setString(2, hashed);//ハッシュ化されたパスワード
		stmt.executeUpdate();//実行はexecuteUpdate
		
		}catch(Exception e){
		e.getStackTrace();//何かエラーがあったときにエラーメッセージを出して
		}
	
	}

	@Override//ログインパス用
	public User findByLoginAndPass(String loginId, String loginPass) {
		// ⑫SQLの用意とtry-catch=?
		String sql="select * from users_login where login_id= ?";//文字列だと悪意のあるSQLを入れられる可能性があるためプリペアードステートメント？を記述
		try(Connection con=ds.getConnection()){//()内に接続情報dsが持っているDataSource ds
			//try-with-resoucesのlogin_id書き方の為使い終わったら自動的にconがcloseしてくれる、この書き方でなければfinallyで自分で閉じる
		//⑬login_idログインidが合っているかを調べる
		//データベースのidが合っていたら今度はレコードごと取得してパスワードを調べる
			PreparedStatement stmt= con.prepareStatement(sql);//⑬SQLの準備
			stmt.setString(1, loginId);//SQLにパラメータをセット　?にデータを入れてあげる１番にはlogin_Id
			//SQLの実行 stmt.executeQuery();には戻り値がある為ResultSetで受け取る
			ResultSet rs=stmt.executeQuery();
			if(rs.next()){//nextがtrueならログインIdが正しいことになる
				//login_idがヒットしたのでResultSet rsのlogin_passを調べる
				if(BCrypt.checkpw(loginPass, rs.getString("login_pass"))) {//データベースに登録されているlogin_passはハッシュ化されているuserはそのままのパスを入力それを調べるのにBCryptを使う
			//引数は(userが入力したパスワード,ハッシュ化されたパスワード（それはrsの中にある））		
			//合っていればtrue 間違っていればfalse
			//login_passもヒットしたのでユーザー情報を返す		
					return new User(rs.getInt("id"),loginId,null);//合っていたらユーザー情報を格納して戻してあげる、パスワードも戻ってくるけど流出が心配なのでUser.javaにフィールドはあるが保存しないでnull
			//loginIdは通ったがパスが間違っていることもある為
				  }
					return null;//パスワードが間違っているのでnullで返す
					}
				return null;//ログインIDすらも間違っていたらnullで返す→servletへ行く
				//成功していればServletのresponse.sendRedirect(request.getContextPath() + "/item/list");//ここではitemの中のlistを会員専用ページとするへ飛ぶ
				
				
		}catch(Exception e) {//catchはException
			
		}
		return null;
	}
	 @Override //staff_schedulesテーブルから全てのスタッフのスケジュールを取得executeQuery
	    public List<StaffSchedule> getAllSchedules() {//(AddScheduleServlet処理③)実装クラスにメソッドを追加
	        List<StaffSchedule> schedules = new ArrayList<>();
	        String sql = "SELECT * FROM staff_schedules";
	        
	        try (Connection con = ds.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                StaffSchedule schedule = new StaffSchedule(
	                	rs.getString("id"),	//9/19削除ボタンを押すと同じ職員IDの人も削除されてしまったのでこの行を追加
	                    rs.getString("staff_id"),
	                    rs.getString("staff_name"),
	                    rs.getString("day_of_week"),
	                    rs.getString("shift"),
	                    rs.getString("staff_skill")
	                );
	                schedules.add(schedule);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return schedules;
	    }
	 
	 
	 	//スタッフのスケジュールをstaff_schedulesテーブルに追加executeUpdate
	    // (AddScheduleServlet処理③)実装クラスにメソッドを追加
	 @Override 
	 public void addSchedule(StaffSchedule schedule) {
	     // 修正: staff_skill の後のカンマを削除
	     String sql = "INSERT INTO staff_schedules (staff_id, staff_name, day_of_week, shift, staff_skill) VALUES (?, ?, ?, ?, ?)";
	      
	     try (Connection con = ds.getConnection();
	          PreparedStatement stmt = con.prepareStatement(sql)) {
	         stmt.setString(1, schedule.getStaffId());
	         stmt.setString(2, schedule.getStaffName());
	         stmt.setString(3, schedule.getDayOfWeek());
	         stmt.setString(4, schedule.getShift());
	         stmt.setString(5, schedule.getStaffSkill());
	         
	         stmt.executeUpdate();
	     } catch (Exception e) {
	         System.err.println("Error occurred while adding schedule for Staff ID: " + schedule.getStaffId() +  
	                            ", Name: " + schedule.getStaffName() + ", Skill: " + schedule.getStaffSkill() + 
	                            ", Day: " + schedule.getDayOfWeek() + ", Shift: " + schedule.getShift());
	         e.printStackTrace();
	     }
	 }

	    
	   //client_requestsテーブルから全てのクライアントのリクエストを取得 executeQuery
	  //↓一行9/18お試し　テスト処理③番目    
	 @Override 
	 public List<ClientRequest> getAllRequests() {
	     List<ClientRequest> requests = new ArrayList<>();
	     String sql = "SELECT * FROM client_requests";
	     try (Connection con = ds.getConnection();
	          PreparedStatement stmt = con.prepareStatement(sql);
	          ResultSet rs = stmt.executeQuery()) {
	         while (rs.next()) {
	             ClientRequest request = new ClientRequest(
	                 rs.getInt("id"), // Integer型のidを取得
	                 rs.getString("client_id"),
	                 rs.getString("client_name"),
	                 rs.getString("day_of_week"),
	                 rs.getString("shift"),
	                 rs.getString("client_skill")
	             );
	             requests.add(request);
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     
	     return requests;
	 }
	    
	   //クライアントのリクエストをclient_requestsテーブルに追加 executeUpdate
	  //↓一行9/18お試し　テスト処理④番目public class UserDaoImpl implements UserDaoのUserDaoImplに赤線が
	    @Override
	    public void addRequests(ClientRequest request) {
	        String sql = "INSERT INTO client_requests (client_id, client_name, day_of_week, shift,client_skill) VALUES (?, ?, ?, ?, ?)";
	        try (Connection con = ds.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, request.getClientId());
	            stmt.setString(2, request.getClientName());
	            stmt.setString(3, request.getDayOfWeek());
	            stmt.setString(4, request.getShift());
	            stmt.setString(5, request.getClientSkill());
	            stmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	    
	    
	   
	    
	    }
	    
	    
	    
	    
	    //指定されたidのスケジュールを削除executeUpdate
	    
		@Override                             //削除対象の識別子をidに変更
		//public void deleteSchedule(String scheduleId) {//(スタッフスケジュール削除処理③)public class UserDaoImpl implements UserDaoのUserDaoImplに赤線が
			public void deleteSchedule(String id) {	//9/20テスト中
			
			
			//入るので実装されていないメソッドの追加を押す
			 //String sql = "DELETE FROM staff_schedules WHERE staff_id = ?";9/19これだとstaff_idが一致するすべての行を削除してしまうので職員IDが同じ他の職員も削除されてしまうので修正
			String sql = "DELETE FROM staff_schedules WHERE id = ?";

		        try (Connection con = ds.getConnection();
		             PreparedStatement stmt = con.prepareStatement(sql)) {
		           // stmt.setString(1, scheduleId);
		        	stmt.setString(1, id); //9/20テスト中
		        	stmt.executeUpdate();
		        } catch (Exception e) {
		            e.printStackTrace();	
			
		}
	}

		@Override                              //削除対象の識別子をidに変更executeUpdate
		public void deleteRequests(String id) {//←一意の識別子idに変更と　9/18テスト処理　削除の追加
			 String sql = "DELETE FROM client_requests WHERE id = ?";//9/19削除ボタンが押せなかったためにclient_requestsをrequestsに変更client_requestsテーブルからidをもとに削除するSQL分　　　　　一意の識別子idに変更　
		        try (Connection con = ds.getConnection();
		             PreparedStatement stmt = con.prepareStatement(sql)) {
		            stmt.setString(1, id);//←一意の識別子idに変更
		            stmt.executeUpdate();
		        } catch (Exception e) {
		            e.printStackTrace();
			
		}
		
		
		
		}
		
		@Override
		public boolean saveMatchingResult(MatchingResult result) {
		    // 既存のマッチング結果を確認するSQLクエリ
		    String checkSql = "SELECT COUNT(*) FROM matching_results WHERE staff_name = ? AND client_name = ? AND day_of_week = ? AND shift = ?";
		    String insertSql = "INSERT INTO matching_results (staff_name, client_name, day_of_week, shift) VALUES (?, ?, ?, ?)";

		    try (Connection con = ds.getConnection();
		         PreparedStatement checkStmt = con.prepareStatement(checkSql);
		         PreparedStatement insertStmt = con.prepareStatement(insertSql)) {

		        // 既存のマッチング結果をチェック
		        checkStmt.setString(1, result.getStaffName());
		        checkStmt.setString(2, result.getClientName());
		        checkStmt.setString(3, result.getDayOfWeek());
		        checkStmt.setString(4, result.getShift());

		        ResultSet rs = checkStmt.executeQuery();
		        if (rs.next() && rs.getInt(1) > 0) {
		            // 既に存在する場合は false を返す
		            return false;
		        }

		        // マッチング結果を挿入
		        insertStmt.setString(1, result.getStaffName());
		        insertStmt.setString(2, result.getClientName());
		        insertStmt.setString(3, result.getDayOfWeek());
		        insertStmt.setString(4, result.getShift());
		        insertStmt.executeUpdate();

		        // 成功した場合は true を返す
		        return true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Update失敗してるよ～", e); // SQLExceptionをRuntimeExceptionとしてスロー
		    }
		}


			
		    //10/1追加　executeUpdate
		 @Override
		 public void deleteMatchingResult(String matchingResultId) { 
		     String sql = "DELETE FROM matching_results WHERE id = ?"; // 修正済み

		     try (Connection con = ds.getConnection();
		          PreparedStatement preparedStatement = con.prepareStatement(sql)) {
		         preparedStatement.setString(1, matchingResultId);
		         preparedStatement.executeUpdate();
		     } catch (SQLException e) {
		         e.printStackTrace();
		         throw new RuntimeException("削除処理に失敗しました", e);  // RuntimeExceptionをスロー
		     }
		 }



		    //10/3　getMatchingResults を getAllMatchingResults　エラーハンドリング追加バージョン	
		 @Override
		 public List<MatchingResult> getMatchingResults() throws Exception {
		     List<MatchingResult> results = new ArrayList<>();
		     String sql = "SELECT id, staff_name, client_name, day_of_week, shift FROM matching_results"; // idを取得

		     try (Connection con = ds.getConnection();
		          PreparedStatement stmt = con.prepareStatement(sql);
		          ResultSet rs = stmt.executeQuery()) {
		         while (rs.next()) {
		             String id = rs.getString("id"); // idを取得
		             String staffName = rs.getString("staff_name");
		             String clientName = rs.getString("client_name");
		             String dayOfWeek = rs.getString("day_of_week");
		             String shift = rs.getString("shift");
		             results.add(new MatchingResult(id, staffName, clientName, dayOfWeek, shift)); // idを追加
		         }
		     } catch (SQLException e) {
		         e.printStackTrace();
		         throw new Exception("マッチング結果の取得に失敗しました", e);
		     }
		     return results;
		 }



		 @Override 
		 public List<MatchingResult> getAllMatchedResults() {
		     List<MatchingResult> matchedResults = new ArrayList<>();
		     String sql = "SELECT id, staff_name, client_name, day_of_week, shift FROM matching_results"; // idを追加

		     try (Connection con = ds.getConnection();
		          PreparedStatement stmt = con.prepareStatement(sql);
		          ResultSet rs = stmt.executeQuery()) {
		         while (rs.next()) {
		             String id = rs.getString("id"); // idを取得
		             String staffName = rs.getString("staff_name");
		             String clientName = rs.getString("client_name");
		             String dayOfWeek = rs.getString("day_of_week");
		             String shift = rs.getString("shift");
		             matchedResults.add(new MatchingResult(id, staffName, clientName, dayOfWeek, shift)); // idを追加
		         }
		     } catch (SQLException e) {
		         e.printStackTrace();
		         throw new RuntimeException("マッチング結果の取得に失敗しました", e);
		     }
		     return matchedResults;
		 }

@Override
public void removeMatchedClients(List<String> matchedClients) {
	String sql = "UPDATE client_requests SET matched = false WHERE client_name IN (?)";
  try (Connection con = ds.getConnection();
       PreparedStatement stmt = con.prepareStatement(sql)) {
      for (String clientName : matchedClients) {
          stmt.setString(1, clientName);
          stmt.addBatch(); // バッチ処理で効率的に実行
      }
      stmt.executeBatch();
  } catch (Exception e) {
      e.printStackTrace();
	
}
	
}

// 職員名とスケジュールに基づいてスケジュールを削除
// 職員名とスケジュールに基づいてスケジュールを削除
@Override
public void deleteScheduleByStaff(String staffName, String dayOfWeek, String shift) throws SQLException {
    String sql = "DELETE FROM staff_schedules WHERE staff_name = ? AND day_of_week = ? AND shift = ?";

    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setString(1, staffName);
        stmt.setString(2, dayOfWeek);
        stmt.setString(3, shift);
        stmt.executeUpdate(); // スケジュール削除
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("職員スケジュールの削除中にエラーが発生しました", e);
    }
}

// クライアント名とスケジュールに基づいてリクエストを削除
@Override
public void deleteRequestByClient(String clientName, String dayOfWeek, String shift) throws SQLException {
    String sql = "DELETE FROM client_requests WHERE client_name = ? AND day_of_week = ? AND shift = ?";

    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setString(1, clientName);
        stmt.setString(2, dayOfWeek);
        stmt.setString(3, shift);
        stmt.executeUpdate(); // リクエスト削除
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("クライアントリクエストの削除中にエラーが発生しました", e);
    }
}



@Override
public Staff findByStaffId(int staffId) throws SQLException {
    String sql = "SELECT * FROM staff WHERE staff_id = ?";
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, staffId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffName(rs.getString("staff_name"));
                staff.setStaffSkill(rs.getInt("staff_skill"));//10/16スキル追加
                return staff;
            }
        }
    }
    return null;
}



//10/11追加　上記のを変更
@Override
public List<Staff> findByStaffName(String staffName) throws SQLException {
    String sql = "SELECT * FROM staff WHERE staff_name = ?";
    List<Staff> staffList = new ArrayList<>(); // スタッフのリストを初期化

    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setString(1, staffName);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffName(rs.getString("staff_name"));
                staff.setStaffSkill(rs.getInt("staff_skill"));
                staffList.add(staff); // リストに追加
            }
        }
    }
    return staffList; // 一致するすべてのスタッフのリストを返す
}











@Override
public void insertStaff(Staff staff) throws SQLException {
    String sql = "INSERT INTO staff (staff_id, staff_name, staff_skill) VALUES (?, ? ,?)";
    
    try (Connection con = ds.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, staff.getStaffId());
        stmt.setString(2, staff.getStaffName());
        stmt.setInt(3, staff.getStaffSkill());
        stmt.executeUpdate(); 
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
@Override
public boolean saveMatchingResult(MatchingResult result, List<String> duplicateStaff) {
    String checkSql = "SELECT COUNT(*) FROM matching_results WHERE staff_name = ? AND day_of_week = ? AND shift = ?";
    String insertSql = "INSERT INTO matching_results (staff_name, client_name, day_of_week, shift) VALUES (?, ?, ?, ?)";

    try (Connection con = ds.getConnection();
         PreparedStatement checkStmt = con.prepareStatement(checkSql);
         PreparedStatement insertStmt = con.prepareStatement(insertSql)) {

        // 既存レコードを確認
        checkStmt.setString(1, result.getStaffName());
        checkStmt.setString(2, result.getDayOfWeek());
        checkStmt.setString(3, result.getShift());

        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count > 0) {
            // 既に存在する場合
            duplicateStaff.add(result.getStaffName()); // 重複をリストに追加
            System.out.println("既に存在するため、挿入しません: " + result);
        } else {
            // 新規に挿入
            insertStmt.setString(1, result.getStaffName());
            insertStmt.setString(2, result.getClientName());
            insertStmt.setString(3, result.getDayOfWeek());
            insertStmt.setString(4, result.getShift());
            insertStmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Update失敗してるよ～", e);
    }
	return false;
}

@Override
public void insertClient(Client client) throws SQLException {
	String sql = "INSERT INTO client (client_id, client_name, client_skill) VALUES (?, ?, ?)";
  
  try (Connection con = ds.getConnection(); 
       PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, client.getClientId());
      stmt.setString(2, client.getClientName());
      stmt.setInt(3, client.getClientSkill());
      stmt.executeUpdate(); 
  } catch (SQLException e) {
      e.printStackTrace();
      throw e;
  }
}

@Override
public Client findByClientId(int clientId) throws SQLException {
	String sql = "SELECT * FROM client WHERE client_id = ?";
  try (Connection con = ds.getConnection();
       PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setInt(1, clientId);
      try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
              Client client = new Client();
              client.setClientId(rs.getInt("client_id"));
              client.setClientName(rs.getString("client_name"));
              client.setClientSkill(rs.getInt("client_skill"));
              return client;
          }
      }
  }
  return null;
}

@Override
public List<Client> findByClientName(String clientName) throws SQLException {
	String sql = "SELECT * FROM client WHERE client_name = ?";
  List<Client> clientList = new ArrayList<>(); // スタッフのリストを初期化

  try (Connection con = ds.getConnection();
       PreparedStatement stmt = con.prepareStatement(sql)) {
      stmt.setString(1, clientName);
      try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
              Client client = new Client();
              client.setClientId(rs.getInt("client_id"));
              client.setClientName(rs.getString("client_name"));
              client.setClientSkill(rs.getInt("client_skill"));
              clientList.add(client); // リストに追加
          }
      }
  }
  return clientList; // 一致するすべてのスタッフのリストを返す
}

@Override
public List<Staff> findAllStaff() throws SQLException {
    List<Staff> staffList = new ArrayList<>();
    String sql = "SELECT staff_id, staff_name, staff_skill FROM staff";

    try (Connection con =ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Staff staff = new Staff();
            staff.setStaffId(rs.getInt("staff_id"));
            staff.setStaffName(rs.getString("staff_name"));
            staff.setStaffSkill(rs.getInt("staff_skill"));
            staffList.add(staff);
        }
    }
    return staffList;
}


public void deleteStaff(int staffId) throws SQLException {
    String sql = "DELETE FROM staff WHERE staff_id = ?";

    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, staffId);
        stmt.executeUpdate();
    }
}

@Override 
public List<Staff> findByStaffSkill(int skill) throws SQLException {
    List<Staff> staffList = new ArrayList<>();
    String sql = "SELECT * FROM staff WHERE staff_skill = ?";  // スキルを条件に検索
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, skill);  // クエリにスキルをバインド
        try (ResultSet rs = stmt.executeQuery()) { // 修正：ここでクエリを実行
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setStaffName(rs.getString("staff_name"));
                staff.setStaffSkill(rs.getInt("staff_skill"));
                staffList.add(staff);  // 結果をリストに追加
            }
        }
    }
    return staffList;
}

@Override
public List<Client> findAllClient() throws SQLException {
	 List<Client> clientList = new ArrayList<>();
   String sql = "SELECT client_id, client_name, client_skill FROM client";

   try (Connection con =ds.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

       while (rs.next()) {
      	 Client client = new Client();
      	 client.setClientId(rs.getInt("client_id"));
      	 client.setClientName(rs.getString("client_name"));
      	 client.setClientSkill(rs.getInt("client_skill"));
      	 clientList.add(client);
       }
   }
   return clientList;
}


@Override
public void deleteClient(int clientId) throws SQLException {
	String sql = "DELETE FROM client WHERE client_id = ?";

  try (Connection con = ds.getConnection();
       PreparedStatement stmt = con.prepareStatement(sql)) {

      stmt.setInt(1, clientId);
      stmt.executeUpdate();
  }
}

@Override
public List<Client> findByClientSkill(int skill) throws SQLException {
	 List<Client> clientList = new ArrayList<>();
   String sql = "SELECT * FROM client WHERE client_skill = ?";  // スキルを条件に検索
   try (Connection con = ds.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)) {
       stmt.setInt(1, skill);  // クエリにスキルをバインド
       try (ResultSet rs = stmt.executeQuery()) { // 修正：ここでクエリを実行
           while (rs.next()) {
          	 Client client = new Client();
          	 client.setClientId(rs.getInt("client_id"));
          	 client.setClientName(rs.getString("client_name"));
          	 client.setClientSkill(rs.getInt("client_skill"));
          	 clientList.add(client);  // 結果をリストに追加
           }
       }
   }
   return clientList;
}}