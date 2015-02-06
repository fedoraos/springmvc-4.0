package com.fedora.org.repository;

import com.fedora.org.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by arno on 15-2-5.
 */

public interface LoginRepository {

    public User getUserById(Long id);


    public List<User> selectList(Long id);

    @Select("   SELECT * FROM USERS WHERE ID= #{id, jdbcType=NUMERIC}")
    public User selectOne(Long id);
}
