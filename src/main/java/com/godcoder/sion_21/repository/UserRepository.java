package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {

    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();

    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String firstname);

    @Query(value = "select * from User u where u.username like %?1%",nativeQuery = true)
    //JPQL이 아니라 순수 sql 쿼리가 됨. native Query써서.
    List<User> findByUsernameNativeQuery(String firstname);

    //마지막 저 뒤에꺼 extends해서, 아래와 같은 함수 사용 가능함.
//    Optional<T> findById(Predicate predicate);
//
//    Iterable<T> findAll(Predicate predicate);
//
//    long count(Predicate predicate);
//
//    boolean exists(Predicate predicate);
}
