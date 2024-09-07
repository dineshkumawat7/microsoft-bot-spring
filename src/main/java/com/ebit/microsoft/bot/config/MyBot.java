package com.ebit.microsoft.bot.config;

import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.ChannelAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class MyBot extends ActivityHandler {

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
        String userInput = turnContext.getActivity().getText();
        log.info("user input: {}" , userInput);
        String reply = "You said: " + userInput;
        return turnContext.sendActivity(MessageFactory.text(reply))
                .thenApply(result -> null);
    }

    @Override
    protected CompletableFuture<Void> onMembersAdded(
            java.util.List<ChannelAccount> membersAdded, TurnContext turnContext) {
        return turnContext.sendActivity(MessageFactory.text("Welcome to the bot!"))
                .thenApply(result -> null);
    }
}
