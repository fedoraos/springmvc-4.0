package com.fedora.org.repository.impl;

import com.fedora.org.domain.User;
import com.fedora.org.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by arno on 15-2-5.
 */
@Repository
public class LoginRepositoryImpl
{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUserById(Long id) {
        User user =    jdbcTemplate.queryForObject("select * from USERS where id =?", new Object[]{id}, User.class);
        return user;
    }
}
