# NeurAInc – Voice Scam Shield

Built in 24 hours at the Global AI Hackathon 2025. Goal: warn people about scam calls **while** they’re still on the line, without sending audio to the cloud.

## What it does
- Listens through the mic
- Transcribes speech locally with Vosk (offline STT)
- Classifies each transcript chunk as "malicious" or "positive"
- Prints a warning in the terminal during the call

## Languages (current)
- English 
- German 
More languages can be added by dropping the corresponding Vosk model into `App/model/` and pointing the loader to it.

## Quick try
Say: “This is Microsoft Support. Your account is at risk. Press 1.”  
Or in German: “Hier ist der Microsoft-Support. Ihr Konto ist gefährdet. Drücken Sie die 1.”  
You should see a **malicious** warning in the terminal.

## Why
Scam calls use speed, pressure, and noise to push fast decisions. This project aims to interrupt the script in the moment, not after.

## How it works
- `App/SpeechToText.py` – Vosk models (EN + DE) loaded locally
- `App/NeurAIncClassifier.py` – TF-IDF vectorizer (max 500 features) + DecisionTreeClassifier
- `App/Main.py` – main loop (mic → STT → classify → print result)
- Data files:
  - `dataMal.txt` – scam examples
  - `dataPos.txt` – safe call examples
  - `App/data/training_data.csv` – combined dataset (207 rows after cleaning)

Sanity check (on shipped CSV, 5-fold CV): **~0.81 ± 0.06** accuracy with TF-IDF + DecisionTree.

## Run
```bash
pip install vosk scikit-learn pandas pyaudio
python App/Main.py
