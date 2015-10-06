package com.epam;

import java.io.File;

/**
 * Created by magdy on 23.09.15.
 */
public class FileInfo {
    private static final String FILE_EXTENSION = ".txt";
    private String path;
    private File file;

    public FileInfo(String workingDirectory, int messageId) {
        this.path = String.format("%s%s%d%s", cutFileSeparatorInTheEndIfExist(workingDirectory), File.separator, messageId, FILE_EXTENSION);
        this.file = new File(path);
    }

    private String cutFileSeparatorInTheEndIfExist(String path) {
        if (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - File.separator.length() - 1);
        }
        return path;
    }

    public String getFilePath() {
        return path;
    }

    public boolean exists() {
        return file.exists();
    }
}
