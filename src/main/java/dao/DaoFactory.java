package dao;//④ 

import javax.naming.InitialContext;
import javax.sql.DataSource;

//実装クラスを生成するクラス
public class DaoFactory {
	
	//UserDaoImplを生成するメソッド
	/*9/27コメントアウト  UserDaoImpl userDao = new UserDaoImpl(); // 引数なしのコンストラクタを呼び出す
	       userDao.setDataSource(getDataSource()); // DataSourceをセット
	       return userDao;*/
	public static UserDao createUserDao() {//9/27この２行追加
		return new UserDaoImpl(getDataSource());
 }
 //他のテーブルも作りたければ〇〇DaoImplなど追加していく
 //その他の実装クラスをDaoFactoryのここへ記述していく
 //DaoFactoryは一つだけ？
 
 //⑧DB情報     戻り値はDataSource
 private static DataSource getDataSource() {
	 //DataSourceはjavax sqlをインポート
	 DataSource ds=null;//Source型のds
	 InitialContext ctx;
	try {
		ctx = new InitialContext();
		 ds=(DataSource)ctx.lookup("java:comp/env/jdbc/home_care_services_db"); //ctxが持っているlookupで("")名前を指定していく
		
	} catch (Exception e) {//ctx = new InitialContext();の例外 NamingExceptionではなく強いExceptionにする
		
		e.printStackTrace();

	 //プロジェクトのContext xmlに書いた名前を指定する〇〇_db
	
  //オブジェクト型で戻ってくるのでDataSourceにキャストする
 
	//例外発生の可能性あるためtry-catchで囲む InitialContext ctx=new InitialContext()
 //例外発生の可能性あるためtry-catchで囲むctx.lookup("java:comp/env/jdb〇〇_db");
 
		}//最終的にはds=のdsを戻してあげる
	return ds;
	//これでgetDataSource()というメソッドを使った時にはデータベースの接続の情報を戻してくれる
 }
}
