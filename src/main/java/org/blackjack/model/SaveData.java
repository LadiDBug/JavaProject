package org.blackjack.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class saves and manages data of the player
 *
 * @author Diana Pamfile
 */
public class SaveData {

    /**
     * The path of the file where the data is saved.
     */
    private String path;

    /**
     * Constructor that initializes the path of the file
     */
    public SaveData() {
        this.path = "src/main/data/userData.txt";
    }

    /**
     * Method that initializes the file with the data of the player.
     *
     * @param path
     * @param username
     */
    public void initializeFile(String path, String username) {
        try (FileWriter fw = new FileWriter(new File(path))) {
            fw.write("Username = " + username + "\n");
            fw.write("TotalGames = 0\n");
            fw.write("TotalWins = 0\n");
            fw.write("TotalLosses = 0\n");
            fw.write("TotalFiches = 10000\n");
            fw.write("Avatar = \n");
            fw.write("Level = 0\n");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that reads the data from the file.
     *
     * @param value
     * @return the value of the key, the data read.
     */
    public String readFromFile(String value) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(value)) {
                    return line.split(" = ")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that updates the data in the file.
     *
     * @param key
     * @param newValue
     */
    public void updateData(String key, String newValue) {
        try {
            List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(path)));

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(key)) {
                    lines.set(i, key + " = " + newValue);
                    break;
                }
            }

            Files.write(Paths.get(path), lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getters and setters for the data of the player.
     *
     * @return
     */

    public String getAvatar() {
        return readFromFile("Avatar");
    }

    public String getUsername() {
        return readFromFile("Username");
    }

    public int getLevel() {
        return Integer.parseInt(readFromFile("Level"));
    }

    public int getTotalGames() {
        return Integer.parseInt(readFromFile("TotalGames"));
    }

    public int getTotalWins() {
        return Integer.parseInt(readFromFile("TotalWins"));
    }

    public int getTotalLosses() {
        return Integer.parseInt(readFromFile("TotalLosses"));
    }

    public int getTotalFiches() {
        return Integer.parseInt(readFromFile("TotalFiches"));
    }

    public void setAvatar(String avatar) {
        updateData("Avatar", avatar);
    }

    public void setUsername(String username) {
        updateData("Username", username);
    }

    public void setTotalGames(int totalGames) {
        updateData("TotalGames", String.valueOf(totalGames));
    }

    public void setTotalWins(int totalWins) {
        updateData("TotalWins", String.valueOf(totalWins));
    }

    public void setTotalLosses(int totalLosses) {
        updateData("TotalLosses", String.valueOf(totalLosses));
    }

    public void setTotalFiches(int totalFiches) {
        updateData("TotalFiches", String.valueOf(totalFiches));
    }

    public void setLevel(int level) {
        updateData("Level", String.valueOf(level));
    }

    public String getPath() {
        return path;
    }

}

