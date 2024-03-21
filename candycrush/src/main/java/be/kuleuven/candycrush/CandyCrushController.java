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

        // Loop through the grid to place each candy
        for (int i = 0; i < model.boardSize.rows(); i++) {
            for (int j = 0; j < model.boardSize.columns(); j++) {
                Position position = new Position(i, j, model.boardSize);
                final Candy candyValue = model.getGridValue(position);

                Node candyShape = makeCandyShape(position, candyValue);
                candyShape.setOnMouseClicked(event -> handleCandyClick(position, candyValue));

                candyGridPane.add(candyShape, j, i);
            }
        }

        // Setting up the score label and adding it to the grid pane
        scoreLabel = new Label();
        updateScoreLabel();
        candyGridPane.add(scoreLabel, model.boardSize.columns()+1, 0); // Adjust position as needed

        // Adding a back button to return to the login scene
        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());
        candyGridPane.add(backButton, model.boardSize.columns()+1, 1); // Adjust position as needed

        primaryStage.setScene(new Scene(candyGridPane, 400, 400));
        primaryStage.setTitle("Candy Crush - " + model.getPlayerName());
    }


    public void handleCandyClick(Position position, Candy clickedCandy) {

        // Get same neighbor positions
        Iterable<Position> neighborPositions = model.getSameNeighbourPositions(position);

        for (Position neighborPosition : neighborPositions) {
            Candy randomCandy = model.createRandomCandy(); // Generate a random candy
            model.updateGridValue(neighborPosition, randomCandy); // Update the grid value with the random candy
        }

        // Change the value of the clicked cell itself to a random value
        model.updateGridValue(position, model.createRandomCandy());

        // Update the score based on the value of the clicked candy
        int value = getValueFromCandy(clickedCandy);
        int count = 0;
        for (Position countpos : neighborPositions) {
            count++;
        }
        if (count==2) {
            updateScore(value);
        } else if (count == 3) {
            updateScore(value+1);
        } else if (count>3) {
            updateScore(value*2);
        }

        // Update the UI to reflect the changes
        updateCandyGridUI(model.getGrid());
    }


    // Method to extract the value from the Candy object
    private int getValueFromCandy(Candy candy) {
        if (candy instanceof NormalCandy normalCandy) {
            return 1; // Returns the color value for NormalCandy
        } else if (candy instanceof ChocoCrunch) {
            return 4; // Specific value for ChocoCrunch
        } else if (candy instanceof CaramelBlast) {
            return 5; // Specific value for CaramelBlast
        } else if (candy instanceof LemonDrop) {
            return 6; // Specific value for LemonDrop
        } else if (candy instanceof BerryBurst) {
            return 7; // Specific value for BerryBurst
        } else {
            throw new IllegalArgumentException("Unknown candy type: " + candy.getClass());
        }
    }

    public void updateCandyGridUI(Iterable<Candy> grid) {
        if (candyGridPane == null) {
            throw new IllegalStateException("candyGridPane is not initialized");
        }

        // Clear the existing UI components in the GridPane
        candyGridPane.getChildren().clear();

        // Iterate over each position in the grid and add the corresponding shape
        for (int i = 0; i < model.boardSize.rows(); i++) {
            for (int j = 0; j < model.boardSize.columns(); j++) {
                final Position position = new Position(i, j, model.boardSize);
                final Candy candyValue = model.getGridValue(position);

                // Use makeCandyShape to create a shape for the candy
                Node candyShape = makeCandyShape(position, candyValue);
                candyShape.setOnMouseClicked(event -> handleCandyClick(position, candyValue));

                // Add the shape to the GridPane
                candyGridPane.add(candyShape, j, i);
            }
        }

        // Re-add the score label and back button to the UI
        addUIComponentsBack();
    }
    private void addUIComponentsBack() {
        // Assuming scoreLabel is already initialized and updated
        candyGridPane.add(scoreLabel, model.boardSize.rows()+1, 0); // Adjust position as needed

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());
        candyGridPane.add(backButton, model.boardSize.rows() + 1, 1); // Adjust position as needed
    }

    public Node makeCandyShape(Position position, Candy candy) {
        switch (candy) {
            case NormalCandy normalCandy -> {
                return getCircle(position, normalCandy);
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

    private static Circle getCircle(Position position, NormalCandy normalCandy) {
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
}
