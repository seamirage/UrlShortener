package services;

import storages.repositories.UserLinksRepository;

import java.sql.SQLException;

public class RedirectionService {
    public RedirectionService(UserLinksRepository userLinksRepository) {
        this.userLinks = userLinksRepository;
    }

    public String GetRedirectionUrl(String shortLinkId) throws SQLException {
        String originalUri = userLinks.getOriginalUri(shortLinkId);

        return originalUri;
    }

    private UserLinksRepository userLinks;
}
