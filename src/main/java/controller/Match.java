package controller;

 public class Match {
    private String staffName;
    private String clientName;
    private String dayOfWeek;
    private String shift;

    public Match(String staffName, String clientName, String dayOfWeek, String shift) {
        this.staffName = staffName;
        this.clientName = clientName;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
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

