package ua.training.servlet_project.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesLoader {
    private static final Logger LOGGER = LogManager.getLogger(PropertiesLoader.class);
    private static final Pattern ENV_VAR_PATTERN = Pattern.compile("\\$\\{(\\w+)\\}|\\$(\\w+)");
    private static final String EMPTY_STRING = "";

    private PropertiesLoader() {
    }

    public static Properties getProperties(String name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();
        try (InputStream input = classLoader.getResourceAsStream(name)) {
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error(ex);
            throw new RuntimeException("Cannot load properties");
        }
        return properties;
    }

    public static String findEnvVarValue(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        Matcher m = ENV_VAR_PATTERN.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String envVarName = m.group(1) == null ? m.group(2) : m.group(1);
            String envVarValue = System.getenv(envVarName);
            m.appendReplacement(sb,
                    envVarValue == null ? EMPTY_STRING : Matcher.quoteReplacement(envVarValue));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
