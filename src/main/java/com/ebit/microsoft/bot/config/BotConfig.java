package com.ebit.microsoft.bot.config;

import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Properties;


@org.springframework.context.annotation.Configuration
public class BotConfig {
    private final Environment environment;

    public BotConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Configuration getConfiguration(){
        Configuration configuration = new Configuration() {
            @Override
            public String getProperty(String key) {
                // Fetch the appId and password from application.properties
                if ("botAppId".equals(key)) {
                    return environment.getProperty("botAppId", "");
                }
                if ("botAppPassword".equals(key)) {
                    return environment.getProperty("botAppPassword", "");
                }
                return null;
            }

            @Override
            public Properties getProperties() {
                Properties props = new Properties();
                props.setProperty("botAppId", environment.getProperty("botAppId", ""));
                props.setProperty("botAppPassword", environment.getProperty("botAppPassword", ""));
                return props;
            }

            @Override
            public String[] getProperties(String key) {
                String property = getProperty(key);
                return property != null ? new String[]{property} : new String[0];
            }
        };
        return configuration;
    }

    @Bean
    public BotFrameworkHttpAdapter getBotFrameworkHttpAdapter(Configuration configuration) {
        return new BotFrameworkHttpAdapter(getConfiguration());
    }
}