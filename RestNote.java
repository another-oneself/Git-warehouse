package Week1Work;

import java.util.Date;

public class RestNote {
    private String IdentityId;
    private String studentName;
    private Date startTime;
    private Date endTime;
    private String reason;
    private String status;

    public RestNote(String IdentityId, String studentName, Date startTime, Date endTime, String reason, String status) {
        this.IdentityId = IdentityId;
        this.studentName = studentName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.status = status;
    }

    public String getIdentityId() {
        return IdentityId;
    }

    public void setIdentityId(String IdentityId) {
        this.IdentityId = IdentityId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
