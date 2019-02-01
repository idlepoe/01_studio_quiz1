package lee.minnanoquiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User {
    private String UserId;
    private String UserName;
    private String regDateYMD;
    private Integer point;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRegDateYMD() {
        return regDateYMD;
    }

    public void setRegDateYMD(String regDateYMD) {
        this.regDateYMD = regDateYMD;
    }

    public User() {
    }



    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public User(String userId, String userName) {
        UserId = userId;
        UserName = userName;
        regDateYMD = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        point = 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", regDateYMD='" + regDateYMD + '\'' +
                ", point=" + point +
                '}';
    }
}
