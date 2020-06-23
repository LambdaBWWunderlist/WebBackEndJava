package com.e94.wunderlist.services;

import com.e94.wunderlist.exceptions.ResourceFoundException;
import com.e94.wunderlist.exceptions.ResourceNotFoundException;
import com.e94.wunderlist.models.Role;
import com.e94.wunderlist.models.User;
import com.e94.wunderlist.models.UserRoles;
import com.e94.wunderlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserAuditing userAuditing;


    public User findUserById(long id) throws ResourceNotFoundException {
        return userrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null) {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            User oldUser = userrepos.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));

            for (UserRoles ur : oldUser.getRoles())
            {
                deleteUserRole(ur.getUser().getUserid(), ur.getRole().getRoleid());
            }

            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());


        if (user.getUserid() == 0)
        {
            for (UserRoles ur : user.getRoles())
            {
                Role newRole = roleService.findRoleById(ur.getRole().getRoleid());

                newUser.addRole(newRole);
            }
        } else
        {
            // add the new roles for the user we are replacing
            for (UserRoles ur : user.getRoles())
            {
                addUserRole(newUser.getUserid(),
                        ur.getRole()
                                .getRoleid());
            }
        }


        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);

            if (user.getUsername() != null) {
                currentUser.setUsername(user.getUsername().toLowerCase());
            }

            if (user.getPassword() != null) {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getEmail() != null) {
                currentUser.setEmail(user.getEmail().toLowerCase());
            }

            if (user.getRoles().size() > 0)
            {
                for (UserRoles ur : currentUser.getRoles()) {
                    deleteUserRole(ur.getUser().getUserid(),
                            ur.getRole().getRoleid());
                }


                for (UserRoles ur : user.getRoles()) {
                    addUserRole(currentUser.getUserid(), ur.getRole().getRoleid());
                }

            return userrepos.save(currentUser);

        } else {
                throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid, long roleid) {
        userrepos.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        roleService.findRoleById(roleid);

        if (userrepos.checkUserRolesCombo(userid, roleid).getCount() > 0) {
            userrepos.deleteUserRoles(userid, roleid);
        } else {
            throw new ResourceNotFoundException("Role and User Combination Does Not Exists");
        }
    }


    @Transactional
    @Override
    public void addUserRole(long userid, long roleid)
    {
        userrepos.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        roleService.findRoleById(roleid);

        if (userrepos.checkUserRolesCombo(userid, roleid).getCount() <= 0) {
            userrepos.insertUserRoles(userAuditing.getCurrentAuditor().get(), userid, roleid);
        } else {
            throw new ResourceFoundException("Role and User Combination Already Exists");
        }
    }
}