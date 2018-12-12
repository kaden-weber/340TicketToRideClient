package com.example.flatfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;

public class CommandDataDao implements weber.kaden.common.injectedInterfaces.persistence.CommandDataDao {

    private String filePath;
    private File file;
    private Serializer serializer;

    CommandDataDao(String filePath) {
        this.filePath = filePath;
        serializer = new Serializer();
        file = new File(filePath);
        try {
            file.createNewFile();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean save(List<CommandData> commands) {

        String serializedData = serializer.serialize(commands);
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
    public boolean clear() {
        // just delete the file completely
        return file.delete();
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
