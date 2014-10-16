import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import configuration.UrlShortenerConfiguration;
import configuration.UrlShortenerXmlConfiguration;
import idgeneration.AlphabetBasedLongIdToStringConverterImpl;
import idgeneration.DummyIdGeneratorImpl;
import idgeneration.SymbolsWithNumbersAlphabetImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.RedirectionService;
import services.ShortenerService;
import servlets.RedirectionServlet;
import servlets.ShortenerServlet;
import storages.connection_sources.ConnectionSource;
import storages.connection_sources.SimpleConnectionSourceImpl;
import storages.repositories.UserLinksSqlBasedRepositoryImpl;
import storages.repositories.UsersSqlBasedRepositoryImpl;

import javax.servlet.ServletContextEvent;

public class ApplicationListener extends GuiceServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new DevModule(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        serve("/shorten").with(ShortenerServlet.class);
                        serve("/*").with(RedirectionServlet.class);
                    }
                });
    }

    final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);
}

class DevModule extends AbstractModule {

    @Override
    protected void configure() {

        initializeDatabaseDriver();
        UrlShortenerConfiguration config = loadConfig();

        ConnectionSource connectionSource = new SimpleConnectionSourceImpl(config.getDbUserName(), config.getDbPassword(),config.getDbUrl());
        UserLinksSqlBasedRepositoryImpl userLinksRepository = new UserLinksSqlBasedRepositoryImpl(connectionSource);
        UsersSqlBasedRepositoryImpl usersRepository = new UsersSqlBasedRepositoryImpl(connectionSource);

        bind(RedirectionService.class).toInstance(new RedirectionService(userLinksRepository));
        bind(ShortenerService.class).toInstance(
                new ShortenerService(
                        new DummyIdGeneratorImpl(218340105584895L),
                        new AlphabetBasedLongIdToStringConverterImpl(new SymbolsWithNumbersAlphabetImpl(), config.getLinkLength()),
                        userLinksRepository,
                        config.getBaseUrl()
                )
        );
    }

    private UrlShortenerXmlConfiguration loadConfig() {
        UrlShortenerXmlConfiguration config = null;
        try {
            config = new UrlShortenerXmlConfiguration("config.xml");
        } catch (ConfigurationException e) {
            logger.error(e.getMessage());
            //TODO: stop app?
        }
        return config;
    }

    private void initializeDatabaseDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: stop application?
        }
    }

    private final Logger logger = LoggerFactory.getLogger(DevModule.class);
}
