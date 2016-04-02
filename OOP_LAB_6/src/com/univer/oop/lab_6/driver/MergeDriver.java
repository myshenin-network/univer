package com.univer.oop.lab_6.driver;

import java.io.*;

public class MergeDriver {
    private File[] files;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;

    public MergeDriver(File[] files) {
        this.files = files;
    }

    public void merge(String destination) throws IOException {
        outputStream = new FileOutputStream(destination);
        int tmp = 0;

        for(int i = 0; i < files.length; i++){
            inputStream = new FileInputStream(files[i]);
            while (tmp != -1){
                tmp = inputStream.read();
                if(tmp != -1) outputStream.write(tmp);
            }
            inputStream.close();
            tmp = 0;
        }
        outputStream.close();
    }
}
