package spring.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseContext {

    @Autowired
    private Environment env;
    
    private DataSource getDataSource() {
        DataSource ds = new DataSource();

        ds.setDriverClassName(env.getProperty("jdbc.driver-class"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));
        
        // Very special configuration
        ds.setInitialSize(3); // 3 connection created
        ds.setMaxActive(3); // 3 is maxium held
        ds.setMinIdle(1); // Always remain 1
        ds.setMaxIdle(3); // 3 maxium in ideal
        // maxwait - default 30000
        ds.setMinEvictableIdleTimeMillis(50000);
        ds.setValidationQuery("SELECT 1");
        ds.setValidationQueryTimeout(3);
        ds.setTestOnBorrow(true);
        ds.setTestOnConnect(true);
        ds.setTestWhileIdle(true);
        ds.setTestOnReturn(true);
        
        

        return ds;
    }

    @Bean(name = "template")
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}