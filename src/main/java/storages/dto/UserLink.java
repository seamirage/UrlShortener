package storages.dto;

public class UserLink {
    public UserLink(String originalUri, String shortLinkId, String userId) {
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

    public String getUserId() {
        return userId;
    }

    private String originalUri;
    private String shortLinkId;
    private String userId;
}
