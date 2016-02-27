# Joseph Leiferman
# Student ID: 00990636
# Discussion Section: A06


def computeWordFrequencies(filename):
    
    file = open(filename,"r")
    dictionary = {}
    # holds all words in file this will be used to fine
    # how many times each words occurs
    totalwords = []
    for line in file:
        wordsInLine = parse(line)
        # takes every words from each line and creates a dictionary key for it 
        # and adds it to the totalwords list
        for x in wordsInLine:
            totalwords.append(x)
            dictionary[x] = 0
    # goes through all the words and updates each dictionary item
    # increasing its value each time it occurs
    for x in totalwords:
        
        dictionary[x] = dictionary[x]+1
    
    file.close()
    return dictionary
        
            


def parse(s):
    
    # create a masterList that will contain all words in length of 4
    # character and longer
    masterList = []
    # create an index so we can step through the string
    index = 0
    # this will keep track of current word being made
    newWord = ""
    
    wordBeingProcessed = False
    
    while index < len(s):
        # check if s[index] a letter and then adds the lowercase version of it to wordingBeingProcessed
        
        if s[index].lower() >= "a" and s[index].lower() <= "z": 
            wordBeingProcessed = True
            newWord = newWord + s[index].lower()
        # Adds wordBeingProcessed to master list
        elif wordBeingProcessed:
            wordBeingProcessed = False
            if len(newWord) >= 4: # checks to make sure length is longer than 4
                masterList.append(newWord)
            newWord = "" # rest newWord to an empty string
        
        
        index = index + 1
    if wordBeingProcessed: # checks for words beingBeingProcessed being cut off at the while loop
        masterList.append(newWord)
    return masterList