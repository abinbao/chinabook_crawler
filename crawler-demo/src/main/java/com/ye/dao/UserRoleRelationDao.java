package com.ye.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleRelationDao {

    /**
     * 添加用户
     * 
     * @param user
     * @return
     */
    @Insert("insert into `crawler_db`.`user_role_relation` (`id`, `userid`, `roleid`) VALUES (null, #{userid}  ,'2');")
    int insert(String userid);
}
