package be.kuleuven.candycrush;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CandyCrushModel model = new CandyCrushModel();
        CandyCrushController controller = new CandyCrushController(primaryStage,model);
        controller.startGame();
    }
}

