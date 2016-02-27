# Jospeh Leiferman
# Student ID: 00990636
# Discussion Section: A6



# function display takes parameters data structure and a List of cites

def display(facilities, data):
    # creates a KML file to write in
    
    f=open("visualizations800.kml","w")
    f.write('<?xml version="1.0" encoding="UTF-8"?>\n')
    f.write('<kml xmlns="http://www.opengis.net/kml/2.2">\n<Document>\n')
    #path style
    f.write("<Style id='smallLine'>\n<LineStyle>\n<color>6478DC00</color>\n<width>4</width>\n</LineStyle>\n<PolyStyle>\n<color>7f00007f</color>\n</PolyStyle>\n</Style>\n\n")
    #icon style/color
    f.write("<Style id='randomColorIcon'>\n<IconStyle>\n<color>6414008FF</color>\n<colorMode>random</colorMode>\n<scale>1.1</scale>\n<Icon>\n<href>https://cdn0.iconfinder.com/data/icons/iconsweets2/40/factory.png</href>\n</Icon>\n</IconStyle>\n</Style>")
    # for every facility location it puts it on the map with a name and 
    # description 
    for x in facilities:
        name = data[0].index(x)
        coordinates = []
        # finds latitude
        latitude = str(data[1][name][1])
        if len(latitude) == 4:
            latitude = "-"+latitude[:2] + "." + latitude[2:]
        if len(latitude) ==5:
            latitude = "-" + latitude[:3] + "." + latitude[2:]
        # finds longitude
        longitude= str(data[1][name][0])
        longitude= longitude[:2] + "." + longitude[2:]
        # puts latitude and longitude in a list
        coordinates.append([float(latitude),float(longitude)])
        # places a factory icon on each facility
        f.write("<Placemark>\n<name>"+data[0][name]+"</name>\n<styleUrl>#randomColorIcon</styleUrl>\n<description>This is a facility, it is located at "+data[0][name]+".</description>\n")
        f.write("<Point>\n<coordinates>")
        f.write(str(coordinates[0][0])) 
        f.write(",") 
        f.write(str(coordinates[0][1])+", 0")
        f.write( "</coordinates>\n</Point>\n</Placemark>\n\n")
    # for every city that isnt a facility it places it on the map
    # and creates a path to the nearest facility
    for x in data[0]:
        if x not in facilities:
            name = data[0].index(x)
            coordinates = []
            # finds latitude of city
            latitude = str(data[1][name][1])
            if len(latitude) == 4:
                latitude = "-"+latitude[:2] + "." + latitude[2:]
            if len(latitude) == 5:
                latitude = "-" + latitude[:3] + "." + latitude[2:]            
            #finds longitude of city
            longitude= str(data[1][name][0])
            longitude= longitude[:2] + "." + longitude[2:]
            coordinates.append([float(latitude),float(longitude)])
            # places a placemark at location of city
            f.write("<Placemark>\n<name>"+data[0][name]+"</name>\n")
            f.write("<Point>\n<coordinates>")
            f.write(str(coordinates[0][0])) 
            f.write(",") 
            f.write(str(coordinates[0][1])+", 0")
            f.write( "</coordinates>\n</Point>\n</Placemark>\n\n")
            #create a path
            coordinates1 = []
            nearestFacilityCity = cityPath(x,facilities,data)
            facilityLocationLat = str(data[1][nearestFacilityCity][1])
            if len(facilityLocationLat) == 4:
                facilityLocationLat = "-"+facilityLocationLat[:2] + "." + facilityLocationLat[2:]
            if len(facilityLocationLat) == 5:
                facilityLocationLat = "-" + facilityLocationLat[:3] + "." + facilityLocationLat[2:]
            
            nearestFacilityLongitude = str(data[1][nearestFacilityCity][0])
            nearestFacilityLongitude= nearestFacilityLongitude[:2] + "." + nearestFacilityLongitude[2:]
            
            coordinates1.append([float(facilityLocationLat),float(nearestFacilityLongitude)])
            
            f.write("<Placemark>\n<name>Nearest Facility</name>\n<styleUrl>#smallLine</styleUrl>\n<LineString>\n<extrude>1</extrude>\n<tessellate>1</tessellate>\n<altitudeMode>absolute</altitudeMode>\n<coordinates>\n")
            f.write(str(coordinates[0][0])+"," +str(coordinates[0][1])+","+ "0\n")
            f.write(str(coordinates1[0][0]) + "," + str(coordinates1[0][1]) + "," + "0")
            f.write("\n</coordinates>\n</LineString>\n</Placemark>\n\n")
            
    f.write("</Document></kml>")
    f.close()
    
        
