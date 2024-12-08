package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.NullException;
import com.example.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import antlr.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    //Saves new user calls saves in repo
    @Transactional
    public Message saveMessage(Message message) {
        if (message.getMessageText().isBlank() || message == null) {
            throw new NullException("Error");
        }
        if (message.getMessageText().length() > 255) {
            throw new NullException("to many charecters");
        } else if (accountRepository.findById(message.getPostedBy()).isPresent()) {
            return messageRepository.save(message);
        } else {
            throw new NullException("Error");
        }

    }
    //Find messeges by account Id created repo for this method
    public List<Message> findMessageByAccountId(Integer id){
        List<Message> messageList = messageRepository.findByPostedBy(id);
       
        return messageList; 
}
    //find all messages 
    public List<Message> findAllMessages() {
        List<Message> messageList = messageRepository.findAll();

        return messageList;
    }
    // calles the find  by id in the repository
    public Message findMessageById(Integer id) {
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent()) {
            return message.get();
        } else {
            return null;
        }
    }
    //Update message method needed to retrive the empty structure and no the json
    public String updateMessage(Integer id, String messsageText) {
        String text = "";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> map = objectMapper.readValue(messsageText, Map.class);
             text =  map.get("messageText");
        } catch (Exception e) {
            throw new NullException("Message Blank");
        }
        
        Optional<Message> oldMessage = messageRepository.findById(id);
    
        if(!oldMessage.isPresent()){
            throw new NullException("Not present for update");
            
        }
        
        if (messsageText == null || text.isBlank() || text.contentEquals("") || text.equals("")){
            throw new NullException("Message Blank");
        }
        if (messsageText.length() > 255) {
            throw new NullException("Message length to long");
        } 
        System.out.println("dasdasdasdad" + text);
            oldMessage.get().setMessageText(text);
            messageRepository.save(oldMessage.get());
            return "1";
        
    }
    // Removes the message which calls deletebyId from the repo
    public String removeMessage(Integer id) {
       if(messageRepository.findById(id).isPresent()){
        messageRepository.deleteById(id);
        return "1";
       }

        return "";
    }
}
