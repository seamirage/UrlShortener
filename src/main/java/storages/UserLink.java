package storages;

public class UserLink {
    public UserLink(String originalUri, String shortLinkId, int userId) {
        this.originalUri = originalUri;
        this.shortLinkId = shortLinkId;
        this.userId = userId;
    }

    public String getOriginalUri() {
        return originalUri;
    }

    public String getShortLinkId() {
        return shortLinkId;
    }

    public int getUserId() {
        return userId;
    }

    private String originalUri;
    private String shortLinkId;
    private int userId;
}
