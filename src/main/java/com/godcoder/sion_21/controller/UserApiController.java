package com.godcoder.sion_21.controller;


import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.model.QUser;
import com.godcoder.sion_21.model.User;
import com.godcoder.sion_21.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/users") //required=false 하면 안줘도 됨 만약 저거 안주면 무조건 파라미터 줘야함
    Iterable<User> all(@RequestParam(required = false) String method,@RequestParam(required = false) String text ){ //이 안에 매겝녀수하면 파라메터로 받음
//        List<User> users = repository.findAll();
        Iterable<User> users = null;
        if("query".equals(method)){
            users=repository.findByUsernameQuery(text);
        }else if("nativeQuery".equals(method)){
            users=repository.findByUsernameNativeQuery(text);
        }else if("querydsl".equals(method)){ //Q클래스명 -> 사용가능
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text); //like검색 : contatins
            users=repository.findAll(predicate);
        }else if("querydslCustom".equals(method)) { //Q클래스명 -> 사용가능
            users = repository.findByUsernameCustom(text); //커스텀클래스 사용가능
        }else if("jdbc".equals(method)) { //Q클래스명 -> 사용가능
            users = repository.findByUsernameJdbc(text); //커스텀클래스 사용가능
        }
        else{
            users = repository.findAll();
        }
        return users; //사용자의 건수에 따라서 n+1 프라블럼.(너무 많이호출함. 따라서 )
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) { //유저를통해서 게시글 바꿔보기

        return repository.findById(id)
                .map(user -> {
//                    User.setTitle(newUser.getTitle());
//                    User.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                        user.getBoards().clear();
                        user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()) {//사용자 정보 대입
                        board.setUser(user);
                    }
                        return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}