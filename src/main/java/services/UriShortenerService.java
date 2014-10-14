package services;

import java.sql.SQLException;

public interface UriShortenerService {
    String shortenAndStore(String originalUri, String userId);
    String getOriginalUri(String shortenedUri) throws SQLException;
}