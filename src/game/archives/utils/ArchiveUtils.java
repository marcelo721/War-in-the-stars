package game.archives.utils;

import game.archives.exceptions.ReadErrorException;

import java.io.*;

public class ArchiveUtils {

    public static String readArchives(String pathEnter) {
       try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pathEnter))){
           return bufferedReader.readLine();
       } catch (IOException e) {
           throw new ReadErrorException("Erro ao ler o arquivo");
       }
    }

    public static void writeArchives(String file, String text) {
        try {

            FileWriter archive = new FileWriter(file);
            archive.write(text);
            archive.close();

        } catch (Exception e) {
            throw new ReadErrorException("Erro ao salvar o arquivo");
        }

    }
}


