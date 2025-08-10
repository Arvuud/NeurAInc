import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.tree import DecisionTreeClassifier

class NeurAIncClassifier:
    def __init__(self):
        self.classifier = None
        self.vectorizer = None
        self.isTrained = False

    def train(self, csv_file):
        df = pd.read_csv(csv_file, sep=';', names=['text', 'label'])

        self.vectorizer = TfidfVectorizer(max_features=500)
        X = self.vectorizer.fit_transform(df['text'])
        y = df['label']

        self.classifier = DecisionTreeClassifier()
        self.classifier.fit(X, y)
        self.isTrained = True
        print("The model has been trained")

    def predict(self, csv_file, input_text):
        if not self.isTrained:
            raise RuntimeError("Trained model has not been trained")

        input_vector = self.vectorizer.transform([input_text])
        prediction = self.classifier.predict(input_vector)[0]

        return prediction
