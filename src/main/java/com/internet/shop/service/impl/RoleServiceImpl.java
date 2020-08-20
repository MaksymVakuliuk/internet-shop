package com.internet.shop.service.impl;

import com.internet.shop.dao.RoleDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Role;
import com.internet.shop.service.RoleService;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Inject
    private RoleDao roleDao;

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }

    @Override
    public Role get(Long id) {
        return roleDao.get(id).orElseThrow();
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Role update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public boolean delete(Long id) {
        return roleDao.delete(id);
    }
}
