package com.epam.model;

import java.io.File;

/**
 * Created by magdy on 23.09.15.
 */
public class FileInfo {

  private String working_directory;
  private int id;
  private String path;
  private File file;
  private static final String FILE_EXTENSION = ".txt";

  public FileInfo(String working_directory, int message_id) {
    this.working_directory = working_directory;
    this.id = message_id;
    if (working_directory.endsWith(File.separator))
      working_directory =
          working_directory.substring(0, working_directory.length() - 2);
    this.path =
        String.format("%s%s%d.txt", working_directory, File.separator, id);
    file = new File(path);
  }

  public FileInfo(String filePath) {
    this.path = filePath;
    int indexOfLastSeparator = filePath.lastIndexOf(File.separator);
    int indexOfFileExtension = filePath.lastIndexOf(FILE_EXTENSION);
    this.id = Integer.valueOf(filePath.substring(indexOfLastSeparator + 1, indexOfFileExtension));
    this.file = new File(path);
  }

  public String getFilePath() {
    return path;
  }

  public boolean exists() {
    return file.exists();
  }
}
