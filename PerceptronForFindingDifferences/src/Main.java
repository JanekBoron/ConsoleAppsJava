import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Perceptron p = new Perceptron(0.1);
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true;

    public static void main(String[] args) {
        p.learn();
        p.run(true);
        setDataFromConsole();
    }


    public static void setDataFromConsole() {
        while (isRunning) {
            System.out.println("Enter attributes 'a1,a2,a3,a4,...' or 'exit': ");
            String line = scanner.nextLine();

            if (line.equals("exit")) {
                isRunning = false;
                break;
            }

            String[] attrsString = line.split(",");
            double[] attributes = new double[attrsString.length];

            try {
                for (int i = 0; i < attributes.length; i++)
                    attributes[i] = Double.parseDouble(attrsString[i].trim());
            } catch (Exception exception) {
                System.out.println("Entered data is not valid!\n");

                setDataFromConsole();
                break;
            }

            ArrayList<Observation> observations = new ArrayList<>();
            observations.add(new Observation(null, attributes));
            p.setTestArray(observations);
            p.run(false);
        }
    }
}