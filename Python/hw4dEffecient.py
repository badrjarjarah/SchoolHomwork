# Joey Leiferman
# Student ID: 00990636
# Discusion Section: A6

#This will contain many helper functions and the main functions 
# parse(s), computeWordFrequency(filename),mostFrequentWords(wordList, frequency, k)

import time

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

# this is a 2nd function that creates a distinct list

def uniqueExtend(bigList, smallList):
    index = 0
    
    while index < len(smallList):
        #processing smallList[index]
        if not (smallList[index] in bigList): # if new word is not in big list it enters it in
            bigList.append(smallList[index])
        index = index + 1
    return bigList
            

# third function takes two list checks how many times words in the first list are in the 
# second list and return a list with the number of time

def frequency(list1,list2):
    index = 0
    frequencyList = []
    while index < len(list1):
        frequencyList.append(list2.count(list1[index]))
        index = index + 1
    return frequencyList
        
# fourth function creates an unique list of all the words in the file

def unUniqueExtend(bigList,smallList):
    index = 0
        
    while index < len(smallList):
        bigList.append(smallList[index])
        index = index + 1
    return bigList    
    
        
    

# this is the function computeWordFrequencies

def computeWordFrequencies(filename):
    
    f = open(filename) # opens the filename
    line = f.readline() # reads the first line
    masterList = [] # for distinct words
    nonMasterList= [] # for all words in the file

    
    
    while line != "":
        wordsInLine = parse(line)
        #print(wordsInLine)
        nonMasterList = unUniqueExtend(nonMasterList, wordsInLine) # create a total word list
        masterList = uniqueExtend(masterList, wordsInLine) # creates a distinct list
        line = f.readline() # reads the next line untill done
    
    # returns a list of times each word shoes up in the file
    frequencyList = frequency(masterList,nonMasterList)
    
    return [masterList , frequencyList]

# mostFrequentWords will use a selection sort to organize the list up untill k 
# than stop and will return k most frequent results
# it will use a function called selectSortMod(wordList,frequency,k)
# it will also use a function called swapthem that swaps values in a list 
# call swapThem (list,x,y)

def swapThem(List,x,y):
    temp = 0
    temp = List[x]
    List[x] = List[y]
    List[y] = temp
        
def selectSortMod(words,List,k):
    partion = 0
    highValue = List[0]
    highIndex = List[0]
    
    while partion < k:
        index = partion 
        while index < len(List):
        
            if List[index] > highValue:
                highValue = List[index]
                highIndex = index
                
            index = index + 1
            
        
        swapThem(List,partion,highIndex)
        swapThem(words,partion,highIndex)
        partion = partion  + 1
        highValue = List[partion]
        highIndex = partion
    return words, List
          
    
def mostFrequentWords(wordList, frequency, k):
    mostFrequent = []
    selectSortMod(wordList, frequency , k) # organize list up untill k
    index = 0
    while index < k:
        
        mostFrequent.append(wordList[index])
        
        
        
        index= index + 1
    return mostFrequent


        

# a modified version of computeWordFrequencies it will take two files names 
# and read from them and add them to two lists one list is distinct words
# the other is all words
def computeWordFrequenciesMod(filename1,filename2):
    
    f = open(filename1)# opens the filename
    g = open(filename2) # opens the second filename
    line = f.readline() # reads the first line
    line2 = g.readline()
    masterList = [] # for distinct words
    nonMasterList= [] # for all words in the file

    
    
    while line != "" and line2 != "":
        wordsInLine = parse(line)
        
        wordsInLine2 = parse(line2)
        nonMasterList = unUniqueExtend(nonMasterList, wordsInLine)# create a total word list
        nonMasterList = unUniqueExtend(nonMasterList, wordsInLine2) 
        masterList = uniqueExtend(masterList, wordsInLine) # creates a distinct list
        masterList = uniqueExtend(masterList, wordsInLine2)
        line = f.readline() # reads the next line untill done
        line2 = g.readline()
    
    # returns a list of times each word shows up in the file
    frequencyList = frequency(masterList,nonMasterList)
    
    return [masterList , frequencyList]



# this where the main function will be it will take war.txt hyde.txt and treasure.txt
# and find the most frequent words used by tolstoy and stevenson
# it will use all previous functions to aid in this 
# along with any modified version of computeWordFrequencies that takes
# two file names instead of one
start_time = time.time()

    
print("Please wait while I find the 20 most frequent words in the Tolstoy and Stevenson Novel's....")    
tolstoyFrequency = computeWordFrequencies("war.txt")
tolstoyList = mostFrequentWords(tolstoyFrequency[0],tolstoyFrequency[1],20)
stevensonFrequency = computeWordFrequenciesMod("hyde.txt","Treasure.txt")
stevensonList = mostFrequentWords(stevensonFrequency[0],stevensonFrequency[1],20)
    
print("Most frequent words in Leo Tolstoy's novel are ", tolstoyList , "The Most frequent words in Robert Stevenson's novels are " , stevensonList)
print("Time it took: ",(time.time()-start_time)/60+"minutes")