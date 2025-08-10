# NeurAInc — Conversational vital signs for scam calls

## Challenge Tackled
Scam calls exploit stress, noise, and speed—pushing vulnerable users (older adults, non-native speakers, hearing-aid/CI users) into fast, costly decisions. The need isn’t a post-mortem; it’s a **timely in-call interruption**.

**Users:** anyone at risk of social engineering; families; clinic front desks that field “verification” calls.

## What I Built (24-hour MVP)
- Local-first companion that runs on a normal laptop  
- Offline STT (Vosk) → no audio leaves the device  
- Chunk-by-chunk risk flagging (TF-IDF[500] + DecisionTree)  
- Clear **on-screen warning during the call** (terminal)  
- Languages now: **EN + DE**

## Tools / ML Models Used
- **STT:** Vosk en-us 0.22 lgraph; small en-us 0.15 (DE via local model)  
- **Classifier:** scikit-learn `TfidfVectorizer(max_features=500)` + `DecisionTreeClassifier`  
- **Glue:** Python (pandas, scikit-learn, PyAudio)  
- **Data (repo):** `dataMal.txt`, `dataPos.txt`, `App/data/training_data.csv` (~207 rows)

## What Worked Well
- Stable loop: **listen → transcribe → classify → warn**  
- Fully offline → low latency, privacy preserved  
- Holds up on imperfect/noisy audio (EN/DE)  
- **Sanity check (5-fold on CSV): ~0.81 ± 0.06** accuracy; catches classic “urgent action / payment / transfer” patterns

## What Was Challenging (and fixes)
- Chunk timing for “final” text → tuned chunk size & acceptance  
- Noisy, varied phrasing → robust TF-IDF baseline + conservative thresholds  
- Small, imbalanced data → light model; quick cleaning

## How I Spent the 24 Hours
0–3h framing & data · 3–8h Vosk+mic · 8–14h TF-IDF+DT baseline · 14–18h live loop · 18–22h noisy tests/fixes · 22–24h README, videos, submission

## Architecture (overview)
Mic/Call → Vosk STT (offline, EN/DE) → text chunks → TF-IDF(500) → DecisionTree → {malicious | positive} → on-screen warning

## Dataset / References
Repo files: `dataMal.txt`, `dataPos.txt`, `App/data/training_data.csv`. Vosk models: en-us 0.22 lgraph / small 0.15 (DE optional).

## If I had 24 more hours, I’d…
- Ship a **big, color-coded caption window** (always-on-top)  
- Add a **watch/phone buzz** for silent alerts  
- Expand languages beyond EN/DE; overlay captions above any call app

**Vision:** Make **scam immunity as standard as caller ID**—a quiet, cognitive safety layer that interrupts manipulation **as it unfolds**.

**Author:** Tony Li — Solo build (STT, data, model, testing, demo)
