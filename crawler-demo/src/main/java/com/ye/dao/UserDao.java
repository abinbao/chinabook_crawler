package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ye.model.User;

/**
 * 用户模块（与数据库进行交互）
 */
@Mapper
public interface UserDao {
    /**
     * 查询用户信息
     */
    @Select({ "select * from user as u where u.id in (select userid from user_role_relation where roleid = '2')" })
    List<User> queryAll();

    @Select({ "select * from user where username = #{username}" })
    User queryUserByUsername(String username);

    /**
     * 添加用户
     * 
     * @param user
     * @return
     */
    @Insert("insert into `crawler_db`.`user` (`id`, `username`, `password`, `realname`, `sex`, `age`, `phone`, `email` , `create_time`, `update_time`, `state`) VALUES (null, #{username}, #{password}, #{realname}, #{sex}, #{age}, #{phone}, #{email}, now(), now(), 1);")
    int insertUser(User user);

    /**
     * 删除用户
     * 
     * @param patient
     * @return
     */
    @Delete("delete from user where id = #{id}")
    int deleteById(User user);

    /**
     * 根据 id 查询用户信息
     * 
     * @param user
     * @return
     */
    @Select({ "select * from user where id = #{id}" })
    User findById(User user);

    /**
     * 根据用户名查询用户信息
     * 
     * @param user
     * @return
     */
    @Select({ "select * from user where username = #{username}" })
    User findByUserName(String username);

    /**
     * 根据 id 更新用户信息
     * 
     * @param user
     */
    @Update("update user set `username`=#{username}, `realname` = #{realname} , `password` = #{password} , `age`=#{age},`sex`=#{sex} , `phone`=#{phone},`email`=#{email} WHERE `id` = #{id};")
    void updateAll(User user);

}
