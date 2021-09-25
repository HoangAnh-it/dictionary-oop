package com.gryffindor.backend.utils;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;

import com.gryffindor.backend.entities.Dictionary;
import com.gryffindor.backend.entities.Word;

public class DictionaryManagement {
  public final Dictionary dictionary;
  // direction of directories.txt
  private static String path = "src/main/java/com/gryffindor/backend/storage/dictionaries.txt";

  private static Scanner scanner = new Scanner(System.in);

  public DictionaryManagement() {
    dictionary = new Dictionary();
  }

  /** Nhập từ mới từ command line. */
  public void insertFromCommandline() {
    System.out.println("Nhap so tu moi muon them:");

    int n = scanner.nextInt();
    scanner.nextLine(); // bỏ qua 1 dòng thừa

    for (int i = 1; i <= n; i++) {
      System.out.println(String.format("Dang nhap tu thu %d...", i));
      System.out.println("Nhap tu moi:");

      String word_target = scanner.nextLine();

      System.out.println("Nhap nghia:");
      String word_explain = scanner.nextLine();

      dictionary.addWord(new Word(word_target, word_explain));
    }
  }

  /**
   * Nhạp dữ liệu vào dictionaries.txt
   */
  public void insertFromFile() {
    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;
    try {
      fileWriter = new FileWriter(path);
      bufferedWriter = new BufferedWriter(fileWriter);
      System.out.println("Nhập số từ muốn thêm: ");
      int n = scanner.nextInt();
      scanner.nextLine();
      while (n-- > 0) {
        System.out.println("Nhập từ tiếng anh: ");
        String word_target = scanner.nextLine();
        System.out.println("Nhập nghĩa: ");
        String word_explain = scanner.nextLine();
        String new_word = word_target + "\t" + word_explain;
        bufferedWriter.write(new_word);
        bufferedWriter.newLine();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedWriter.close();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * tra từ trong từ điển.
   */
  public void dictionaryLookup() {
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;
    try {
      System.out.println("Nhập từ cần tra: ");
      String input = scanner.nextLine();

      fileReader = new FileReader(path);
      bufferedReader = new BufferedReader(fileReader);
      String data = null;
      while ((data = bufferedReader.readLine()) != null) {
        if (data.toLowerCase().contains(input.toLowerCase())) {
          String[] text = data.trim().split("\t");
          String word_target = text[0].trim();
          String word_explain = text[1].trim();
          System.out.println(word_target + " | " + word_explain);
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
        fileReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Xóa từ trong list words.
   */
  public void deleteFromCommand() {
    System.out.println("Nhap tu muon xoa: ");
    String input = scanner.next();
    List<Word> w = dictionary.searchWords(input);
    List<Word> clone_words = dictionary.getAllWords();
    clone_words.removeAll(w);
  }

}
