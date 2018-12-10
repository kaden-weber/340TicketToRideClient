package com.example.flatfile;

import java.io.File;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;

public class CommandDataDao implements weber.kaden.common.injectedInterfaces.persistence.CommandDataDao {

    private String filePath;
    private File file;

    CommandDataDao(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean save(List<CommandData> data) {
        return false;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public boolean add(CommandData data) {
        return false;
    }

    @Override
    public List<CommandData> getCommands() {
        return null;
    }
}
