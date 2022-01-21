package com.godcoder.sion_21.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //sequence가 빠르지만, 조금 관리하기 복잡함. 그래서 IDENTITY 자주씀
    //내부에서 db종류에따라서 알아서 설정됨.
    private Long id;
    @NotNull
    @Size(min=2, max=30)
    private String title;
    private String content;
}
