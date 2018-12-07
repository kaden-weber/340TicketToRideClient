package com.example.sql;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.injectedInterfaces.persistence.CommandDataDao;

public class SQLCommandDataDao implements CommandDataDao {
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
