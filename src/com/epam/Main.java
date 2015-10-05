package com.epam;

import com.epam.model.FileInfo;
import com.epam.model.MessageCache;
import com.epam.model.UserCommand;
import com.epam.service.MessagingService;
import com.epam.util.Maybe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  private static BufferedReader reader;
  private static final String WORKING_DIR =
      "/home/magdy/Desktop/messages_folder";
  public static final MessageCache messageCache = new MessageCache();

  public static void main(String[] args) {
    runApplication();
  }

  public static void runApplication() {
    reader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int id_counter = 1;
    do {
      MessagingService messaging_service =
          new MessagingService(WORKING_DIR, messageCache);
      System.out.print(
          "Please save one of these options: \n" +
              "[w]rite message\n" +
              "[r]ead message\n" +
              "[q]uit\n" +
              "Please save your choice and press <Enter>: "
      );

      try {
        input = reader.readLine();
        id_counter = actionForInput(input, id_counter, messaging_service,
            reader);
      } catch (IOException e) {
        throw new RuntimeException(
            "The message cannot be read.", e);
      }
    } while (!input.toLowerCase().equals(UserCommand.QUIT));
  }

  private static int actionForInput(String input, int id_counter,
                                    MessagingService messaging_service,
                                    BufferedReader reader)
      throws IOException {
    if (input.toLowerCase().equals(UserCommand.WRITE)) {
      id_counter = makeWriteAction(id_counter, messaging_service, reader);
    } else if (input.toLowerCase().equals(UserCommand.READ)) {
      makeReadAction(messaging_service, reader);
    }
    return id_counter;
  }

  private static void makeReadAction(MessagingService messaging_service,
                                     BufferedReader reader)
      throws IOException {
    System.out.print("Please enter the message id: ");
    int id = Integer.parseInt(reader.readLine());
    FileInfo file_info = messaging_service.getMessageFileInfoById(id);
    if (file_info.exists()) {
      String path = file_info.getFilePath();
      Maybe<String> message = messaging_service.readMessage(path);
      System.out.println(message);
    }
  }

  private static int makeWriteAction(int id_counter,
                                     MessagingService messaging_service,
                                     BufferedReader reader)
      throws IOException {
    System.out.print("Please enter your message: ");
    String message = reader.readLine();
    messaging_service.saveMessage(id_counter, message);
    id_counter++;
    return id_counter;
  }
}
