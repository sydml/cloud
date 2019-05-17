package com.sydml.transaction.domain;

import javax.persistence.*;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
@Entity
@Table(name = "user2")
public class User2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
