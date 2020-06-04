package com.example.springsecurityjwt.repository;


import com.example.springsecurityjwt.entity.Role;
import com.example.springsecurityjwt.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Set<Role> findAllByName(RoleName name);

    Role findByName(RoleName roleName);

}
