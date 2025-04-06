package controller;

// StaffScheduleクラスの実装
public class StaffSchedule {
    private String id;
    private String staffId;
    private String staffName;
    private String dayOfWeek;
    private String shift;
    private String staffSkill; // スキルをString型で追加

    public StaffSchedule(String id, String staffId, String staffName, String dayOfWeek, String shift, String staffSkill) {
        this.id = id;
        this.staffId = staffId;
        this.staffName = staffName;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
        this.staffSkill = staffSkill; // スキルをセット
    }

    public String getId() {
        return id;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getShift() {
        return shift;
    }

    public String getStaffSkill() {
        return staffSkill; // スキルのゲッターを追加
    }

    @Override
    public String toString() {
        return staffName + " (" + dayOfWeek + ", " + shift + ", Skill: " + staffSkill + ")";
    }
}