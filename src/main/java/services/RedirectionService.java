package services;

import storages.DatabaseException;
import storages.repositories.UserLinksRepository;

public class RedirectionService {
    public RedirectionService(UserLinksRepository userLinksRepository) {
        this.userLinks = userLinksRepository;
    }

    public String GetRedirectionUrl(String requestUri) throws DatabaseException {
        String[] uriParts = requestUri.split("/");
        String linkId = uriParts[2];
        String originalUri = userLinks.getOriginalUri(linkId);

        return originalUri;
    }

    private UserLinksRepository userLinks;

}
