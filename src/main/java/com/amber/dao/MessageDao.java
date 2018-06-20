package com.amber.dao;

import com.amber.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * has_read: unread=0,has read=1.
 */

@Mapper
public interface MessageDao {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") ", "values(#{fromId}, #{toId}, #{content}, #{hasRead}, #{conversationId}, #{createdDate})"})
    int addMessage(Message message);

    @Select({"select * from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit} "})
    List<Message> selectAllMessageByCid(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    //@Select({"select *, count(id) as id from (select * from message where from_id=#{userId} or to_id=#{userId} order by id desc ) tt group by conversation_id order by id desc limit #{offset},#{limit};"})
    @Select({"select ", SELECT_FIELDS, "  from ", TABLE_NAME, " where created_date in(select MAX(created_date) from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} group by conversation_id) order by created_date desc limit #{offset},#{limit}"})
    List<Message> selectAllConversationByUserId(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) as cnt from message where conversation_id=#{cid} and to_id=#{userId} and has_read=0"})
    int selectUnreadMessageCountByCid(@Param("userId") int userId, @Param("cid") String cid);

    @Select({"select count(id) from ", TABLE_NAME, " where conversation_id=#{cid}"})
    int getConversationTotalCount(@Param("cid") String cid);

    @Update({"update message set has_read=1 where conversation_id=#{cid}"})
    int updateReadStatusByCid(@Param("cid") String cid);

}
