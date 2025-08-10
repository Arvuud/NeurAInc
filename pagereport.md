# NeurAInc — Conversational vital signs for scam calls

---

## Challenge Tackled
Scam calls weaponize **stress, noise, and speed**—especially against older adults, non-native speakers, and people using hearing support. The need isn’t a post-mortem; it’s a **timely, in-call interruption** that helps people pause before complying.  
**Users:** anyone at risk of social engineering; families and clinic front desks that field “verification” calls.

## What I Built (24-hour MVP)
A **local-first early-warning layer** that:
- listens through the mic,
- transcribes speech **offline** (Vosk),
- flags **red-flag phrases** chunk-by-chunk (TF-IDF + DecisionTree),
- shows a clear **on-screen warning during the call** (terminal).  
Runs on a normal laptop. No audio leaves the device.

## Tools / ML Models Used
- **STT:** Vosk en-us 0.22 lgraph; small en-us 0.15 *(German supported via local model)*
- **Classifier:** scikit-learn `TfidfVectorizer(max_features=500)` + `DecisionTreeClassifier`
- **Language:** Python (pandas, scikit-learn, PyAudio)
- **Data (repo-shipped):** `dataMal.txt` (scam), `dataPos.txt` (legit), `App/data/training_data.csv` (≈207 rows after cleaning)
- **Current languages:** **EN + DE** (more via drop-in Vosk models)

## What Worked Well
- Stable **listen → transcribe → classify → warn** loop on live mic input
- **Offline by default** → low latency, privacy preserved
- Holds up with imperfect/noisy audio (EN/DE)
- **Reproducible in minutes** (no cloud keys, simple deps)
- **Sanity check:** 5-fold CV on shipped CSV ≈ **0.81 ± 0.06** (enough to catch classic “urgent action / payment / transfer” patterns)

## What Was Challenging (and fixes)
- **Chunk timing** for “final enough” text → tuned chunk size & acceptance logic
- **Noisy, varied phrasing** → simple, robust TF-IDF baseline + conservative thresholds
- **Small, imbalanced data** → light model to avoid overfit; quick cleaning

## How I Spent the 24 Hours
- **0–3h:** Problem framing, data assembly (EN/DE)
- **3–8h:** Vosk integration + mic pipeline
- **8–14h:** TF-IDF + DecisionTree baseline; dataset cleanup
- **14–18h:** Live loop (chunking, prediction, warnings)
- **18–22h:** Noisy-audio testing, fixes, EN/DE switch
- **22–24h:** README, videos, submission

## Architecture (overview)
Mic/Call
↓
Vosk STT (offline, EN/DE) → text chunks
↓
TF-IDF(500) → DecisionTree → {malicious | positive}
↓
on-screen warning (during call)

## Dataset / References
Repo files: `dataMal.txt`, `dataPos.txt`, `App/data/training_data.csv`.  
Vosk models: en-us 0.22 lgraph / small 0.15 *(German optional via local model)*.

## If I had 24 more hours, I’d…
- Ship a **big, color-coded caption window** (always-on-top)
- Add a **watch/phone buzz** for silent alerts
- Expand languages beyond EN/DE; improve urgency/pressure handling
- Overlay captions **above any call app** (accessibility layer)

## Vision (why this matters)
Make **scam immunity as standard as caller ID**—a quiet, **cognitive safety layer** that travels with the call (phones, wearables, assistive audio) and interrupts manipulation **as it unfolds**.

---
**Author:** Tony Li — Solo build (STT, data, model, testing, demo)
