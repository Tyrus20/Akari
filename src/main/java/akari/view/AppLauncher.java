package akari.view;

import akari.SamplePuzzles;
import akari.controller.AlternateMvcController;
import akari.controller.ControllerImpl;
import akari.model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    stage.setTitle("Akari");
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    Model model = new ModelImpl(library);
    AlternateMvcController controller = new ControllerImpl(model);
    MessageView messageView = new MessageView(controller);
    ControlView controlView = new ControlView(controller);
    PuzzleView puzzleView = new PuzzleView(controller);
    VBox gui = new VBox(puzzleView.render(), messageView.render(), controlView.render());
    gui.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    Scene scene = new Scene(gui, 600, 600);
    stage.setScene(scene);
    stage.show();
    ModelObserver observer =
        (Model model1) -> {
          gui.getChildren().clear();
          gui.getChildren().add(puzzleView.render());
          gui.getChildren().add(messageView.render());
          gui.getChildren().add(controlView.render());
        };
    model.addObserver(observer);
    // TODO: Create your Model, View, and Controller instances and launch your GUI
  }
}
