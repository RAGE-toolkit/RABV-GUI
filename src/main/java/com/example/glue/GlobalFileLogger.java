package com.example.glue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalFileLogger {
    private static GlobalFileLogger instance;
    private BufferedWriter writer;

    private GlobalFileLogger(String filePath) {
        try {
            // Open the file in append mode
            FileWriter fileWriter = new FileWriter(filePath, true);
            writer = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println("Error opening the log file: " + e.getMessage());
        }
    }

    public static GlobalFileLogger getInstance(String filePath) {
        if (instance == null) {
            instance = new GlobalFileLogger(filePath);
        }
        return instance;
    }

    public void write(String content) {
        try {
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing the log file: " + e.getMessage());
        }
    }
}
