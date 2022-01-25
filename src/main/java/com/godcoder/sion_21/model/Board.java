package com.godcoder.sion_21.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //sequence가 빠르지만, 조금 관리하기 복잡함. 그래서 IDENTITY 자주씀
    //내부에서 db종류에따라서 알아서 설정됨.
    private Long id;
    @NotNull
    @Size(min=2, max=30,message = "제목을 2~30으로 맞추도록.")
    private String title;
    private String content;

    //board에서 user 정보 알수 잇도록
    @ManyToOne
    @JoinColumn(name="user_id")//d어떤 테이블과 연결될지 ,, 사용자 컬럼의 어떤 것과 연결이 되는지!
    //User테이블에 id와 연결이 되므로
    @JsonIgnore
    private User user;

}
