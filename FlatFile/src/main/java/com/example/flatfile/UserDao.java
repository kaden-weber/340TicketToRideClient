package com.example.flatfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import weber.kaden.common.model.Player;

public class UserDao implements weber.kaden.common.injectedInterfaces.persistence.UserDao {

    private String filePath;
    private File file;
    private Serializer serializer;

    UserDao(String filePath) {
        serializer = new Serializer();
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(List<Player> users) {
        String serializedData = serializer.serialize(users);
        // write to file
        BufferedWriter writer = null;
        boolean success = false;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(serializedData);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null)
                    writer.close();
            } catch (Exception e) {
                System.out.println("Error in closing the BufferedWriter" + e);
                success = false;
            }
        }
        return success;
    }

    @Override
    public List<Player> getUsers() {
        try {
            String serializedData = new String(Files.readAllBytes(Paths.get(filePath)));
            List<Player> users = serializer.deserializePlayers(serializedData);
            if (users == null) return new ArrayList<>();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean clear() {
        return file.delete();
    }
}
