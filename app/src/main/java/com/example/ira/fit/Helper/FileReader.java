package com.example.ira.fit.Helper;

import java.io.*;
//import java.io.FileReader;
//import java.io.IOException;

public class FileReader {
    private BufferedReader br = null;

    public FileReader(String file) {
        try {
            InputStream ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            br = new BufferedReader(/*new FileReader(file)*/ipsr);

        } catch (IOException e) {
            e.printStackTrace();
            br = null;
        }
    }

    public String readLine() {
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void close() {
        try {
            if (br != null) br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}