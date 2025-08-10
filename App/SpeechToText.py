'''
import speech_recognition as sr
import pyttsx3

r = sr.Recognizer()

def record_text():
    while(True):
        try:
            with sr.Microphone() as source2:
                r.adjust_for_ambient_noise(source2, duration=0.2)
                audio2 = r.listen(source2)
                MyText = r.recognize_vosk(audio2)

                return MyText
        except sr.RequestError as e:
            print("Could not request results")
        except sr.UnknownValueError:
            print("Unknown error")

    return
def output_text():
    f = open
    return

while(True):
    text = record_text()
    output_text(text)

    print("Wrote text")
'''
import pyaudio
from vosk import Model, KaldiRecognizer

def textToSpeechFromEnglish():
    print("Starting textToSpeech")
    model = Model(r"model/vosk-model-small-en-us-0.15")
    recognizer = KaldiRecognizer(model, 16000)

    mic = pyaudio.PyAudio()
    stream = mic.open(format=pyaudio.paInt16,
                      channels=1,
                      rate=16000,
                      input=True,
                      frames_per_buffer=8192)
    stream.start_stream()
    while True:
        data = stream.read(4096)
        if recognizer.AcceptWaveform(data):
            text = recognizer.Result()
            return text[14:-3]

def textToSpeechFromGerman():
    print("Starting textToSpeech")
    model = Model(r"model/vosk-model-small-de-0.15")
    recognizer = KaldiRecognizer(model, 16000)

    mic = pyaudio.PyAudio()
    stream = mic.open(format=pyaudio.paInt16,
                      channels=1,
                      rate=16000,
                      input=True,
                      frames_per_buffer=8192)
    stream.start_stream()
    while True:
        data = stream.read(4096)
        if recognizer.AcceptWaveform(data):
            text = recognizer.Result()
            return text[14:-3]

def textToSpeechFromIndianEnglish():
    print("Starting textToSpeech")
    model = Model(r"model/vosk-model-small-en-in-0.4")
    recognizer = KaldiRecognizer(model, 16000)

    mic = pyaudio.PyAudio()
    stream = mic.open(format=pyaudio.paInt16,
                      channels=1,
                      rate=16000,
                      input=True,
                      frames_per_buffer=8192)
    stream.start_stream()
    while True:
        data = stream.read(4096)
        if recognizer.AcceptWaveform(data):
            text = recognizer.Result()
            return text[14:-3]