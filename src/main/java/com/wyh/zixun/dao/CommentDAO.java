package com.wyh.zixun.dao;

import com.wyh.zixun.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "user_id,content,created_date,entity_id,entity_type,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType} and status=0 order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") String entityType);

    @Select({"select count(id) from ", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType} and status=0 order by id desc"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") String entityType);

    @Update({"update ", TABLE_NAME, " set status=1 where id =#{id}"})
    int updateCommentStatus(@Param("id") int id);
}
