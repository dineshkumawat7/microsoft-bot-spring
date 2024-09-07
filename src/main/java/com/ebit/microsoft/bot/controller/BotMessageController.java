package com.ebit.microsoft.bot.controller;

import com.ebit.microsoft.bot.config.MyBot;
import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.connector.authentication.AuthenticationException;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.schema.Activity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@Slf4j
public class BotMessageController {
    private final BotFrameworkHttpAdapter adapter;
    private final Bot bot;

    public BotMessageController(BotFrameworkHttpAdapter withAdapter, Bot withBot) {
        this.adapter = withAdapter;
        this.bot = withBot;
    }

    @PostMapping({"/api/messages"})
    public CompletableFuture<ResponseEntity<?>> incoming(@RequestBody Activity activity, @RequestHeader(value = "Authorization",defaultValue = "") String authHeader) {
        return this.adapter.processIncomingActivity(authHeader, activity, this.bot).handle((result, exception) -> {
            if (exception == null) {
                return result != null ? new ResponseEntity(result.getBody(), HttpStatus.valueOf(result.getStatus())) : new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                this.log.error("Exception handling message", exception);
                if (exception instanceof CompletionException) {
                    return exception.getCause() instanceof AuthenticationException ? new ResponseEntity(HttpStatus.UNAUTHORIZED) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        });
    }
}
