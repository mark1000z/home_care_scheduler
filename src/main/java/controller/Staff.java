package controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // getter/setter, toString, equals, hashCode を自動生成
@NoArgsConstructor  // 引数なしのデフォルトコンストラクタを自動生成
@AllArgsConstructor // 全てのフィールドを引数に取るコンストラクタを自動生成
public class Staff {
    private int staffId;
    private String staffName;
    private int staffSkill;
}
