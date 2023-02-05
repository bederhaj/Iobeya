package com.iobeya.category.entity;


import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "role")
    private String role;



}
