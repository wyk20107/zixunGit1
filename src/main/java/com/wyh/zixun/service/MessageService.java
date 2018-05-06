package com.wyh.zixun.service;

import com.wyh.zixun.dao.MessageDAO;
import com.wyh.zixun.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    public int addMessage(Message message){
        return messageDAO.addMessage(message);
    }


    public int deleteMessage(int id){
        return messageDAO.updateMessageStatus(id);
    }

    public List<Message> getConversationDetail(String ConversationId,int offset,int limit){
        return messageDAO.getConversationDetail(ConversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId,int offset,int limit){
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount (int userId,String conversationId){
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }

    public int updateMessageHasRead(int id){
        return messageDAO.updateMessageRead(id);
    }
}
