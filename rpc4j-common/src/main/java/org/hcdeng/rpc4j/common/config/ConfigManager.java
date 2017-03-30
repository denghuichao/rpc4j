package org.hcdeng.rpc4j.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class ConfigManager {
    private static final String CLASSPATH_CONFIG_PATH = "/rpc4j.properties";

    private static volatile ConfigManager INSTANCE;

    private Properties properties;

    public static ConfigManager instance() {
        if (INSTANCE == null) {
            synchronized (ConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigManager();
                }
            }
        }
        return INSTANCE;
    }

    private ConfigManager() {
        properties = new Properties();

        InputStream inputStream = ConfigManager.class.getResourceAsStream(CLASSPATH_CONFIG_PATH);
        try {
            properties.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
