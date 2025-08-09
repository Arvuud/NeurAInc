package org.example;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.util.Random;

public class WekaExample {
    public static void main(String[] args) throws Exception {
        // Daten laden
        DataSource source = new DataSource("data/");
        Instances data = source.getDataSet();

        // Klassenattribut setzen (letztes Attribut)
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        // Klassifizierer erstellen und trainieren
        J48 tree = new J48();
        tree.buildClassifier(data);

        // Evaluierung
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));

        System.out.println(eval.toSummaryString());
    }
}
