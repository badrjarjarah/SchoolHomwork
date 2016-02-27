# This function takes a single parameter L, which is a list of strings
# increments through this list and checks the length
# if one the strings is shorter or longer than the next it returns false
# if it gets to the end of the list it returns True

def equalLengthStrings(L):
    index = 0
    while index+1 < len(L):
        if len(L[index]) != len(L[index+1]):
            return False
        index = index + 1
    return True

# Takes three parameters L which is a list and i and j which are indicies
# returns L[i] + L[i+1] up untill l[j]
def addSubsequence(L,i,j):
    total = 0
    while i <=j:
        
        total = total + L[i]
        i = i + 1
    return total
# takes three parameters again L which is a list and i and j it then 
# returns a list again that discludes indicies l[i-j]

def deleteSubsequence(L,i,j):
    newL = []
    index = 02
    while index < len(L):
        if index< i or index >j:
            newL.append(L[index])
        index = index + 1
        
    return newL

# takes a list as parameter and returns consecutive positions that when 
# added together have the largest value

def maxPairSum(L):
    index = 0
    maxSum = 0
    bestPair = []
    while index+1 < len(L):
        newMaxSum = L[index] + L[index+1]
        if newMaxSum > maxSum:
            maxSum = newMaxSum
            bestPair = [L[index],L[index+1]]
        index = index + 1
    return bestPair
# takes a list as a parameter and returns the index of the smallest
# element in that list

def minIndex(L):
    index = 0
    smallestIndex = index
    smallestValue = L[index]
    while index+1 < len(L):
        if smallestValue > L[index+1]:
            smallestIndex = index+1
            smallestValue = L[index+1]
        index = index + 1
    return smallestIndex
        
        
        

    


        