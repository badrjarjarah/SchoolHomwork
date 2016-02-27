# Joe Leiferman
# Student ID: 00990636
# Discussion section:A6
# a visual display of test scores in an intervals of 25 
user = input("Please enter a integer between 0 and 100 or done: ") # ask the user for an integer to get into the loop
group1 = 0 # create a variable for each group they will be used as counters later
group2 = 0
group3 = 0
group4 = 0
group1F = "[0,25]   " # these will be wear the answers will be stored
group2F = "(25,50]  "
group3F = "(50,75]  "
group4F = "(75,100] "
while user != "done": # create a loop that will be broken when the user enters done
    if  0<=int(user)<=25: # these if statements, that have paremeters for which variable they will go to
        group1 = group1 + 1
        
    elif 25<int(user)<=50:
        group2 = group2 + 1
        
    elif 50<int(user)<=75:
        group3 = group3 + 1
        
    elif 75<int(user)<=100:
        group4 = group4 + 1
        
    elif user == "done": # it will break when the user enters done
        break
    elif 0>int(user):
        print("Please enter a valid integer sir") # if the user enters an invalid number they will be prompted to do so
    elif int(user)>100:
        print("please enter a valid integer sir")
    
    user = input("Please enter a integer between 0 and 100 or done: ")
while group1 >0: # these while loops are used to create the answers I used the variables from earlier as counters so I know how many people are in each range
    group1F = group1F + "*"
    group1 = group1 - 1
while group2 >0:
    group2F = group2F + "*"
    group2 = group2 - 1
while group3 >0:
    group3F = group3F + "*"
    group3= group3 -1
while group4 >0:
    group4F = group4F + "*"
    group4 = group4 -1
print(group1F) # displays the results
print(group2F)
print(group3F)
print(group4F)

    
        
    
