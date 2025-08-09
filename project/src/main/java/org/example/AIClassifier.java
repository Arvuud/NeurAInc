package org.example;

import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

public class AIClassifier {
    public int classify(String trainingData, String inputString) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(trainingData));
            Instances train = new Instances(reader);
            train.setClassIndex(train.numAttributes() - 1);
            reader.close();

            J48 tree = new J48();
            tree.buildClassifier(train);

            Instance instance = createInstanceFromString(inputString, train);

            double clsLabel = tree.classifyInstance(instance);
            return (int) clsLabel;

        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Hilfsmethode - musst du je nach Datenformat implementieren
    private Instance createInstanceFromString(String input, Instances dataset) {
        // Diese Methode musst du entsprechend deinem Datenformat implementieren
        // Beispiel für einfachen Fall:
        Instance instance = new DenseInstance(dataset.numAttributes());
        instance.setDataset(dataset);
        // Hier würdest du die Werte aus dem inputString parsen und setzen
        return instance;
    }
}
