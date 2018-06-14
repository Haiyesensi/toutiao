package com.amber.dao;

import com.amber.model.Ticket;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface TicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "userId,ticket,expired,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_FIELDS, " )", " values(#{userId},#{ticket},#{expired},#{status})"})
    void addTicket(Ticket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    Ticket selectByTicket(String ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where userId=#{userId}"})
    Ticket selectByUserId(int userId);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("status") int status, @Param("ticket") String ticket);

    @Update({"update ", TABLE_NAME, " set expired=#{expired} where ticket=#{ticket}"})
    void updateExpired(@Param("expired") Date expired, @Param("ticket") String ticket);
}
