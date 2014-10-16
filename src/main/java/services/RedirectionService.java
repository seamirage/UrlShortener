package services;

import storages.repositories.UserLinksRepository;

import java.sql.SQLException;

public class RedirectionService {
    public RedirectionService(UserLinksRepository userLinksRepository) {
        this.userLinks = userLinksRepository;
    }

    public String GetRedirectionUrl(String requestUri) throws SQLException {
        String[] uriParts = requestUri.split("/");
        String linkId = uriParts[2];
        String originalUri = userLinks.getOriginalUri(linkId);

        return originalUri;
    }

    private UserLinksRepository userLinks;

}
