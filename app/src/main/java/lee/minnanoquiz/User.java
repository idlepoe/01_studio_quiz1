package lee.minnanoquiz;

public class User {
    private String UserId;
    private String UserName;
    private String regDateYMD;

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

    public User(String userId, String userName) {
        UserId = userId;
        UserName = userName;
    }
}
