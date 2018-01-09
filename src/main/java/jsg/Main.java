package jsg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws IOException {

    List<String> lines;
    if(args.length == 1) {
      lines = Files.readAllLines(Paths.get(args[0]));
    } else if(args.length == 0) {
      Scanner scanner = new Scanner(System.in);
      lines = new ArrayList<>();
      while(scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }

      scanner.close();
    } else {
      System.out.println("Usage : " + Main.class.getName() + " [file]");
      return;
    }

    StringBuilder buf = new StringBuilder();

    Map<String, List<String>> uniqueTraces = new HashMap<>();


    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      line = line.replaceAll("\\s+$", "");

      if (!Character.isWhitespace(line.charAt(0))) {
        String header = line;
        buf.setLength(0);

        i++;

        for (; i < lines.size(); i++) {
          line = lines.get(i);
          line = line.replaceAll("\\s+$", "");

          if (line.isEmpty()) {
            break;
          }

          buf.append(line + "\n");
        }

        String key = buf.toString();

        uniqueTraces.computeIfAbsent(key, k -> new ArrayList<>()).add(header);


      } else {
        System.out.println("Ignored : " + line);
      }

    }

    for (Entry<String, List<String>> entry : uniqueTraces.entrySet()) {
      for (String header : entry.getValue()) {
        System.out.println(header);
      }

      System.out.println(entry.getKey());
    }
  }
}
