import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import configuration.UrlShortenerConfiguration;
import configuration.UrlShortenerPropertiesConfiguration;
import filters.IsAuthorizedFilter;
import idgeneration.AlphabetBasedLongIdToStringConverterImpl;
import idgeneration.DummyIdGeneratorImpl;
import idgeneration.SymbolsWithNumbersAlphabetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.RedirectionService;
import services.ShortenerService;
import servlets.AuthenticationByGoogleServlet;
import servlets.LogoutServlet;
import servlets.RedirectionServlet;
import servlets.ShortenerServlet;
import storages.connection_sources.ConnectionSource;
import storages.connection_sources.PooledConnectionSourceImpl;
import storages.repositories.UserLinksSqlBasedRepositoryImpl;
import storages.repositories.UsersRepository;
import storages.repositories.UsersSqlBasedRepositoryImpl;
import validation.UriValidator;

import javax.servlet.ServletContextEvent;
import java.io.IOException;

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
                        serve("/authenticateByGoogle").with(AuthenticationByGoogleServlet.class);
                        serve("/logout").with(LogoutServlet.class);
                        serveRegex("/\\w+").with(RedirectionServlet.class);
                        filter("/shorten").through(IsAuthorizedFilter.class);
                        filter("/").through(IsAuthorizedFilter.class);
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

        ConnectionSource connectionSource = new PooledConnectionSourceImpl(config.getDbUserName(), config.getDbPassword(),config.getDbUrl());
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

        bind(UsersRepository.class).toInstance(usersRepository);
        bind(UrlShortenerConfiguration.class).toInstance(config);
        bind(UriValidator.class).toInstance(new UriValidator(config.getMaxLengthOfOriginalUri(), config.getBaseUrlWithoutProtocol()));
    }

    private UrlShortenerConfiguration loadConfig() {
        UrlShortenerConfiguration config = null;
        try {
            config = new UrlShortenerPropertiesConfiguration("/config.properties");
        } catch (IOException e) {
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
