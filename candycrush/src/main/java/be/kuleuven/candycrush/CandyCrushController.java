package be.kuleuven.candycrush;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.effect.DropShadow;

import java.util.List;

public class CandyCrushController {
    public final Stage primaryStage;
    public CandyCrushModel model;
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
        //model.updateScore(value);

        // Update the score label
        updateScoreLabel();
    }

    public void startGame() {
        primaryStage.setTitle("Candy Crush");
        showLoginScene();
    }

    public void showLoginScene() {
        GridPane loginPane = new GridPane();
        this.model = new CandyCrushModel(model.boardSize);
        this.model.updateBoard();
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
        for (int i = 0; i < model.candyBoard.boardSize.rows(); i++) {
            for (int j = 0; j < model.candyBoard.boardSize.columns(); j++) {
                Position position = new Position(i, j, model.candyBoard.boardSize);
                final Candy candyValue = model.candyBoard.getCellAt(position);

                Node candyShape = makeCandyShape(position, candyValue);
                candyShape.setOnMouseClicked(event -> handleCandyClick(position, candyValue));

                candyGridPane.add(candyShape, j, i);
            }
        }

        // Setting up the score label and adding it to the grid pane
        scoreLabel = new Label();
        updateScoreLabel();
        candyGridPane.add(scoreLabel, model.candyBoard.boardSize.columns()+1, 0);

        // Adding a back button to return to the login scene
        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());
        candyGridPane.add(backButton, model.candyBoard.boardSize.columns()+1, 1);

        // Adding a button to maximize the score
        Button maximizeScoreButton = new Button("Maximize Score");
        maximizeScoreButton.setOnAction(event -> maximizeScore());
        candyGridPane.add(maximizeScoreButton, model.candyBoard.boardSize.columns()+1, 2);

        // Adding a button to reset the game with a new model
        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(event -> resetGame());
        candyGridPane.add(resetButton, model.candyBoard.boardSize.columns()+1, 4);

        primaryStage.setScene(new Scene(candyGridPane, 400, 400));
        primaryStage.setTitle("Candy Crush - " + model.getPlayerName());
    }
    public void resetGame() {
        // Create a new CandyCrushModel instance
        model = new CandyCrushModel(model.boardSize);
        // Update the UI to reflect the new model
        updateCandyGridUI();
        // Update the score label
        updateScoreLabel();
        model.updateBoard();
        showCandyCrushScene();
    }
    public void maximizeScore(){
        // Call the maximizeScore method
        model.maximizeScore();
        // Output the best sequence of moves
        System.out.println("Best sequence of moves to maximize score:");
        for (List<Position> positions : model.bestSequence) {
            int i=0;
            for (Position pos : positions) {
                System.out.print("(r" + pos.row() + ", c" + pos.column() + ")");
                if (i==0)System.out.print("<->");
                i++;
            }
            System.out.print("|");
        }
        System.out.println("max score: "+model.maxScore);
        updateCandyGridUI();
        updateScore(model.maxScore);
        updateScoreLabel();
        Label instructionlabel = new Label("Volg de instructies in de terminal voor een maximale score");
        candyGridPane.add(instructionlabel, model.candyBoard.boardSize.columns()+1, 3);
    }

    public void handleCandyClick(Position position, Candy clickedCandy) {
        // Check if there's already a selected position
        if (model.getSelectedPosition() == null) {
            // If no candy is selected yet, set the current position as selected
            model.setSelectedPosition(position);
        } else {
            // Candy already selected, so swap candies and check for a match
            Position selectedPosition = model.getSelectedPosition();

            // Check if the selected position is adjacent to the clicked position
            if (isAdjacent(selectedPosition, position)) {
                // Perform the swap
                model.swapCandies(selectedPosition.row(),selectedPosition.column(),position.row(), position.column());

                // Check if the swap results in any matches
                if (model.updateBoard()) {
                    // If matches found, update the score and UI
                    updateScore(model.getScore());
                    updateCandyGridUI();
                } else {
                    // If no matches found, swap back the candies to their original positions
                    model.swapCandies(selectedPosition.row(),selectedPosition.column(),position.row(), position.column());
                }
            }

            // Reset selected position after handling the click
            model.setSelectedPosition(null);
        }
    }
    public boolean isAdjacent(Position position1, Position position2) {
        int rowDiff = Math.abs(position1.row() - position2.row());
        int colDiff = Math.abs(position1.column() - position2.column());
        // Two positions are adjacent if their row or column difference is 1 and the other difference is 0
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }


    // Method to extract the value from the Candy object
    private int getValueFromCandy(Candy candy) {
        return switch (candy) {
            case NormalCandy ignored -> 1; // Returns the color value for NormalCandy

            case ChocoCrunch chocoCrunch -> 4; // Specific value for ChocoCrunch

            case CaramelBlast caramelBlast -> 5; // Specific value for CaramelBlast

            case LemonDrop lemonDrop -> 6; // Specific value for LemonDrop

            case BerryBurst berryBurst -> 7; // Specific value for BerryBurst

            case null -> {
                assert false;
                throw new IllegalArgumentException("Unknown candy type: " + candy.getClass());
            }
        };
    }

    public void updateCandyGridUI() {
        if (candyGridPane == null) {
            throw new IllegalStateException("candyGridPane is not initialized");
        }

        // Clear the existing UI components in the GridPane
        candyGridPane.getChildren().clear();

        // Iterate over each position in the grid and add the corresponding shape
        for (int i = 0; i < model.boardSize.rows(); i++) {
            for (int j = 0; j < model.boardSize.columns(); j++) {
                final Position position = new Position(i, j, model.boardSize);
                final Candy candyValue = model.candyBoard.getCellAt(position);

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
        candyGridPane.add(scoreLabel, model.boardSize.rows()+1, 0);

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(event -> showLoginScene());
        candyGridPane.add(backButton, model.boardSize.rows() + 1, 1);

        // Adding a button to maximize the score
        Button maximizeScoreButton = new Button("Maximize Score");
        maximizeScoreButton.setOnAction(event -> maximizeScore());
        candyGridPane.add(maximizeScoreButton, model.candyBoard.boardSize.columns()+1, 2);

        // Adding a button to reset the game with a new model
        Button resetButton = new Button("Reset Game");
        resetButton.setOnAction(event -> resetGame());
        candyGridPane.add(resetButton, model.candyBoard.boardSize.columns()+1, 4);
    }

    public Node makeCandyShape(Position position, Candy candy) {
        switch (candy) {
            case null-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                rectangle.setFill(Color.WHITE);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case NormalCandy normalCandy -> {
                return getCircle(position, normalCandy);
            }
            case ChocoCrunch chocoCrunch-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                DropShadow dropShadow = new DropShadow(10, Color.CHOCOLATE);
                rectangle.setEffect(dropShadow);
                rectangle.setFill(Color.CHOCOLATE);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case CaramelBlast caramelBlast-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                DropShadow dropShadow = new DropShadow(20, Color.FIREBRICK);
                rectangle.setEffect(dropShadow);
                rectangle.setFill(Color.BISQUE);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case LemonDrop lemonDrop-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                DropShadow dropShadow = new DropShadow(10, Color.LIGHTGOLDENRODYELLOW);
                rectangle.setEffect(dropShadow);
                rectangle.setFill(Color.YELLOW);
                rectangle.setX(position.row() - 25); // Adjusting position for centering
                rectangle.setY(position.column() - 25);
                return rectangle;
            }
            case BerryBurst berryBurst-> {
                Rectangle rectangle = new Rectangle(50, 50); // Width and height 50
                DropShadow dropShadow = new DropShadow(10, Color.DARKBLUE);
                rectangle.setEffect(dropShadow);
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
        // Add drop shadow effect to the circle
        DropShadow dropShadow = new DropShadow(20, Color.BLACK);
        circle.setEffect(dropShadow);
        circle.setCenterX(position.row());
        circle.setCenterY(position.column());
        return circle;
    }
}
