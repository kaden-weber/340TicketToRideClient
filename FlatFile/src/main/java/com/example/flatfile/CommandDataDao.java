package com.example.flatfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import weber.kaden.common.command.Command;
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
    private String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString();
        }
    }
    @Override
    public boolean add(CommandData data) {
        //open file
        try {
            String contents = readFile("commands.json");
            List<CommandData> currentData = serializer.deserializeCommandData(contents);
            currentData.add(data);
            File f = new File("commands.json");
            f.delete();
            save(currentData);

        } catch (Exception e){
            System.out.println("File not created yet");
            List<CommandData> currentData = new ArrayList<>();
            currentData.add(data);
            save(currentData);
        }

        return false;
    }

    @Override
    public List<CommandData> getCommands() {
        try {
            String contents = readFile("commands.json");
            List<CommandData> currentData = serializer.deserializeCommandData(contents);
            return  currentData;


        } catch (Exception e){
            System.out.println("File not created yet");
            List<CommandData> currentData = new ArrayList<>();
            return  currentData;
        }
    }
}
