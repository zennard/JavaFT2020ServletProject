package ua.training.servlet_project.model.dao.impl;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.util.PropertiesLoader;

import javax.sql.DataSource;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        LOGGER.info("datasource: ");
        LOGGER.info(dataSource);
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    Properties properties = PropertiesLoader.getProperties("application.properties");
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName(properties.getProperty("datasource.driverClassName"));
                    ds.setUrl(properties.getProperty("datasource.url"));
                    ds.setUsername(PropertiesLoader.findEnvVarValue(properties.getProperty("datasource.username")));
                    ds.setPassword(PropertiesLoader.findEnvVarValue(properties.getProperty("datasource.password")));
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }


}
