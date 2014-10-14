package services;

public interface UriShortenerService {
    String shortenAndStore(String originalUri, String userId);
    String getOriginalUri(String shortenedUri);
}