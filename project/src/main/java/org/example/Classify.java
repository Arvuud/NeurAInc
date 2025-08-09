package org.example;

import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Classify {
    public void classify(String trainingData, String testData) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(trainingData));
            Instances train = new Instances(reader);
            train.setClassIndex(train.numAttributes() - 1);

            reader = new BufferedReader(new FileReader(testData));
            Instances test = new Instances(reader);
            test.setClassIndex(test.numAttributes() - 1);

            reader.close();

            J48 tree = new J48();
            tree.buildClassifier(train);
            Instances classified = new Instances(test);

            for (int i = 0; i < classified.numInstances(); i++) {
                double clsLabel = tree.classifyInstance(classified.instance(i));
                classified.instance(i).setClassValue(clsLabel);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/labeled"));
            writer.write(classified.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
