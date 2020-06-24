package com.e94.wunderlist;

import com.e94.wunderlist.models.Item;
import com.e94.wunderlist.models.Role;
import com.e94.wunderlist.models.User;
import com.e94.wunderlist.models.UserRoles;
import com.e94.wunderlist.services.ItemService;
import com.e94.wunderlist.services.RoleService;
import com.e94.wunderlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    ItemService itemService;


    @Transactional
    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);

        List<UserRoles> admins = new ArrayList<>();
        List<UserRoles> users = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        users.add(new UserRoles(new User(), r2));


        User u1 = new User("admin", "password", "admin@site.com", admins);
        u1.getItems().add(new Item("Take out the trash", u1));

        User u2 = new User("Roger", "password", "roger@site.com", users);
        u2.getItems().add(new Item("Take over the world", u2));

        User u3 = new User("Carl", "password", "carl@site.com", users);
        u3.getItems().add(new Item("Be lazy", u3));

        User u4 = new User("Jenny", "password", "jenny@site.com", users);
        u4.getItems().add(new Item("Talk to Jesus", u4));

        User u5 = new User("Lily", "password", "lily@site.com", users);
        u5.getItems().add(new Item("Get nails done", u5));
        u5.getItems().add(new Item("Help do the laundry", u5));

        User u6 = new User("Brock", "password", "brock@site.com", users);

        User u7 = new User("admin_lesley", "password", "adminlesley@site.com", admins);
        u7.getItems().add(new Item("Delete all users", u7));

        User u8 = new User("Jeff", "password", "jeff@site.com", users);
        u8.getItems().add(new Item("Go drink a red bull", u8));

        User u9 = new User("Erick", "password", "erick@site.com", users);
        u9.getItems().add(new Item("Buy a car", u9));

        User u10 = new User("Haley", "password", "haley@site.com", users);
        u10.getItems().add(new Item("Go on a hike", u10));

        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.save(u4);
        userService.save(u5);
        userService.save(u6);
        userService.save(u7);
        userService.save(u8);
        userService.save(u9);
        userService.save(u10);

    }
}