# Programmer: 22C:16 class
# Date: 4/4/13
# Version 1 
# This program reads a list of 5-letter words from a file called words.dat. It then builds a dictionary
# of these words. Each word is a key in the dictionary. The neighbors of a word w are all those words that
# differ from it in exactly one letter. The value associated with a key w in the dictionary is the list
# of neighbors of that word. The program then reads two 5-letter words: source and target and explores
# the dictionary to determine if there exists a chain of words connecting the source to the target.
# This version of the program will not output a chain - just tell us that a chain exists.

import time

# Two words are neighbors if they differ in exactly on letter.
# This function returns True if a given pair of words are neighbors
def areNeighbors(w1, w2):
    count = 0
    for i in range(len(w1)):
        if w1[i] != w2[i]:
            count = count + 1
    
    return count == 1

# The function reads from the file "words.dat" and inserts the words that are read
# into a list. The words are also inserted into a dictionary as keys, with each key 
# initialized to have [] as its value.
def readWords(wordList, D):
    fin = open("words.dat", "r")

    # Loop to read words from the and to insert them in a list
    # and in a dictionary
    for word in fin:
        newWord = word.strip("\n")
        wordList.append(newWord)
        D[newWord] = []

    fin.close()
    

# Builds the network of words using a dictionary. Two words are connected by an edge in this
# network if they are neighbors of each other, i.e., if they differ from each
# other in exactly one letter.
def buildDictionary(wordList, D):
    numPairs = 0
    # Nested for loop to generate pairs of words
    for i in range(len(wordList)):
        for j in range(i+1, len(wordList)):
            # Check if the two generated words are neighbors
            # if so append word2 to word1's neighbor list and word1 to word2's neighbor list
            if areNeighbors(wordList[i], wordList[j]):
                D[wordList[i]].append(wordList[j])
                D[wordList[j]].append(wordList[i])
    
# Explores the network of words D starting at the source word. If the exploration ends
# without reaching the target word, then there is no path between the source and the target.
# In this case the function returns False. Otherwise, there is a path and the function
# returns True.
def searchWordNetwork(source, target, D):

    # Initialization: processed and reached are two dictionaries that will help in the 
    # exploration. 
    # reached: contains all words that have been reached but not processed.
    # processed: contains all words that have been reached and processed, i.e., their neighbors have also been explored.
    # The values of keys are not useful at this stage of the program and so we use 0 as dummy values.
    processed = {source:0}
    reached = {}
    for e in D[source]:
        reached[e] = 0
       
    # Repeat until reached set becomes empty or target is reached 
    while reached:
        # Check if target is in reached; this would imply there is path from source to target
        if target in reached:
            return True
        
        # Pick an item in reached and process it
        item = reached.popitem() # returns an arbitrary key-value pair as a tuple
        newWord = item[0]
        
        # Find all neighbors of this item and add new neighbors to reached
        processed[newWord] = 0
        for neighbor in D[newWord]:
            if neighbor not in reached and neighbor not in processed:
                reached[neighbor] = 0 
    
    return False
        
# Main program
wordList = []
D = {}
readWords(wordList, D)
buildDictionary(wordList, D)

source = raw_input("Please type the source word: ")
target = raw_input("Please type the target word: ")

if searchWordNetwork(source, target, D):
    print("There is path from source to target")
else:
    #print ("There is no path from source to target")
