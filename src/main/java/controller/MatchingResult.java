package controller;

public class MatchingResult {
  private String id; // IDを追加
  private String staffName;
  private String clientName;
  private String dayOfWeek;
  private String shift;

  // IDを引数に取るコンストラクタ
  public MatchingResult(String id, String staffName, String clientName, String dayOfWeek, String shift) {
      this.id = id;
      this.staffName = staffName;
      this.clientName = clientName;
      this.dayOfWeek = dayOfWeek;
      this.shift = shift;
  }

  // 既存のコンストラクタ（必要に応じて保持）
  public MatchingResult(String staffName, String clientName, String dayOfWeek, String shift) {
      this.staffName = staffName;
      this.clientName = clientName;
      this.dayOfWeek = dayOfWeek;
      this.shift = shift;
  }

  // ゲッターとセッター（必要に応じて追加）
  public String getId() {
      return id;
  }

  public String getStaffName() {
      return staffName;
  }

  public String getClientName() {
      return clientName;
  }

  public String getDayOfWeek() {
      return dayOfWeek;
  }

  public String getShift() {
      return shift;
  }
}
