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

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

        candyGridPane = new GridPane();

        // Get the grid from the model
        Iterable<Integer> grid = model.getGrid();

        for (int i = 0; i < model.boardSize.rows(); i++) {
            for (int j = 0; j < model.boardSize.columns(); j++) {
                Position position = new Position(i,j,model.boardSize);
                final int candyValue = model.getGridValue(grid, position);

                Button candyButton = new Button(String.valueOf(candyValue));
                candyButton.setOnAction(event -> handleCandyClick(position, candyValue, grid));

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

    public void handleCandyClick(Position position, int value, Iterable<Integer> grid) {
        // Index om te controleren
        int indexToCheck = position.toIndex();

        // Haal de indices van dezelfde buren op
        Iterable<Integer> neighborIndices = getSameNeighboursIds.getSameNeighboursIds(grid, model.boardSize.columns(), model.boardSize.rows(), indexToCheck);

        List<Integer> neighborList = new ArrayList<>();
        neighborIndices.forEach(neighborList::add);

        // Verander de waarde van de cel en zijn buren naar een willekeurige waarde
        Random rd = new Random();
        int min = 0;
        int max = 3;

        for (int neighborIndex : neighborList) {
            // Update de gridwaarde in het model
            model.updateGridValue(Position.fromIndex(neighborIndex, model.boardSize), rd.nextInt(max - min + 1) + min);
        }

        // Verander de waarde van de cel zelf naar een willekeurige waarde
        model.updateGridValue(position, rd.nextInt(max - min + 1) + min);

        // Update de score op basis van de waarde van de geklikte snoep
        updateScore(value);

        // Update de UI om de wijzigingen weer te geven
        updateCandyGridUI(model.getGrid());
    }

    public void updateCandyGridUI(Iterable<Integer> gridList) {
        if (candyGridPane == null) {
            // Handle the case when candyGridPane is not initialized
            throw new IllegalStateException("candyGridPane is not initialized");
        }

        // Clear the existing buttons
        candyGridPane.getChildren().clear();

        // Add the updated buttons
        for (int i = 0; i < model.boardSize.rows(); i++) {
            for (int j = 0; j < model.boardSize.columns(); j++) {
                Position position = new Position(i,j,model.boardSize);
                final int candyValue = model.getGridValue(gridList, position);

                Button candyButton = new Button(String.valueOf(candyValue));
                candyButton.setOnAction(event -> handleCandyClick(position, candyValue, gridList));

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
    public Node makeCandyShape(Position position, Candy candy) {
        switch (candy) {
            case NormalCandy normalCandy -> {
                int color = normalCandy.color();
                Color circleColor;
                switch (color) {
                    case 0 -> circleColor = Color.RED;
                    case 1 -> circleColor = Color.BLUE;
                    case 2 -> circleColor = Color.GREEN;
                    case 3 -> circleColor = Color.YELLOW;
                    default -> throw new IllegalArgumentException("Invalid color value: " + color);
                }
                Circle circle = new Circle(25); // Radius 25
                circle.setFill(circleColor);
                circle.setCenterX(position.row());
                circle.setCenterY(position.column());
                return circle;
            }
            case ChocoCrunch chocoCrunch-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                rectangle.setFill(Color.BROWN);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case CaramelBlast caramelBlast-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                rectangle.setFill(Color.ORANGE);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case LemonDrop lemonDrop-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                rectangle.setFill(Color.YELLOW);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case BerryBurst berryBurst-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                rectangle.setFill(Color.PURPLE);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            default -> throw new IllegalArgumentException("Invalid candy type.");
        }
    }
}
