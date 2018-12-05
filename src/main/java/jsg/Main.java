package jsg;

import java.io.IOException;
import java.lang.Thread.State;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.Thread.State.*;

public class Main {

  private static final String PREFIX = "   java.lang.Thread.State: ";

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
          line = line.replaceAll("0x[0-9a-f]+", "HEX");

          if (line.isEmpty()) {
            break;
          }

          buf.append(line).append("\n");
        }

        String key = buf.toString();

        uniqueTraces.computeIfAbsent(key, k -> new ArrayList<>()).add(header);


      } else {
        System.out.println("Ignored : " + line);
      }

    }

    uniqueTraces.entrySet().stream()
        .sorted(Map.Entry.comparingByKey(
            Comparator.comparingInt(Main::toStateIdx).reversed()))
        .flatMap(e -> Stream.concat(e.getValue().stream(), Stream.of(e.getKey())))
        .forEach(System.out::println);
  }

  private static int toStateIdx(String trace) {
    for (State state : values()) {
      if (trace.contains(PREFIX + state)) {
        return state.ordinal();
      }
    }
    return -1;
  }
}
