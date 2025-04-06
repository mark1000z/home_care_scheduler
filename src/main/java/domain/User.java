package domain;//①

import lombok.AllArgsConstructor;
import lombok.Data;

//Usersテーブルの格納用DTO
@AllArgsConstructor
@Data
public class User { //データベース名はusers
//フィールド名はカラム名と合わす
//DBはスネークケース　javaはキャメルケース
private Integer id;
private String loginId; //カラム名はlogin_id
private String loginPass;//カラム名はlogin_pass
}
