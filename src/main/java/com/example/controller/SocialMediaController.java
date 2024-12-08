package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import net.bytebuddy.asm.Advice.Argument;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

   
    private final MessageService messageService;

   
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }
    //Account login and Register
    @PostMapping("/register")
    public Account registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("/login")
    public Account loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    //Message controler specfic options
    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.findAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable Integer messageId) {
        return messageService.findMessageById(messageId);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getAllMessagesByAccountId(@PathVariable Integer accountId){
        return messageService.findMessageByAccountId(accountId);
    }

    @PatchMapping("/messages/{messageId}")
    public String updateMessage(@PathVariable Integer messageId,  @RequestBody String messageText){
        return messageService.updateMessage(messageId,messageText);
    }

    @DeleteMapping("/messages/{messageId}")
    public String deleteMessage(@PathVariable Integer messageId){
        return messageService.removeMessage(messageId);
    }


}
