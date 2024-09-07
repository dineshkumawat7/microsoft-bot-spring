package com.ebit.microsoft.bot.controller;

import com.ebit.microsoft.bot.config.MyBot;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.builder.TurnContextImpl;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ActivityTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class BotController {

    @Autowired
    private MyBot myBot;

    @PostMapping("/api/messages")
    public CompletableFuture<Void> receiveMessage(@RequestBody Activity activity) {
        if(activity.getType().equals(ActivityTypes.MESSAGE)){
            String userMessage = activity.getText();
            System.out.println(userMessage);
            String processedMessage = preprocessMessage(userMessage);
            TurnContext turnContext = new TurnContextImpl(myBot.getAdapter(), activity);
            return myBot.onTurn(turnContext);
        }
        return null;
    }

    private String preprocessMessage(String message) {
        return message.trim();
    }

//    @Override
//    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {
//        String userMessage = turnContext.getActivity().getText();
//        return turnContext.sendActivity(MessageFactory.text("You said hfbewe Hii..: " + userMessage))
//                .thenApply(result -> null);
//    }
}
