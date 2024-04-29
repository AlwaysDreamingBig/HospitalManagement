import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AutoAdaptiveLayout extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Label topLabel = new Label("Top");
        Label leftLabel = new Label("Left");
        Label centerLabel = new Label("Center");
        Label rightLabel = new Label("Right");
        Label bottomLabel = new Label("Bottom");

        root.setTop(topLabel);
        root.setLeft(leftLabel);
        root.setCenter(centerLabel);
        root.setRight(rightLabel);
        root.setBottom(bottomLabel);

        Scene scene = new Scene(root, 400, 300);

        // Binding sizes to the scene width and height
        topLabel.prefWidthProperty().bind(scene.widthProperty());
        bottomLabel.prefWidthProperty().bind(scene.widthProperty());
        leftLabel.prefHeightProperty().bind(scene.heightProperty().subtract(40));
        rightLabel.prefHeightProperty().bind(scene.heightProperty().subtract(40));
        centerLabel.prefWidthProperty().bind(scene.widthProperty().subtract(80));
        centerLabel.prefHeightProperty().bind(scene.heightProperty().subtract(80));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Auto Adaptive Layout Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
