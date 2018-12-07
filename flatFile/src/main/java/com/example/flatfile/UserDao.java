package com.example.flatfile;

import java.util.List;

import weber.kaden.common.model.Player;

public class UserDao implements weber.kaden.common.injectedInterfaces.persistence.UserDao {

    @Override
    public boolean save(List<Player> users) {
        return false;
    }

    @Override
    public List<Player> getUsers() {
        return null;
    }
}
