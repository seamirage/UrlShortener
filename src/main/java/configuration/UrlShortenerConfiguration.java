package configuration;

public interface UrlShortenerConfiguration {
    String getDbUserName();

    String getDbPassword();

    String getDbUrl();

    String getBaseUrl();

    int getLinkLength();
}
