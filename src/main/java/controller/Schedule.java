package controller;

class Schedule {
    private String staffName;
    private String dayOfWeek;
    private String shift;

    public Schedule(String staffName, String dayOfWeek, String shift) {
        this.staffName = staffName;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
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
}