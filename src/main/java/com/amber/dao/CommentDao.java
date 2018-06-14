package com.amber.dao;

import com.amber.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " entity_type, entity_id, user_id, content, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")", " values(#{entityType}, #{entityId}, #{userId},  #{content}, #{createdDate})"})
    int addComment(Comment comment);

    @Select({"select * from comment where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int selectCommentCountByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);
}
