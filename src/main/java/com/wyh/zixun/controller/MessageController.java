package com.wyh.zixun.controller;

import com.wyh.zixun.model.HostHolder;
import com.wyh.zixun.model.Message;
import com.wyh.zixun.model.User;
import com.wyh.zixun.model.ViewObject;
import com.wyh.zixun.service.MessageService;
import com.wyh.zixun.service.UserService;
import com.wyh.zixun.util.ZixunUtil;
import org.apache.catalina.Host;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model) {
        try{
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
            for (Message msg:conversationList){
                ViewObject vo = new ViewObject();
                vo.set("conversation",msg);
                int targetId ;
                targetId = (msg.getFromId() == localUserId)?msg.getToId():msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("target",user);
                vo.set("unread",messageService.getConversationUnreadCount(localUserId,msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch(Exception e){
            logger.error("获取会话列表失败"+e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                messageService.updateMessageHasRead(msg.getId());
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取消息列表失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addComment(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setHasRead(0);
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            message.setStatus(0);
            messageService.addMessage(message);
            return ZixunUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return ZixunUtil.getJSONString(1, "发送消息失败");
        }
    }

    @RequestMapping(path = {"/msg/deleteMessage"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteComment(@RequestParam("messageId") int messageId,
                                @RequestParam("conversationId") String conversationId) {
        try {
            messageService.deleteMessage(messageId);
        } catch (Exception e) {
            logger.error("删除消息失败" + e.getMessage());
        }
        return "redirect:/msg/detail?conversationId=" + conversationId;
    }
}
