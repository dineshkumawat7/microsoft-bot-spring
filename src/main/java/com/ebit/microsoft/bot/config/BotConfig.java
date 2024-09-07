package com.ebit.microsoft.bot.config;

import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.connector.ConnectorClient;
import com.microsoft.bot.connector.authentication.MicrosoftAppCredentials;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Properties;

@Component
public class BotConfig {
    @Bean
    public BotFrameworkAdapter getBotFrameworkAdapter(Configuration configuration) {
        return new BotFrameworkHttpAdapter(configuration);
    }
}