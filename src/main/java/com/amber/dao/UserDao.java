package com.amber.dao;


import com.amber.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    String TABLE_NAME = " user ";
    String INSERT_FIELD = " name,password,salt,headUrl ";
    String SELECT_FIELD = " id,"+INSERT_FIELD;
    @Insert({"insert into ",TABLE_NAME," (",INSERT_FIELD,") values(#{name},#{password},#{salt},#{headUrl})"})
    void addUser(User user);

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," where id=#{id}"})
    User selectUserByID(int id);

    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    void updatePassowed(User user);

    @Delete({"delete ",TABLE_NAME," where id=#{id}"})
    void deleteById(int id);
}
