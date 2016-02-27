# Joseph Leiferman
# Student ID: 00990636
# Discussion Section: A06

# will create a list length 26 for each letter in the alphabet
# in each list their will be a another list length 133 for each year
# within the this list is a dictionary with each name of the child born that year
# the value will be how many times a male and female was born with that name
def lastLetterIndex():
    # this will create the entire dataStructure for D
    D = dataStructure()
    
    file = open('babyNames.txt','r')
    
    for line in file:
        
        # gets rid of end of line and character and splits by the comma
        # will than get the name, year, gender, and frequency from that line
        line = (line.strip("\n")).split(",")
        year = int(line[0])
        name = line[1]
        gender = line[2]
        frequency = int(line[3])
        # retracts the last letter of the name
        lastLetter = name[-1]
        # depending on if the baby name is male or female it will update the
        # tempory value stored for that name with the correct frequency
        if gender == 'F':
            
            D[lastLetter][year-1880][name][0]=(gender,frequency)
        
        elif gender == 'M':
            D[lastLetter][year-1880][name][1] =(gender,frequency)
            
    file.close()    
        
    return D
# this helper function creates tempory values for each name and year
# a baby was born
def dataStructure():
    D = {}
    file = open("babyNames.txt",'r')
    # create a list length 26 and inside each list creates a dictionary
    # for each possible year
    for x in range(26):
        D[chr(x+97)] = []
        for y in range(134):
            D[chr(x+97)].append({})
    # creates tempory frequencies that will be changed in the lastLetterIndex
    # function. This helps solve the male and female problem with each name.
    for line in file:
        
        line = (line.strip('\n')).split(',')
        year = int(line[0])
        name = line[1]
        lastLetter = name[-1]
        D[lastLetter][year-1880].update({name:[('F',0),('M',0)]})
    file.close()
    return D