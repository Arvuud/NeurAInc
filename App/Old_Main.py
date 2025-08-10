import NeurAIncClassifier as NC
import SpeechToText as STT
import CSVSearcher as CSS

classifier = NC.NeurAIncClassifier()
classifier.train("data/training_data.csv")


print("-----NeurAInc-----\n")
print("Press any key and/or [enter] to start recording the call.\n"
      "In case you need more information about this tool press [i] and [enter]\n"
      "In case you want to enable advanced-safety-settings press [s] and [enter]\n"
      "In case you want to exit the programm type [exit] and press [enter]")
randomUserInput = input()
runProgramm = True
furtherSafety = False
if randomUserInput == "i":
    print("-----Manual-----\n")
    print("This programm automatically starts a recording.\n"
          "This recording will be classified, if it either is \"positive\" (i.e. it is a normal call)\n"
          "or \"malicious\" (i.e. it is likely to be a spam-call)")
elif randomUserInput == "exit":
    runProgramm = False
elif randomUserInput == "s":
    furtherSafety = True

if runProgramm:
    # "Microsoft Customer Servicer here,"
    #              "we really need to talk about your pc, this situation is really concerning"
    input = STT.textToSpeechFromEnglish()
    result = classifier.predict(input)
    print(input)
    print(result)

if furtherSafety:
    if CSS.search_in_csv("data/blocked_numbers.csv", input("Enter callers number: ")):
        print("MALICIOUS")