# a function to find the nearest facility 
def cityPath(cityName,facilities, data):
    nearestFacility = facilities[0]
    nearestFacilityDist = getDistance(cityName,facilities[0],data)
    currentDistance = 0
    # iterates through facilities looking for the nearest one
    for x in facilities:
        currentDistance =getDistance(cityName,x,data)
        if currentDistance < nearestFacilityDist:
            nearestFacilityDist = currentDistance
            nearestFacility = x
    return data[0].index(nearestFacility)
    




#locates facilites to be served
def locateFacilities(data, r ):

    citiesServed = [False for x in range(128)]
    facilityLocations = []
    
    while False in citiesServed:
        #keeps track of of the amount of cities
        #that are with r from each city that 
        # hasnt been served
        citiesBeingServed = [0 for x in range(128)]
        for city in range(len(citiesServed)):
            # checks if this city has been served
            if data[0][city] != [facilityLocations]:
                # will append the amount of cities that are with r to that cities location
                citiesBeingServed[city] = len(nearbyCitiesMod(data[0][city],r,data,citiesServed))
                
            

        bestFacility = facilityLocator(citiesBeingServed,data)
        # marks of the city and all cities around it as served
        placingFacility(bestFacility,r,citiesServed,data)
        facilityLocations.append(bestFacility)
        
    
    facilityLocations.sort()
    
    return facilityLocations

#find highest number in a list and returns the cityName at that index
def facilityLocator(citiesBeingServed,data):
    highestValue = 0
    index = 0
    for x in range(len(citiesBeingServed)):
        if citiesBeingServed[x] > highestValue:
            highestValue = citiesBeingServed[x]
            index = x 
    
    return data[0][index]
    
#will mark cityName and all cities with r as served
def placingFacility(cityName, r , citiesServed, data):

    cities = nearbyCitiesMod(cityName, r, data, citiesServed)
    cityIndex = 0
    for x in cities:
        cityIndex = data[0].index(x)
        
        citiesServed[cityIndex] = True
    
    
            
        
        
# a modified version of nearby cities that will only find cities
# that have not been served
def nearbyCitiesMod(cityName, r, data, citiesServed):
    
    nearbyCities= []

    for i in range(len(data[0])):
        # checks each city for their distance from cityName
        
        if getDistance(cityName,data[0][i],data) <= r:
            
            if citiesServed[i] == False:
            
                nearbyCities.append(data[0][i])
        
    nearbyCities.sort()    
    return  nearbyCities  

        


#creates a 2D list containing facility information


def createDataStructure():
    
    f = open("miles.dat")
    line = f.readline()
    dataStructure = []
    cities = []
    coordinates = []
    population = []
    citiesProcessed = 1
    distances = []
    while len(distances) < 128: # creates lists inside distances
        distances.append([])
    totalDistance = []
    processingDistance = False # checks to see if it is a new city or distances
    while line != "":
        
        if line[0] != "*":
            # checks the beginning of the line to see if it is a new city or 
            # if it is distances of the current city
            if line[0].lower()>="a" and line[0].lower()<= "z":
                if processingDistance:
                    processingDistance = False
                    processLineDistance(distances,totalDistance,citiesProcessed)
                    totalDistance = []
                    citiesProcessed = citiesProcessed + 1
                    
                cities.append(processLineCity(line))
                coordinates.append(processLineCoord(line))
                population.append(processLinePop(line))
                
            
            elif ord(line[0])>=48 and ord(line[0])<=57: 
                totalDistance = totalDistance + processDistances(line)
                
                processingDistance = True
                
                
            
                    
                    
        # reads the next line   
        line = f.readline()
    # gets the remainder of the distances from the final city    
    processLineDistance(distances,totalDistance,citiesProcessed)
    insertDistanceZero(distances)
    
    dataStructure.append(cities)
    dataStructure.append(coordinates)
    dataStructure.append(population)
    dataStructure.append(distances)
    f.close()
    
    return dataStructure
    
    
        
        
