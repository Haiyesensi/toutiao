package com.amber.toutiao.Dao;


import com.amber.toutiao.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELD = " (name,password,salt,headUrl) ";
    String SELECT_FIELD = " (id,name,password,salt,headUrl) ";
    @Insert({"insert into ",TABLE_NAME,INSERT_FIELD," values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME,""})
    String getSelectField();
}
