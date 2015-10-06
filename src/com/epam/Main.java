package com.epam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static BufferedReader reader;
    private static final String WORKING_DIR = "/home/magdy/Desktop/messages_folder";

    public static void main(String[] args) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int id_counter = 1;
        do {
            MessagingService messaging_service = new MessagingServiceImpl(WORKING_DIR);
            System.out.print(
                    "Please write one of these options: \n" +
                    "[w]rite message\n" +
                    "[r]ead message\n" +
                    "[q]uit\n" +
                    "Please write your choice and press <Enter>: "
            );
            try {
                input = reader.readLine();

                if (input.toLowerCase().startsWith("w")) {  // write
                    System.out.print("Please enter your message: ");
                    String message = reader.readLine();
                    messaging_service.saveMessage(id_counter, message);
                    id_counter++;
                } else if (input.toLowerCase().startsWith("r")) {   // read
                    System.out.print("Please enter the message id: ");
                    int id = Integer.parseInt(reader.readLine());
                    FileInfo fileInfo = new FileInfo(WORKING_DIR, id);
                    if (fileInfo.exists()) {
                        String path = fileInfo.getFilePath();
                        Maybe<String> message = messaging_service.readMessage(path, id);
                        System.out.println(message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("If I cannot read from Standard Input then what is life good for :( let's die.", e);
            }
        } while (!input.toLowerCase().startsWith("q"));  // quit
    }
}
