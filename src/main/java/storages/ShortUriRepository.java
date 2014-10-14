package storages;

public interface ShortUriRepository {
    void store(String shortUri, String originalUri, String userId);
    String getOriginalUri(String shortUri);
}
