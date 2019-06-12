package com.example.newistdemo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@Entity
@Table(name = "tb_user")
@Data
public class User {

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
}
