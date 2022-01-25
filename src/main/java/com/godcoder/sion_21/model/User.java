package com.godcoder.sion_21.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>(); //기본적으로 Arraylist로 해버림. nullexception방지
    //현재 계속 서로 재귀적으로 하는데..?

//    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,orphanRemoval = true) //저장이 안될땡 ㅣㅆ는데, 그러떄 cascade
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY) //저장이 안될땡 ㅣㅆ는데, 그러떄 cascade
    //fetch : ㅅ용자 조회시 board클래스를 같이 가져올건지, 필요할떄 가져올껀지.
    //EAGER: 같이 가져옴. LAZY: 필요할떄 조회함.
    //orphan 옵션: 지울떄 편함. 부모없는애들 다 죽임
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
