import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets.RedirectionServlet;

public class ApplicationListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets() {
                serve("/*").with(RedirectionServlet.class);
            }
        });
    }

    final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);
}
