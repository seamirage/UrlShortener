package storages.dto;

public class UserInfo {
    public UserInfo(String googleIdentity) {
        this.googleIdentity = googleIdentity;
    }

    public UserInfo(String userId, String googleIdentity) {
        this.userId = userId;
        this.googleIdentity = googleIdentity;
    }

    public String getUserId() {
        return userId;
    }

    public String getGoogleIdentity() {
        return googleIdentity;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    private String googleIdentity;
}
