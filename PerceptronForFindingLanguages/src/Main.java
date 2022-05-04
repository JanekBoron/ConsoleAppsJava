import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static ArrayList<Data> test, training;
    public static HashMap<String, Perceptron> identifiers;

    public static void main(String[] args) {
        test = Service.parse("C:\\nauka\\NAI\\NAI3\\Data\\Test");
        training = Service.parse("C:\\nauka\\NAI\\NAI3\\Data\\Train");
        identifiers = setIdentifiers();

        for (int i = 0; i < 10000; i++)
            for (Data trainingObs : training)
                for (Map.Entry<String, Perceptron> entry : identifiers.entrySet())
                    entry.getValue().learn(trainingObs, trainingObs.getLanguage(), entry.getKey());

        for (Data testObs : test)
            for (Map.Entry<String, Perceptron> entry : identifiers.entrySet())
                if (entry.getValue().evaluate(testObs) == 1) {
                    System.out.println(testObs.getLanguage() + " -> " + entry.getKey());
                    break;
                }

        Application.launch(args);
    }

    private static HashMap<String, Perceptron> setIdentifiers() {
        HashMap<String, Perceptron> identifiers = new HashMap<>();
        for (Data observation : training)
            identifiers.put(observation.getLanguage(), new Perceptron());
        return identifiers;
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();

        TextArea textArea = new TextArea();
        textArea.setPrefWidth(720);
        textArea.setPrefHeight(500);

        textArea.setPrefColumnCount(10);

        Button button = new Button("Get language");
        button.setPrefWidth(720);
        button.setOnAction(event -> {

            ArrayList<Data> observations = new ArrayList<>();
            observations.add(new Data("Language", textArea.getText()));

            for (Data observation : observations)
                for (Map.Entry<String, Perceptron> entry : identifiers.entrySet())
                    if (entry.getValue().evaluate(observation) == 1) {
                        System.out.println(observation.getLanguage() + " = " + entry.getKey());
                        break;
                    }
        });

        borderPane.setCenter(textArea);
        borderPane.setBottom(button);

        Scene scene = new Scene(borderPane, 720, 500);
        stage.setScene(scene);
        stage.show();
    }
}