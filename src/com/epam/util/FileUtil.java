package com.epam.util;

import java.io.File;

public class FileUtil {
    public static String constructPath(int id, String workingDirectory){
        return String.format("%s%s%d.txt", workingDirectory, File.separator, id);
    }
}
