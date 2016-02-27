# Joe Leiferman
# Student ID: 00990636
# Discussion Section: a6

# This program takes a parameter N and returns a list [m,n] 
# so that m and n are consecutive primes and that m < n <= N
# and that m-n is the largest gap 
# it also calls the boolean function isPrime

import math
def isPrime(n):
    factor = 2 # initial value of possible factor
    factorUpperBound = math.sqrt(n) # the largest possible factor we need to test is sqrt(n)
    
    # loop to generate and test all possible factors
    while (factor <= factorUpperBound):
        # test if n is evenly divisible by factor
        if (n % factor == 0):
            return False
        
        factor = factor + 1
        
    return True

def farthestConsecutivePrimes(N):
    
    list1 = [] # this holds all primes up untill N
    i = 0 # this argument increments and finds all primes up untill N
    while i < N:
        if isPrime(i): # call the function isPrime with the argument i
            list1.append(i)
        i = i + 1 # increment i
    
    m = 0 # m and n are are going to be set to primes as we increment through list1
    n = 0
    d = 0 # is the difference betweent the current primes in list1
    x = 0 # x and y will be used to keep track of where we are in list1
    y = 1
    maxD = 0 # maxD will find the greatest difference
    maxDlist=[] # and put it in this list
    
    while y < len(list1):
        m,n,d = list1[x],list1[y],(list1[y]-list1[x]) # this is used to find the differences of each prime
        
        if maxD <= d: # if the current difference is greater than the last difference it is appended to the maxD list
            maxD = d
            maxDlist= [m,n]
        
        x= y # increment through the list
        y = y +1
         
    return maxDlist # return the consecutive integers with the highest difference and wala 
    
    
    
        
        
    
       
    
            