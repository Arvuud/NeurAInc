import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.tree import DecisionTreeClassifier
import matplotlib.pyplot as plt
import numpy as np
from sklearn.cluster import KMeans
from sklearn.datasets import make_blobs
from sklearn.metrics import silhouette_score, davies_bouldin_score, calinski_harabasz_score

def simple_classifier(csv_file, input_text):
    df = pd.read_csv(csv_file, sep=';', names=['text', 'label'])

    vectorizer = TfidfVectorizer(max_features=500)
    X = vectorizer.fit_transform(df['text'])
    y = df['label']

    classifier = DecisionTreeClassifier()
    classifier.fit(X, y)

    input_vector = vectorizer.transform([input_text])
    prediction = classifier.predict(input_vector)[0]

    # Beispiel-Daten generieren
    np.random.seed(0)
    X = np.random.randn(150, 2)  # 100 Beispiele mit 2 Features
    y = np.random.randint(0, 2, 150)  # 2 Kategorien

    # Plotte die Daten
    plt.figure(figsize=(10, 6))
    plt.scatter(X[y == 0, 0], X[y == 0, 1], color='blue', label='Kategorie 0', alpha=0.4)
    plt.scatter(X[y == 1, 0], X[y == 1, 1], color='red', label='Kategorie 1', alpha=0.4)
    plt.title('Trainingsdaten nach Kategorien')
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.legend()
    plt.grid(True)
    plt.show()

    return prediction

result = simple_classifier('project/src/main/resources/data/training_data.csv',
                           'We need to verify some recent concerning activity on your account')
print(result)