package com.ebit.microsoft.bot.controller;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.connector.authentication.AuthenticationException;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.schema.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
    public CompletableFuture<ResponseEntity<?>> incoming(@RequestBody Activity activity, @RequestHeader(value = "Authorization", defaultValue = "") String authHeader) {
        return this.adapter.processIncomingActivity(authHeader, activity, this.bot).handle((result, exception) -> {
            if (exception == null) {
                return result != null ? new ResponseEntity(result.getBody(), HttpStatus.valueOf(result.getStatus())) : new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                log.error("Exception handling message", exception);
                if (exception instanceof CompletionException) {
                    return exception.getCause() instanceof AuthenticationException ? new ResponseEntity(HttpStatus.UNAUTHORIZED) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        });
    }
}
