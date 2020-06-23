package com.e94.wunderlist.services;

import com.e94.wunderlist.exceptions.ResourceFoundException;
import com.e94.wunderlist.exceptions.ResourceNotFoundException;
import com.e94.wunderlist.models.Role;
import com.e94.wunderlist.repositories.RoleRepository;
import com.e94.wunderlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository rolerepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Role> findAll()
    {
        List<Role> list = new ArrayList<>();

        rolerepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }


    @Override
    public Role findRoleById(long id) {
        return rolerepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role findByName(String name) {
        Role rr = rolerepos.findByNameIgnoreCase(name);

        if (rr != null) {
            return rr;
        } else {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public Role save(Role role)
    {
        if (role.getUsers().size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through Role.");
        }

        return rolerepos.save(role);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        rolerepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
        rolerepos.deleteById(id);
    }

    @Transactional
    @Override
    public Role update(long id, Role role) {
        if (role.getName() == null) {
            throw new ResourceNotFoundException("No role name found to update!");
        }

        if (role.getUsers().size() > 0) {
            throw new ResourceFoundException("User Roles are not updated through Role");
        }

        Role newRole = findRoleById(id);

        rolerepos.updateRoleName(userAuditing.getCurrentAuditor().get(), id, role.getName());
        return findRoleById(id);
    }
}
