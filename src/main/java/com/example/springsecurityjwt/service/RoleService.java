package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.Role;
import com.example.springsecurityjwt.entity.enums.RoleName;
import com.example.springsecurityjwt.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role>findAll(){
        return roleRepository.findAll();
    }

    public Role findByName(RoleName roleName){
        return roleRepository.findByName(roleName);
    }





}
