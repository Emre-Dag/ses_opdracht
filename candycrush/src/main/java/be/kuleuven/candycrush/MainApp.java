package be.kuleuven.candycrush;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BoardSize boardsize = new BoardSize(5,5);
        CandyCrushModel model = new CandyCrushModel(boardsize);
        CandyCrushController controller = new CandyCrushController(primaryStage,model);
        controller.startGame();
    }
}