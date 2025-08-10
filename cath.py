import NeurAIncClassifier

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
    input = ("Microsoft Customer Servicer here,"
             "we really need to talk about your pc, this situation is really concerning")

    classifier = NeurAIncClassifier()
    result = classifier.predict('project/src/main/resources/data/training_data.csv',
                               input)
    print(result)