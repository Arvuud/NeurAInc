package org.example;

public class Main {
    public static void main(String[] args) {
        final NeurAInc neurAInc = new NeurAInc();
        AIClassifier aiClassifier = new AIClassifier();
        System.out.println(aiClassifier.classify("Hey, buddy"));
    }
}