package com.ebit.microsoft.bot.config;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.builder.BotFrameworkAdapter;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.connector.ConnectorClient;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MyBot implements Bot {
    @Autowired
    private BotFrameworkAdapter adapter;

    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        if (turnContext.getActivity().getType().equals(ActivityTypes.MESSAGE)) {
            String replyText = String.format("You said '%s'", turnContext.getActivity().getText());
            Activity reply = MessageFactory.text(replyText);
            return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
        }
        return CompletableFuture.completedFuture(null);
    }

    public BotFrameworkAdapter getAdapter() {
        return this.adapter;
    }
}
