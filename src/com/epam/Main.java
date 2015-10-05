package com.epam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private enum Command {
        READ,
        WRITE,
        EXIT,
        SKIP
    }

    private static final String WORKING_DIR = "D:";
    private static final Map<String, Message> CACHING_MESSAGES = new HashMap<>();
    private static BufferedReader consoleReader;
    private static int MESSAGES_COUNTER = 0;

    public static void main(String[] args) throws IOException {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        MessageReader reader = new CachedMessageReader(new HashMap<>());
        MessageWriter writer = new CachedMessageWriter(CACHING_MESSAGES, WORKING_DIR);
        do {
            switch (getConsoleCommand()) {
                case READ:
                    readMessage(reader);
                    break;
                case WRITE:
                    writeMessage(writer);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private static Command getConsoleCommand() throws IOException {
        System.out.print("Please write one of these options: \n" +
                "[w]rite message\n" +
                "[r]ead message\n" +
                "[q]uit\n" +
                "Please write your choice and press <Enter>: ");
        String input = consoleReader.readLine().toLowerCase();
        return input.startsWith("w") ? Command.WRITE : (input.startsWith("r") ? Command.READ : (input.startsWith("q") ? Command.EXIT : Command.SKIP));
    }

    private static void readMessage(MessageReader reader) throws IOException {
        System.out.print("Please enter the message id: ");
        int id = Integer.parseInt(consoleReader.readLine());
        FileInfo fileInfo = new FileInfo(WORKING_DIR, id);
        if (fileInfo.exists()) {
            String path = fileInfo.getFilePath();
            for (Message message : reader.readMessage(path)) {
                System.out.println(message.getBody());
            }
        }
    }

    private static void writeMessage(MessageWriter writer) throws IOException {
        System.out.print("Please enter your message: ");
        String message = consoleReader.readLine();
        writer.saveMessage(new Message(MESSAGES_COUNTER, message));
        MESSAGES_COUNTER++;
    }
}
