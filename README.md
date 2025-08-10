# NeurAInc – Voice Scam Shield

Built in 24 hours at the Global AI Hackathon 2025. The goal: warn people about scam calls **while** they’re still on the line, without sending any audio to the cloud.

## What it does
- Listens through the mic
- Transcribes speech locally with Vosk (offline STT)
- Classifies each transcript chunk as "malicious" or "positive"
- Prints a warning in the terminal during the call

Right now it’s English-only and catches common scam language from our dataset.

## Why
Scam calls often only need 30–45 seconds to push someone into a bad decision.  
We wanted something simple that can interrupt that script before it’s too late.

## How it works
- `App/SpeechToText.py` – uses Vosk (large and small EN models)
- `App/NeurAIncClassifier.py` – TF-IDF vectorizer (max 500 features) + DecisionTreeClassifier
- `App/Main.py` – main loop (mic → STT → classify → print result)
- Data files:
  - `dataMal.txt` – scam examples
  - `dataPos.txt` – safe call examples
  - `App/data/training_data.csv` – combined dataset (207 rows after cleaning)

Tested accuracy on our dataset: ~0.81 (5-fold CV).

## Run
```bash
pip install vosk scikit-learn pandas pyaudio
python App/Main.py

Team
Tony Li
