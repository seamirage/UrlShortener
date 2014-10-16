package configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class UrlShortenerXmlConfiguration implements UrlShortenerConfiguration {
    public UrlShortenerXmlConfiguration(String configPath) throws ConfigurationException {
        config = new XMLConfiguration(configPath);
    }

    @Override
    public String getDbUserName() {
        return config.getString("database.username");
    }

    @Override
    public String getDbPassword() {
        return config.getString("database.password");
    }

    @Override
    public String getDbUrl() {
        return config.getString("database.dbUrl");
    }

    @Override
    public String getBaseUrl() {
        return config.getString("baseUrl");
    }

    @Override
    public int getLinkLength() {
        return config.getInt("linkLength");
    }

    private XMLConfiguration config;
}
