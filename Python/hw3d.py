# Joe Leiferman
# Student ID: 00990636
# Discussion Section: A6

import random # import the random to help simulate a step in a random direction
import math #  used for finding the absolute values of x and y to see if the robot is with the barrier

#function takes two argument one is the size of the barrier and the other 
# is too see if we should show the robots progress untill it hits the barrier

def twoDRandomWalk(n = 100, printOn = False): 
    xPosition= 0 # keeps track of the position of x
    yPosition = 0 # keeps track of the position of y
    steps = 0 # used to count the number of steps taken
    
    while (abs(xPosition) and abs(yPosition)) < (2*n): # execute simulated step if with boundary
        direction =random.randint(1,4) # simulates a random step 
        
        if direction == 1:      # determines which direction the robot steped
            yPosition = yPosition + 1
        if direction == 2:
            yPosition = yPosition - 1
        if direction == 3:
            xPosition = xPosition + 1
        if direction == 4:
            xPosition = xPosition - 1
        steps = steps + 1
        if printOn:           # if printOn is true it will show the progress of the robot
            print(xPosition,yPosition)
    return steps    # return the number of steps taken
        
        
    