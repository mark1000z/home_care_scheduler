package controller;

public class ClientRequest { 
    private Integer id;
    private String clientId;
    private String clientName;
    private String dayOfWeek;
    private String shift;
    private String clientSkill; // スキルを追加

    // コンストラクタ
    public ClientRequest(Integer id, String clientId, String clientName, String dayOfWeek, String shift, String clientSkill) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
        this.clientSkill = clientSkill; // スキルを初期化
    }

    // ゲッターとセッター
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getClientSkill() {
        return clientSkill;
    }

    public void setClientSkill(String clientSkill) {
        this.clientSkill = clientSkill;
    }

    // 必要なスキルを取得するメソッド（getRequiredSkill）
    public String getRequiredSkill() {
        return clientSkill; // クライアントのスキルを返す
    }

    @Override
    public String toString() {
        return "ClientRequest{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", shift='" + shift + '\'' +
                ", clientSkill='" + clientSkill + '\'' +
                '}';
    }
}
