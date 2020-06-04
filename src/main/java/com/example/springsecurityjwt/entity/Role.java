package com.example.springsecurityjwt.entity;


import com.example.springsecurityjwt.entity.enums.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

}
