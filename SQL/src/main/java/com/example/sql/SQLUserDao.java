package com.example.sql;

import java.sql.Connection;
import java.util.List;

import weber.kaden.common.injectedInterfaces.persistence.UserDao;
import weber.kaden.common.model.Player;

public class SQLUserDao extends SQLDao implements UserDao {
    public SQLUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(List<Player> users) {
        return false;
    }

    @Override
    public List<Player> getUsers() {
        return null;
    }
}
