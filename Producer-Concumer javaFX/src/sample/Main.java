package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    final double circleRadius = 30;
    static List<Circle> Qs = new ArrayList<>();
    static List<Circle> Ms = new ArrayList<>();
    static List<Text> QsLabels = new ArrayList<>();
    List<String> items = new ArrayList<>();
    ArrayList<ArrayList<Integer>> Queues = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<ArrayList<Integer>>> Machines = new ArrayList<ArrayList<ArrayList<Integer>>>();
    String message;
    Color color ;
    double x_pos;
    double y_pos;
    boolean isAddQClicked = false;
    boolean isAddMClicked = false;
    static int qCounter = 0;
    static int mCounter = 1;
    boolean isFirstChosen = false;
    boolean isSecondChosen = false;
    String selectedSecond;
    String selectedFirst;

    Button addQ_Button = new Button("Add Q");
    Button addM_Button = new Button("Add M");
    Button connectButton = new Button("Connect");
    Button simulateButton = new Button("Simulate");
    Button replayButton = new Button("Replay");
    Button clearButton = new Button("Clear");
    ComboBox chooseFirst = new ComboBox(FXCollections.observableArrayList(items));
    ComboBox chooseSecond = new ComboBox(FXCollections.observableArrayList(items));
    Text tempText = new Text();
    Stage primaryStage;
    Pane layout = new Pane();
    VBox buttons = new VBox(10);
    Facade f = new Facade();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simulation Program");
        connectButton.setDisable(true);
        chooseFirst.setValue("from");
        chooseSecond.setValue("to");
        //Generating circles on clicking
        layout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                x_pos = mouseEvent.getSceneX();
                y_pos = mouseEvent.getSceneY();
                if (isAddMClicked) {
                    generateCircle(x_pos, y_pos, Color.VIOLET);
                    isAddMClicked = false;
                } else if (isAddQClicked) {
                    generateCircle(x_pos, y_pos, Color.YELLOW);
                    isAddQClicked = false;
                }
            }
        });

        //Button : Add Q
        addQ_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isAddMClicked = false;
                isAddQClicked = true;
            }
        });

        //Button : Add M
        addM_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isAddQClicked = false;
                isAddMClicked = true;
            }
        });

        //Button : Connect
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isFirstChosen = false;
                isSecondChosen = false;
                connectButton.setDisable(true);
                if (!selectedFirst.equals(selectedSecond) && !(selectedFirst.charAt(0) == selectedSecond.charAt(0))) {
                    Circle circle1 = new Circle();
                    Circle circle2 = new Circle();
                    if (selectedFirst.charAt(0) == 'Q') {
                        int Q_id = Integer.parseInt(String.valueOf(selectedFirst.charAt(1)));
                        int M_id = Integer.parseInt(String.valueOf(selectedSecond.charAt(1))) - 1;
                        circle1 = Qs.get(Q_id);
                        circle2 = Ms.get(M_id);
                        Queues.get(Q_id).add(M_id);
                        Machines.get(M_id).get(1).add(Q_id);
                        generateLine(circle1, circle2);
                    } else if (selectedFirst.charAt(0) == 'M') {
                        int M_id = Integer.parseInt(String.valueOf(selectedFirst.charAt(1))) - 1;
                        int Q_id = Integer.parseInt(String.valueOf(selectedSecond.charAt(1)));
                        circle1 = Ms.get(M_id);
                        circle2 = Qs.get(Q_id);
                        if (Machines.get(M_id).get(0).isEmpty()) {
                            Machines.get(M_id).get(0).add(Q_id);
                            generateLine(circle1, circle2);
                        }
                    }
                }
            }
        });

        //Button : Choose First
        chooseFirst.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedFirst = String.valueOf(chooseFirst.getValue());
                isFirstChosen = true;
                if (isFirstChosen && isSecondChosen)
                    connectButton.setDisable(false);
            }
        });

        //Button : Choose Second
        chooseSecond.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedSecond = String.valueOf(chooseSecond.getValue());
                isSecondChosen = true;
                if (isFirstChosen && isSecondChosen)
                    connectButton.setDisable(false);
            }
        });

        //Button : Simulate
        simulateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                f.sendData(Machines, Queues);
            }
        });

        //Button : Replay
        replayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                f.replay(Machines,Queues);
            }
        });

        //Button : Clear
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                qCounter = 0;
                mCounter = 1;
                isAddMClicked = false;
                isAddQClicked = false;
                Ms.clear();
                Qs.clear();
                items.clear();
                Machines.clear();
                Queues.clear();
                layout.getChildren().clear();
                layout.getChildren().add(buttons);
                chooseFirst.setItems(FXCollections.observableArrayList(items));
                chooseSecond.setItems(FXCollections.observableArrayList(items));
                isFirstChosen = false;
                isSecondChosen = false;
                connectButton.setDisable(true);
            }
        });

        buttons.setPrefWidth(75);
        addQ_Button.setMinWidth(buttons.getPrefWidth());
        addM_Button.setMinWidth(buttons.getPrefWidth());
        connectButton.setMinWidth(buttons.getPrefWidth());
        chooseFirst.setMinWidth(buttons.getPrefWidth());
        chooseSecond.setMinWidth(buttons.getPrefWidth());
        simulateButton.setMinWidth(buttons.getPrefWidth());
        replayButton.setMinWidth(buttons.getPrefWidth());
        clearButton.setMinWidth(buttons.getPrefWidth());
        buttons.getChildren().addAll(addQ_Button,addM_Button,connectButton, chooseFirst, chooseSecond, simulateButton,replayButton, clearButton);
        layout.getChildren().add(buttons);
        this.primaryStage.setScene(new Scene(layout, 1000, 500,Color.rgb(188,184,202)));
        this.primaryStage.show();
    }

    public void generateLine(Circle circle1, Circle circle2) {
        double angleFirst = angleBetween(circle1.getCenterX(), circle1.getCenterY(), circle2.getCenterX(), circle2.getCenterY());
        double angleSecond = angleBetween(circle2.getCenterX(), circle2.getCenterY(), circle1.getCenterX(), circle1.getCenterY());
        double[] pointFrom = getPointOnCircle(circle1.getCenterX(), circle1.getCenterY(), angleFirst);
        double[] pointTo = getPointOnCircle(circle2.getCenterX(), circle2.getCenterY(), angleSecond);
        Line line = new Line(pointFrom[0], pointFrom[1], pointTo[0], pointTo[1]);
        layout.getChildren().add(line);
        chooseFirst.setValue(null);
        chooseSecond.setValue(null);
        connectButton.setDisable(true);
    }

    public double angleBetween(double x_from, double y_from, double x_to, double y_to) {
        double deltaX = x_to - x_from;
        double deltaY = y_to - y_from;
        // Calculate the angle...
        double rotation = -Math.atan2(deltaX, deltaY);
        rotation = Math.toRadians(Math.toDegrees(rotation) + 180);
        return rotation;
    }

    public double[] getPointOnCircle(double x_center, double y_center, double radians) {
        radians = radians - Math.toRadians(90.0); // 0 becomes the top
        // Calculate the outter point of the line
        double xPos = Math.round((float) (x_center + Math.cos(radians) * circleRadius));
        double yPos = Math.round((float) (y_center + Math.sin(radians) * circleRadius));

        return new double[]{xPos + circleRadius, yPos + circleRadius};
    }

    public void generateCircle(double center_x, double center_y, Color color) {
        Circle circle = new Circle();
        StackPane stackPane = new StackPane();
        Text text = new Text();
        stackPane.setLayoutX(center_x - circleRadius);
        stackPane.setLayoutY(center_y - circleRadius);
        circle.setCenterX(center_x - circleRadius);
        circle.setCenterY(center_y - circleRadius);
        circle.setRadius(circleRadius);
        circle.setFill(color);
        if (isAddMClicked){
            text.setText("M" + mCounter++);
            circle.setId(text.getText());
            Ms.add(circle);
            Machines.add(new ArrayList<ArrayList<Integer>>());
            int M_id = Integer.parseInt(String.valueOf(circle.getId().charAt(1))) - 1;
            Machines.get(M_id).add(new ArrayList<Integer>());
            Machines.get(M_id).add(new ArrayList<Integer>());
            items.add(circle.getId());
        } else if (isAddQClicked){
            text.setTextAlignment(TextAlignment.CENTER);
            text.setText("Q" + qCounter + "\n" + "0");
            circle.setId(text.getText());
            Qs.add(circle);
            QsLabels.add(text);
            Queues.add(new ArrayList<Integer>());
            items.add("Q" + qCounter++);
        }

        chooseFirst.setItems(FXCollections.observableArrayList(items));
        chooseSecond.setItems(FXCollections.observableArrayList(items));
        stackPane.getChildren().addAll(circle,text);
        layout.getChildren().add(stackPane);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static void main(String[] args) { launch(args); }
}
