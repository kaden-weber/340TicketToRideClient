package com.example.flatfile;

import java.io.File;
import java.util.List;

import weber.kaden.common.model.Player;

public class UserDao implements weber.kaden.common.injectedInterfaces.persistence.UserDao {

    private String filePath;
    private File file;

    UserDao(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
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
