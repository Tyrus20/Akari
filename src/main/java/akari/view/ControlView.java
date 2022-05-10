package akari.view;

import akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControlView implements FXComponent {
    private final AlternateMvcController controller;

    public ControlView(AlternateMvcController controller) {
        this.controller = controller;
    }

    @Override
    public Parent render() {
        HBox controls = new HBox();
        Button reset = new Button("Reset");
        Button previous = new Button("Previous");
        Label puzzleIndex =
                new Label(
                        " Puzzle "
                                + (controller.getActivePuzzleIndex() + 1)
                                + "/"
                                + (controller.getPuzzleLibrarySize())
                                + " ");
        Button next = new Button("Next");
        Button random = new Button("Random");
        reset.setOnAction(actionEvent -> controller.clickResetPuzzle());
        previous.setOnAction(actionEvent -> controller.clickPrevPuzzle());
        next.setOnAction(actionEvent -> controller.clickNextPuzzle());
        random.setOnAction(actionEvent -> controller.clickRandPuzzle());
        Font buttonFont = new Font(15);
        reset.setFont(buttonFont);
        previous.setFont(buttonFont);
        puzzleIndex.setFont(new Font(20));
        puzzleIndex.setTextFill(Color.BLACK);
        next.setFont(buttonFont);
        random.setFont(buttonFont);
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.getChildren().addAll(reset, previous, puzzleIndex, next, random);
        return controls;
    }
}