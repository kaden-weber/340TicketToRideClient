package com.example.sql;

import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.UserDao;
import weber.kaden.common.model.Player;

public class SQLUserDao implements UserDao {
    @Override
    public boolean save(List<Player> users) {
        return false;
    }

    @Override
    public List<Player> getUsers() {
        return null;
    }
}
