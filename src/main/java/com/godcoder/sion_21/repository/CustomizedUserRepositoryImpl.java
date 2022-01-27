package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.QUser;
import com.godcoder.sion_21.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{
    @PersistenceContext
    private EntityManager em;

    //이번엔 jdbc 템플릿으로

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findByUsernameCustom(String username) {
        QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<User> users = query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();

        return users;
    }

    @Override
    public List<User> findByUsernameJdbc(String username) {

        List<User> users= jdbcTemplate.query(
                "select * from user where username like ?", //원하는커리 날리기위함
                new Object[]{"%"+username + "%"},
                new BeanPropertyRowMapper(User.class));
         return users;
    }
}
