package com.epam.model;

public enum UserCommand {

  WRITE('w'),
  READ('r'),
  QUIT('q');

  private final char key;

  UserCommand(Character key){
    this.key = key;
  }

}
