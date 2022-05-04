import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Perceptron {
    private ArrayList<Observation> testArray, trainingArray;
    private double[] weights;
    private double theta, alpha;

    public Perceptron(double alpha) {

        testArray = Service.parse("C:\\nauka\\NAI2\\Test-set.txt");

        trainingArray = Service.parse("C:\\nauka\\NAI2\\Train-set.txt");

        randomizeWeightsAndTheta();

        this.alpha = alpha;
    }

    private void randomizeWeightsAndTheta() {

        theta = (Math.random() * 2) - 1;

        weights = new double[Service.length];

        for (int i = 0; i < weights.length; i++)
            weights[i] = (Math.random() * 2) - 1;
    }


    private int evaluate(Observation observation) {

        double net = theta;

        for (int i = 0; i < observation.getValues().length; i++)
            net += observation.getValues()[i] * weights[i];

        return (net >= 0 ? 1 : 0);
    }


    public void learn() {

        for (int i = 0; i < trainingArray.size(); i++) {

            int summaryError = 0;

            for (Observation observation : trainingArray) {

                int writtenDeterminant = evaluate(observation), actualDeterminant = 0;

                for (Map.Entry<String, Integer> entry : Service.dataValues.entrySet())

                    if (entry.getKey().equals(observation.getName()))

                        actualDeterminant = entry.getValue();

                int error = actualDeterminant - writtenDeterminant;

                summaryError += error;

                for (int j = 0; j < Service.length; j++)

                    weights[j] += alpha * observation.getValues()[j] * error;

                theta += error * alpha;
            }
            if (summaryError == 0) break;
        }
    }


    public void run(boolean flag) {

        int actualDeterminant;

        double total = 0, found = 0;

        for (Observation observation : testArray) {

            actualDeterminant = evaluate(observation);

            String actualName = null;

            for (Map.Entry<String, Integer> entry : Service.dataValues.entrySet())
                if (actualDeterminant == entry.getValue())
                    actualName = entry.getKey();

            if (flag && observation.getName().equals(actualName)) found++;
            total++;

            System.out.println((flag ? observation.getName() : "null") + Arrays.toString(observation.getValues()) + " -> " + actualName);
        }
        if (flag) System.out.println("Accuracy: " + total / found);
    }

    public ArrayList<Observation> getTestArray() {
        return testArray;
    }

    public void setTestArray(ArrayList<Observation> testArray) {
        this.testArray = testArray;
    }

    public ArrayList<Observation> getTrainingArray() {
        return trainingArray;
    }

    public void setTrainingArray(ArrayList<Observation> trainingArray) {
        this.trainingArray = trainingArray;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}