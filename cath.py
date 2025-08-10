import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.tree import DecisionTreeClassifier

print("-----NeurAInc-----\n")
print("Press any key and/or [enter] to start recording the call.")
input
input = "Microsof Customer Servicer here, we really need to talk about your pc, this situation is really concerning"
def simple_classifier(csv_file, input_text):
    df = pd.read_csv(csv_file, sep=';', names=['text', 'label'])

    vectorizer = TfidfVectorizer(max_features=500)
    X = vectorizer.fit_transform(df['text'])
    y = df['label']

    classifier = DecisionTreeClassifier()
    classifier.fit(X, y)

    input_vector = vectorizer.transform([input_text])
    prediction = classifier.predict(input_vector)[0]

    return prediction

result = simple_classifier('project/src/main/resources/data/training_data.csv',
                           input)
print(result)