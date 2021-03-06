package com.e94.wunderlist.services;


import com.e94.wunderlist.models.User;

import java.util.List;

public interface UserService
{

    List<User> findAll();

    List<User> findByNameContaining(String username);

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);

}