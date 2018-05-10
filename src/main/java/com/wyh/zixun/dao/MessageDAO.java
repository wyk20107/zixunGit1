package com.wyh.zixun.dao;


import com.wyh.zixun.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = "from_id,to_id,created_date,has_read,conversation_id,content,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{fromId},#{toId},#{createdDate},#{hasRead},#{conversationId},#{content},#{status})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} and status=0 order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversation,
                                        @Param("offset") int offset, @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set status=1 where id =#{id}"})
    int updateMessageStatus(@Param("id") int id);

    @Select({"select count(id) from ",TABLE_NAME," where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId")int userId,@Param("conversationId")String conversationId);

    @Select({"select ",INSERT_FIELDS, " ,count(id) as id from (select * from ",TABLE_NAME," where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId")int userId,
                                      @Param("offset")int fooset,
                                      @Param("limit") int limit);

    @Update({"update ",TABLE_NAME," set has_read=1 where id=#{id}"})
    int updateMessageRead(@Param("id") int id);
}
