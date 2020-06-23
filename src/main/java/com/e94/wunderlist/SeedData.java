package com.e94.wunderlist;

import com.e94.wunderlist.models.Item;
import com.e94.wunderlist.models.Role;
import com.e94.wunderlist.models.User;
import com.e94.wunderlist.models.UserRoles;
import com.e94.wunderlist.services.RoleService;
import com.e94.wunderlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner
{

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Transactional
    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);

        List<UserRoles> admins = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));

        User u1 = new User("admin", "password", "admin@site.com", items, admins);

        userService.save(u1);

    }
}