package akari.view;

import akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MessageView implements FXComponent {
  private final AlternateMvcController controller;

  public MessageView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Label solved;
    if (controller.isSolved()) {
      solved = new Label("Puzzle Solved!");
      solved.setTextFill(Color.GREEN);
    } else {
      solved = new Label("Puzzle Unsolved");
      solved.setTextFill(Color.BLACK);
    }
    solved.setFont(new Font(20));
    VBox vBox = new VBox(solved);
    vBox.setAlignment(Pos.TOP_CENTER);
    return vBox;
  }
}
