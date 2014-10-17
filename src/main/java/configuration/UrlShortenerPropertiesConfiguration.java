package configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UrlShortenerPropertiesConfiguration implements UrlShortenerConfiguration {
    public UrlShortenerPropertiesConfiguration(String configPath) throws IOException {
        properties = new Properties();
        InputStream config = this.getClass().getResourceAsStream(configPath);
        if (null != config) {
            properties.load(config);
        } else {
           throw new FileNotFoundException("File " + configPath + " was not found");
        }
    }

    @Override
    public String getDbUserName() {
        return properties.getProperty("dbUserName");
    }

    @Override
    public String getDbPassword() {
        return properties.getProperty("dbPassword");
    }

    @Override
    public String getDbUrl() {
        return properties.getProperty("dbUrl");
    }

    @Override
    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    @Override
    public int getLinkLength() {
        return Integer.parseInt(properties.getProperty("linkLength"));
    }

    @Override
    public int getMaxLengthOfOriginalUri() {
        return Integer.parseInt(properties.getProperty("maxLengthOfOriginalUri"));
    }

    @Override
    public String getBaseUrlWithoutProtocol() {
        return properties.getProperty("baseUrlWithoutProtocol");
    }

    private Properties properties;
    private final Logger log = LoggerFactory.getLogger(UrlShortenerPropertiesConfiguration.class);
}