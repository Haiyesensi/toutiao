package com.amber.dao;

import com.amber.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NewsDao {
    String TABLE_NAME = " news ";
    String INSERT_FIELDS = " title, link, image, likeCount, commentCount, createdDate, userId ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select commentCount from", TABLE_NAME, "where id=#{id}"})
    int selectCommentCount(@Param("id") int id);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, "where id=#{newsId}"})
    News selectByNewsId(int newsId);

    @Update({"update ", TABLE_NAME, " set commentCount=#{commentCount} where id=#{id}"})
    int updateNewsCommentCount(@Param("commentCount") int commentCount, @Param("id") int id);

    @Update({"update ", TABLE_NAME, " set likeCount=#{likeCount} where id=#{newsId}"})
    int updateNewsLikeCount(@Param("newsId") int newsId, @Param("likeCount") int likeCount);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);
}