# Parses line for city and state      
def processLineCity(line):
    city = ""
    for x in line:
        
        if x == "[":
            break
        elif x != ",":
            city = city + x
    return city
# parses the line for the Coordinates of the city
def processLineCoord(line):
    Coord = []
    word = ""
    inBrackets = False # to check if x is in the brackets
    for x in line:
        
        if x == "[":
            inBrackets = True
        if x == "]":
            Coord.append(int(word))
            inBrackets = False
        
        if inBrackets:
            
            if x == ",": # checks when to start longitude
                Coord.append(int(word))
                word = ""
            if ord(x)>=48 and ord(x)<=57:
                word = word + x
      
    return Coord
# parse the line for population of the city
def processLinePop(line):
    population = ""
    pop = False # checks for when the ] shows up and starts keeping track
    for x in line:
        if pop:
            population = population + x 
        
        if x =="]":
            pop = True
    
            
    return int(population)    
# parse the line for Distance and puts them into a list
def processDistances(line):
    totalList =[]
    word = ""
    for x in line:
        
        if ord(x)>=48 and ord(x)<=57:
            word = word + x
        if x == " ":
            totalList.append(int(word))
            word = ""
    totalList.append(int(word))
    return totalList
            
         
# will add total distances to each previous sub distance and 
# to the current city being processed
def processLineDistance(distances,totalDistances,citiesProcessed):
    
    index = citiesProcessed
    index2 = 0
    index3 = -1
    while index3 >= (citiesProcessed*-1):
        distances[citiesProcessed].append(totalDistances[index3])
        index3 = index3 -1
    while index > 0:
        
        distances[index2].append(totalDistances[index-1])
            
    
        index2= index2 + 1
        index = index - 1
    

    return distances  

# adds a 0 infront of all subdistance lists
def insertDistanceZero(distances):
    index = 0
    for x in distances:
        
        distances[index].insert(0,0)
        
        index = index + 1
    
# takes a city's name as a parameter and returns its coordinates

def getCoordinates(cityName, data):
    
    
    index = 0
    for x in data[0]:
        
        if x == cityName:
            return data[1][index]
    
        index = index + 1   
# takes city name as a parameter and returns its population

def getPopulation(cityName, data):
    index = 0
    for x in data[0]:
        
        if x == cityName:
            return data[2][index]
        
        index = index + 1
# takes two cities as parameters and return the distance between
# them
def getDistance(cityName1,cityName2,data):
    counter = 0
    city1index= 0
    city2index= 0
    if cityName1 == cityName2:
        return 0 
    for x in data[0]:
        
        if x == cityName1:
            city1index = counter
            
        if x == cityName2:
            city2index = counter
        
        counter = counter + 1
    
    
    if city1index <= city2index:
        if city2index == 0:
            city2index = 1
        return data[3][city1index][city2index]
    if city2index < city1index:
        if city1index == 0:
            city1index = 1
        return data[3][city2index][city1index]

# takes a parameter a city and a radius and returns all nearby
# cities within that radius

            
def nearbyCities(cityName, r, data):
    
    nearbyCities= []
    counter = 0
    while counter < 128:
        # checks each city for their distance from cityName
        if getDistance(cityName,data[0][counter],data) <= r:
            nearbyCities.append(data[0][counter])
        counter = counter + 1
        
    return sorted(nearbyCities)

# Main program
# will return visualization800.kml
data = createDataStructure()
facilities = locateFacilities(data, 800)
display(facilities, data)