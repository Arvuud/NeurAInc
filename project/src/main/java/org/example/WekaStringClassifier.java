package org.example;

import weka.core.Instances;
import weka.classifiers.Evaluation;

import java.util.Random;

import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;

public class WekaStringClassifier {

    private MultilayerPerceptron neuralNetwork;
    private StringToWordVector stringFilter;
    private Instances trainingData;
    private boolean isTrained = false;

    public void trainModel(String csvFilePath) throws Exception {
        System.out.println("Lade Trainingsdaten...");

        // 1. CSV-Datei laden
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(csvFilePath));
        Instances data = loader.getDataSet();

        // 2. Klassenattribut setzen (letzte Spalte = malicious/positive)
        data.setClassIndex(data.numAttributes() - 1);

        System.out.println("Anzahl Instanzen: " + data.numInstances());
        System.out.println("Anzahl Attribute: " + data.numAttributes());

        // 3. String zu Wort-Vektor konvertieren
        stringFilter = new StringToWordVector();
        stringFilter.setInputFormat(data);

        // Konfiguration des StringToWordVector Filters
        stringFilter.setWordsToKeep(1000); // Top 1000 Wörter behalten
        stringFilter.setMinTermFreq(1);     // Minimale Worthäufigkeit
        stringFilter.setLowerCaseTokens(true); // Kleinbuchstaben
        stringFilter.stemmerTipText();  // Stopwörter entfernen

        // Filter anwenden
        Instances filteredData = Filter.useFilter(data, stringFilter);

        System.out.println("Nach Filterung - Anzahl Features: " + filteredData.numAttributes());

        // 4. Neural Network konfigurieren
        neuralNetwork = new MultilayerPerceptron();

        // Netzwerk-Parameter
        neuralNetwork.setLearningRate(0.3);
        neuralNetwork.setMomentum(0.2);
        neuralNetwork.setTrainingTime(500); // Epochen
        neuralNetwork.setHiddenLayers("a"); // 'a' = (Anzahl Attribute + Anzahl Klassen) / 2

        // 5. Modell trainieren
        System.out.println("Trainiere Neural Network...");
        neuralNetwork.buildClassifier(filteredData);

        // 6. Modell evaluieren (10-fold Cross Validation)
        Evaluation eval = new Evaluation(filteredData);
        eval.crossValidateModel(neuralNetwork, filteredData, 10, new Random(1));

        System.out.println("\n=== Evaluierungsergebnisse ===");
        System.out.println("Korrekt klassifizierte Instanzen: " +
                String.format("%.2f%%", eval.pctCorrect()));
        System.out.println("Genauigkeit: " + String.format("%.4f", eval.precision(0)));
        System.out.println("Recall: " + String.format("%.4f", eval.recall(0)));
        System.out.println("F-Measure: " + String.format("%.4f", eval.fMeasure(0)));

        System.out.println("\n=== Confusion Matrix ===");
        System.out.println(eval.toMatrixString());

        trainingData = filteredData;
        isTrained = true;

        System.out.println("Training erfolgreich abgeschlossen!");
    }

    public String classifyString(String inputString) throws Exception {
        if (!isTrained) {
            throw new IllegalStateException("Modell muss erst trainiert werden!");
        }

        // 1. Neue Instanz für Klassifikation erstellen
        Instance newInstance = new DenseInstance(2);

        // 2. Attribute aus Trainingsdaten verwenden
        Instances testData = new Instances(trainingData, 0);

        // 3. String-Attribut setzen
        newInstance.setValue(0, inputString);
        newInstance.setDataset(testData);

        // 4. Gleichen Filter wie beim Training anwenden
        stringFilter.input(newInstance);
        Instance filteredInstance = stringFilter.output();

        // 5. Klassifikation durchführen
        double prediction = neuralNetwork.classifyInstance(filteredInstance);

        // 6. Ergebnis zurückgeben
        return testData.classAttribute().value((int) prediction);
    }

    public double[] getClassificationProbabilities(String inputString) throws Exception {
        if (!isTrained) {
            throw new IllegalStateException("Modell muss erst trainiert werden!");
        }

        // Instanz erstellen und filtern (wie in classifyString)
        Instance newInstance = new DenseInstance(2);
        Instances testData = new Instances(trainingData, 0);
        newInstance.setValue(0, inputString);
        newInstance.setDataset(testData);

        stringFilter.input(newInstance);
        Instance filteredInstance = stringFilter.output();

        // Wahrscheinlichkeiten für beide Klassen zurückgeben
        return neuralNetwork.distributionForInstance(filteredInstance);
    }

    public static void main(String[] args) {
        try {
            WekaStringClassifier classifier = new WekaStringClassifier();

            // Training (ersetze mit deinem CSV-Pfad)
            classifier.trainModel("data/training_data.csv");

            // Test-Klassifikationen
            String[] testStrings = {
                    "This is a normal message",
                    "Click here to win money now!!!",
                    "Meeting scheduled for tomorrow",
                    "URGENT: Your account will be suspended"
            };

            System.out.println("\n=== Test-Klassifikationen ===");
            for (String testString : testStrings) {
                String prediction = classifier.classifyString(testString);
                double[] probabilities = classifier.getClassificationProbabilities(testString);

                System.out.println("Text: \"" + testString + "\"");
                System.out.println("Vorhersage: " + prediction);
                System.out.println("Wahrscheinlichkeiten - Malicious: " +
                        String.format("%.3f", probabilities[0]) +
                        ", Positive: " + String.format("%.3f", probabilities[1]));
                System.out.println("---");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}