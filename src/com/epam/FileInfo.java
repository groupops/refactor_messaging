package com.epam;

import java.io.File;

public class FileInfo {
    private int id;
    private String path;
    private File file;

    public FileInfo(String workingDirectory, int message_id){
        this.id = message_id;
        if (workingDirectory.endsWith(File.separator))
            workingDirectory = workingDirectory.substring(0, workingDirectory.length()-2);
        this.path = FileInfo.constructPath(id, workingDirectory);
        file = new File(path);
    }

    public static String constructPath(int id, String workingDirectory){
        return String.format("%s%s%d.txt", workingDirectory, File.separator, id);
    }
    
    public String getFilePath(){
        return path;
    }

    public boolean exists(){
        return file.exists();
    }

    public int getId() {
        return id;
    }
}
