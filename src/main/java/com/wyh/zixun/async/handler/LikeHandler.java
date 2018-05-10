package com.wyh.zixun.async.handler;

import com.wyh.zixun.async.EventHandler;
import com.wyh.zixun.async.EventModel;
import com.wyh.zixun.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        System.out.println("Liked");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
