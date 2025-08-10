import NeurAIncClassifier as NC
import SpeechToText as STT

classifier = NC.NeurAIncClassifier()
classifier.train("data/training_data.csv")

print("-----NeurAInc-----\n")
print("Press any key and/or [enter] to start recording the call.\n"
      "In case you need more information about this tool press [i] and [enter]\n"
      "In case you want to exit the programm type [exit] and press [enter]\n")
randomUserInput = input()
runProgramm = True
if randomUserInput == "i":
    print("-----Manual-----\n")
    print("This programm automatically starts a recording.\n"
          "This recording will be classified, if it either is \"positive\" (i.e. it is a normal call)\n"
          "or \"malicious\" (i.e. it is likely to be a spam-call)")
elif randomUserInput == "exit":
    runProgramm = False


if runProgramm:
    # "Microsoft Customer Servicer here,"
    #              "we really need to talk about your pc, this situation is really concerning"
    input = (STT.textToSpeech())
    result = classifier.predict(input)
    print(input)
    print(result)