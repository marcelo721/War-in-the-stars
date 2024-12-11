package game.archives;

import java.io.*;

public class Utils {

    public static String readArchives(String pathEnter) {
       try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pathEnter))){
           return bufferedReader.readLine();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }

    public static void writeArchives(String file, String text) {
        try {

            FileWriter archive = new FileWriter(file);
            archive.write(text);
            archive.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


