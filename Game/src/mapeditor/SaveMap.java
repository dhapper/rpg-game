package mapeditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveMap {

	public static void WriteToFile(Tile[][] tiles) {
        String folderPath = "res";
        String filename = folderPath + "/new_map.txt";

        // Create the res folder if it doesn't exist
        File resFolder = new File(folderPath);
        if (!resFolder.exists()) {
            resFolder.mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    bufferedWriter.write(tiles[i][j].getSpriteID() + ",");
                }
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
