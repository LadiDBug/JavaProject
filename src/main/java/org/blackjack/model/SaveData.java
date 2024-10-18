package org.blackjack.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class saves and manages data of the player
 * 
 * @author Diana Pamfile
 */
public class SaveData {
	
	private static final String FILE_PATH = "src/resources/userData.json";
	
	// Method to save player data for the first time
    public static boolean savePlayerData(RealPlayer player) {
        String jsonData = generateJson(player);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(jsonData);
            return true; // Operation successful
        } catch (IOException e) {
            return false; // Operation failed
        }
    }

    // Method to update player data
    public static boolean updatePlayerData(RealPlayer player) {
        String existingData = getPlayerData();
        if (existingData != null) {
            // Update player stats
            String updatedData = existingData.replaceAll(
                "\"wonGames\": \\d+", "\"wonGames\": " + player.getWonGames())
                .replaceAll("\"totalGames\": \\d+", "\"totalGames\": " + player.getTotalGames())
                .replaceAll("\"totalFiches\": \\d+", "\"totalFiches\": " + player.getTotalFiches());
            
            return saveDataToFile(updatedData); // Save updated data
        }
        return false; // Player data not found
    }

    // Method to retrieve player data as a String
    public static String getPlayerData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder contentBuilder = new StringBuilder();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                contentBuilder.append(currentLine);
            }
            return contentBuilder.toString(); // Return all data
        } catch (IOException e) {
            return null; // Error occurred
        }
    }

    // Helper method to generate JSON data
    private static String generateJson(RealPlayer player) {
        return "{\n" +
                "\"username\": \"" + player.getUsername() + "\",\n" +
                "\"level\": " + player.getLevel() + ",\n" +
                "\"totalGames\": " + player.getTotalGames() + ",\n" +
                "\"wonGames\": " + player.getWonGames() + ",\n" +
                "\"lostGames\": " + player.getLostGames() + ",\n" +
                "\"totalFiches\": " + player.getTotalFiches() + ",\n" +
                "\"bet\": " + player.getBet() + "\n" +
                "}";
    }

    // Save data back to file
    private static boolean saveDataToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(data);
            return true; // Operation successful
        } catch (IOException e) {
            return false; // Operation failed
        }
    }

	
}

