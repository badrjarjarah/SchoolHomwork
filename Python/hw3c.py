# Joe Leiferman
# Student ID: 00990636
# Discussion Section: A6

import math

# nearHalfInteger checks if a float is a a near half integer
# returns true if so, and returns the constant none if it isnt
def nearHalfInteger(f):
    counter = 0
    while abs(counter) <= abs(f)+1:
        
        if f >= 0 :
            
            if f >= counter -.001 and f <= counter + .001:
                
                return counter
            counter = counter + .5
            
        
        if f < 0:
            if f >= counter -.001 and f <= counter + .001:
                return counter
            counter = counter - .5 
    return None

# Main Program
counter = 0 # a counter to take as many inputs as user wants

numHalfInt = 0 # counts the number of half integers

n = int(input("How many integers are you going to enter? ")) # indicates how many floats they are going to enter

while counter < n:
    user = float(input())
    
    if nearHalfInteger(user): # checks if half integer
        numHalfInt = numHalfInt + 1 # increments the number of half integers by one
    counter = counter + 1
print("The number of near half integers is " + str(numHalfInt)) # returns number of half integers
    

