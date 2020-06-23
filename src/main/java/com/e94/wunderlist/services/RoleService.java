package com.e94.wunderlist.services;

import com.e94.wunderlist.models.Role;

import java.util.List;

public interface RoleService
{

    List<Role> findAll();

    Role findRoleById(long id);

    Role save(Role role);

    Role findByName(String name);

    void delete(long id);

    Role update(long id, Role role);
}