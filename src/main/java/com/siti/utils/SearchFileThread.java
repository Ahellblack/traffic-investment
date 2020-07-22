package com.siti.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Solarie on 2020/7/15.
 */
public class SearchFileThread extends Thread {
    private File file;
    private String content;

    SearchFileThread(File file, String content) {
        this.file = file;
        this.content = content;
    }

    private String getFileContent(File file) {
        try(FileReader fileReader = new FileReader(file)) {
            char []all = new char[(int)file.length()];
            fileReader.read(all);
            return new String(all);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        String str = getFileContent(this.file);
        if(null != str) {
            if(str.contains(this.content)) {
                System.out.printf("在%s中含有 目标串\"%s\" ...%n",this.file.getName(),this.content);
            }
        }
    }


    public static void searchFileContent(File file, String content) {
        if(file.isFile()) {
            if(file.getName().toLowerCase().endsWith(".txt")) {
                new SearchFileThread(file, content).start();
            }
        }
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File singlefile : files) {
                searchFileContent(singlefile, content);
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("C:/Users/Solarie/Desktop/端口策略开通申请表.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String content="端口";
            searchFileContent(file,content);
        }
    }

}