package org.example;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AIClassifier {
    public String classify(String inputString) {
        try {
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("data/training_data.csv"));
            loader.setFieldSeparator(";");  // Semikolon als Trenner
            Instances train = loader.getDataSet();
            train.setClassIndex(train.numAttributes() - 1);

            J48 tree = new J48();
            tree.buildClassifier(train);

            // Hier müsstest du den inputString in eine Instance umwandeln
            // Das hängt von deinem Datenformat ab
            Instance instance = createInstanceFromString(inputString, train);

            double clsLabel = tree.classifyInstance(instance);
            return train.classAttribute().value((int) clsLabel);

        } catch(Exception e) {
            e.printStackTrace();
            return "error"; // Fehlerfall
        }
    }

    // Hilfsmethode für Text-Input
    private Instance createInstanceFromString(String input, Instances dataset) {
        Instance instance = new DenseInstance(dataset.numAttributes());
        instance.setDataset(dataset);

        // Setze den Text in das erste Attribut
        instance.setValue(0, input);

        return instance;
    }
}
