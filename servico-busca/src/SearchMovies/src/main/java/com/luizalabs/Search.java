package com.luizalabs;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


public class Search {

  public static void main(String[] args) throws IOException {

    String movie = "walt disney";

    if(args.length == 2){
      movie = args[1];
    }
    List<String> results = new ArrayList<String>();

    File folder = getFileFromURL();
    File[] files = folder.listFiles();

    if (files != null) {

      for (File file : files) {

        String line = null;
        ArrayList<String> fileContents = new ArrayList<>();

        if (file.isFile()) {

          FileReader fReader = new FileReader(file);
          BufferedReader fileBuff = new BufferedReader(fReader);
          while ((line = fileBuff.readLine()) != null) {
            fileContents.add(line);
          }
          fileBuff.close();

          String finalMovie = movie;

          int fileContentsSize = fileContents.stream().filter(x -> x.matches("^.*?("+ finalMovie +").*$")).toList().size();

          if(fileContentsSize > 0){
            results.add(file.getName());
          }
        }
      }

      Collections.sort(results);

      System.out.println("Foram encontrada(s) " + results.size() + " ocorrências pelo termo " + movie + ".");

      if(results.size() > 0){

        System.out.println("O(s) arquivo(s) que possui/possuem " + movie + " é/são:");

        for (String itens: results) {
          System.out.println(itens);
        }
      }

    }

  }

  private static File getFileFromURL() throws MalformedURLException {

    URL url = Paths.get("target", "classes", "data").toUri().toURL();

    File file = null;
    try {
      file = new File(url.toURI());
    } catch (URISyntaxException e) {
      file = new File(url.getPath());
    } finally {
      return file;
    }
  }

}