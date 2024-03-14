package be.kuleuven.candycrush;
import be.kuleuven.getSameNeighboursIds;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandyCrushController {
    public final Stage primaryStage;
    public final CandyCrushModel model;
    public GridPane candyGridPane;
    public Label scoreLabel;

    public CandyCrushController(Stage primaryStage, CandyCrushModel model) {
        this.primaryStage = primaryStage;
        this.model = model;
    }

    // Method to update the score label
    public void updateScoreLabel() {
        scoreLabel.setText("Score: " + model.getScore());
    }

    // Method to update the score
    public void updateScore(int value) {
        // Update the score in the model
        model.updateScore(value);

        // Update the score label
        updateScoreLabel();
    }

    public void startGame() {
        primaryStage.setTitle("Candy Crush");
        showLoginScene();
    }

    public void showLoginScene() {
        GridPane loginPane = new GridPane();
        model.score = 0;
        TextField nameField = new TextField();
        Button loginButton = new Button("Login");
        model.setPlayerName(""); // Reset player name
        loginPane.add(nameField, 0, 0);
        loginPane.add(loginButton, 0, 1);

        loginButton.setOnAction(event -> {
            String playerName = nameField.getText().trim(); // Trim to remove leading/trailing spaces
            if (!playerName.isEmpty()) {
                model.setPlayerName(playerName); // Set the player name in the model
                showCandyCrushScene();
            } else {
                // Show an alert or error message indicating that the name cannot be empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your name.");
                alert.showAndWait();
            }
        });

        primaryStage.setScene(new Scene(loginPane, 300, 150));
        primaryStage.show();
    }

    public void showCandyCrushScene() {
        int rows = 4;
        int cols = 4;

        candyGridPane = new GridPane();

        // Get the grid from the model
        Iterable<Integer> grid = model.getGrid();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                final int candyValue = model.getGridValue(grid, cols, row, col);

                Button candyButton = new Button(String.valueOf(candyValue));
                candyButton.setOnAction(event -> handleCandyClick(row, col, candyValue, grid));

                candyGridPane.add(candyButton, j, i);
            }
        }
        scoreLabel = new Label();
        updateScoreLabel();

        candyGridPane.add(scoreLabel, 6, 1);
        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());

        candyGridPane.add(backButton, 6, 0);

        primaryStage.setScene(new Scene(candyGridPane, 400, 400));
        primaryStage.setTitle("Candy Crush - " + model.getPlayerName());
    }

    public void handleCandyClick(int row, int col, int value, Iterable<Integer> grid) {
        // grid width and height
        int width = 4;
        int height = 4;

        // Index to check
        int indexToCheck = row * width + col;

        // Get the same neighbors indices
        Iterable<Integer> neighborIndices = getSameNeighboursIds.getSameNeighboursIds(grid, width, height, indexToCheck);

        List<Integer> neighborList = new ArrayList<>();
        neighborIndices.forEach(neighborList::add);

        // Change the value of the cell and its neighbors to a random value
        Random rd = new Random();
        int min = 0;
        int max = 3;

        for (int neighborIndex : neighborList) {
            // Update the grid value in the model
            model.updateGridValue(neighborIndex, rd.nextInt(max - min + 1) + min);
        }

        // Change the value of the cell itself to a random value
        model.updateGridValue(indexToCheck, rd.nextInt(max - min + 1) + min);

        // Update the score based on the value of the clicked candy
        updateScore(value);

        // Update the UI to reflect the changes
        updateCandyGridUI(model.getGrid());
    }

    public void updateCandyGridUI(Iterable<Integer> gridList) {
        int rows = 4;
        int cols = 4;

        if (candyGridPane == null) {
            // Handle the case when candyGridPane is not initialized
            throw new IllegalStateException("candyGridPane is not initialized");
        }

        // Clear the existing buttons
        candyGridPane.getChildren().clear();

        // Add the updated buttons
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;
                final int candyValue = model.getGridValue(gridList, cols, row, col);

                Button candyButton = new Button(String.valueOf(candyValue));
                candyButton.setOnAction(event -> handleCandyClick(row, col, candyValue, gridList));

                // Add the button to the candyGridPane
                candyGridPane.add(candyButton, j, i);
            }
        }

        // Add the back button again
        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());

        // Add the back button to the candyGridPane
        candyGridPane.add(backButton, 6, 0);

        // Add the Scores label again
        scoreLabel = new Label();
        updateScoreLabel();

        candyGridPane.add(scoreLabel, 6, 1);
    }
}
