package notification.persistence.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("notificationDatabaseScriptExecutor")
public class DatabaseScriptExecutor {

    private final DataSource dataSource;

    public DatabaseScriptExecutor(@Qualifier("notificationDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void executeScripts() {
        try {
            var populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("notification-schema.sql"));
            populator.execute(dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute database script", e);
        }
    }
}