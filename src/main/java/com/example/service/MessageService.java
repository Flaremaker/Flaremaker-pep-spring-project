package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.NullException;
import com.example.repository.MessageRepository;

import java.util.List;
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

    public List<Message> findMessageByAccountId(Integer id){
        List<Message> messageList = messageRepository.findByPostedBy(id);
       
        return messageList; 
}

    public List<Message> findAllMessages() {
        List<Message> messageList = messageRepository.findAll();

        return messageList;
    }

    public Message findMessageById(Integer id) {
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent()) {
            return message.get();
        } else {
            throw new NullException("Error");
        }
    }

    public Message updateMessage(Integer id, String messsageText) {
        Optional<Message> oldMessage = messageRepository.findById(id);
      
        if(!oldMessage.isPresent()){
            throw new NullException("Not present for update");
        }
        if (messsageText.equals(null) || messsageText.isBlank()) {
            throw new NullException("Message Blank");
        }
        if (messsageText.length() > 255) {
            throw new NullException("Message length to long");
        } else {
            oldMessage.get().setMessageText(messsageText);
            return messageRepository.save(oldMessage.get());
        }
    }

    public void removeMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
