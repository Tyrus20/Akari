package akari.view;

import akari.controller.AlternateMvcController;
import akari.model.CellType;
import akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PuzzleView implements FXComponent {
    private final AlternateMvcController controller;

    public PuzzleView(AlternateMvcController controller) {
        this.controller = controller;
    }

    @Override
    public Parent render() {
        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty();
        Puzzle puzzle = controller.getActivePuzzle();
        int height = puzzle.getHeight();
        int width = puzzle.getWidth();

        for (int i = 0; i < height; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(50);
            rowConstraints.setMinHeight(50);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int j = 0; j < width; j++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(50);
            columnConstraints.setMinWidth(50);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int r = 0; r < controller.getActivePuzzle().getHeight(); r++) {
            for (int c = 0; c < controller.getActivePuzzle().getWidth(); c++) {
                if (puzzle.getCellType(r, c) == CellType.CLUE) {
                    StackPane clue = new StackPane();
                    Label clueLabel = new Label(Integer.toString(puzzle.getClue(r, c)));
                    clueLabel.setTextFill(Color.WHITE);
                    clue.getChildren().add(clueLabel);
                    gridPane.add(clue, c, r);
                    if (controller.isClueSatisfied(r, c)) {
                        clue.setStyle("-fx-background-color: green");
                    } else {
                        clue.setStyle("-fx-background-color: black");
                    }
                } else if (puzzle.getCellType(r, c) == CellType.WALL) {
                    StackPane wall = new StackPane();
                    wall.setStyle("-fx-background-color: black");
                    gridPane.add(wall, c, r);
                } else {
                    Button corridor = new Button();
                    corridor.setMinWidth(50);
                    corridor.setMinHeight(50);
                    corridor.setMaxWidth(50);
                    corridor.setMaxHeight(50);
                    gridPane.add(corridor, c, r);

                    int r2 = r;
                    int c2 = c;
                    corridor.setOnAction(actionEvent -> controller.clickCell(r2, c2));
                    if (controller.isLit(r2, c2)) {
                        corridor.setStyle("-fx-background-color: khaki");
                    }
                    if (controller.isLamp(r2, c2)) {
                        Image lightBulb = new Image("light-bulb.png");
                        ImageView imageView = new ImageView(lightBulb);
                        imageView.setFitHeight(25);
                        imageView.setFitWidth(25);
                        corridor.setGraphic(imageView);
                        if (controller.isLampIllegal(r, c)) {
                            corridor.setStyle("-fx-background-color: red");
                        }
                    }
                }
            }
        }
        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        return gridPane;
    }
}
