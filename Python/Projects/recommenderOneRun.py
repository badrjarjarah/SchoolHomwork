# Joseph Leiferman
# Student ID: 00990636
# Discussion Section: A06

import math
import random
import time

# Reads information about users from the file u.user. This information is stored as a list dictionaries and returned.
# Each dictionary has keys "age", "gender", "occupation", and "zip". The dictionary for user i is stored in slot i-1.
def createUserList():
    fusers = open("u.user", "r")

    userList = []
    for line in fusers:
        userInfo = line.strip().split("|")
        userList.append({"age": int(userInfo[1]), "gender": userInfo[2], "occupation": userInfo[3], "zip": userInfo[4]})

    fusers.close()
    return userList

# Reads information about users from the file u.item. This information is stored as a list dictionaries and returned.
# Each dictionary has keys "title", "release date", "video release date", "IMDB url", and "genre". The dictionary for movie i
# is stored in slot i-1.
def createMovieList():
    fitems = open("u.item", "r", encoding="windows-1252")
    itemList = []
    for line in fitems:
        itemInfo = line.strip().split("|")
        itemList.append({"title": itemInfo[1], "release date": itemInfo[2], "video release date": itemInfo[3], "IMDB url": itemInfo[4],
                     "genre": map(int, itemInfo[5:])})

    fitems.close()
    return itemList

# This function reads the file u.genre for the names of genres.
def createGenreList():
    f = open("u.genre", "r")

    L = []
    for line in f:
        L.append(line.split("|")[0])

    f.close()
    return L


# Read ratings from a file u.data. Each rating line consisting of a user, movie, and rating are turned into a length-3 int tuple.
# A list of 100,000 such ratings is returned. The timestamps that appear in each rating are ignored. 
def readRatings():
    ratings = []
    f = open("u.data", "r")

    for line in f:
        data = tuple([int(x) for x in line.split()][:3])
        ratings.append(data)

    f.close()
    return ratings
   
# This function is given a bunch of ratings in the form of a list of (user, movie, rating)-tuple.
# It then creates two data structures, one from the point of view of users and one from the point of view of movies. 
# In addition, the function takes the number of users and movies as parameters and uses these to appopriately initialize
# the rating lists.
def createRatingsDataStructure(numUsers, numItems, ratingTuples):
    # Initialization of rating lists
    ratingsList1 = []
    ratingsList2 = []
    for i in range(numUsers):
        ratingsList1.append({})

    for i in range(numItems):
        ratingsList2.append({})

    # Read each line in the rating file and store it in each 
    # of the two data structures
    for rating in ratingTuples:
        
        ratingsList1[rating[0]-1][rating[1]] = rating[2]
        ratingsList2[rating[1]-1][rating[0]] = rating[2]
    
    return [ratingsList1, ratingsList2]


# returns the mean rating provided by user with id u. The second argument is 
# the ratings list containing ratings per user.
def meanUserRating(u, userRatings):
    return sum(userRatings[u-1].values())/float(len(userRatings[u-1]))

# returns the mean rating received by a movie with id u. The second argument is 
# the ratings list containing ratings per movie. 
def meanMovieRating(u, movieRatings):
    return sum(movieRatings[u-1].values())/float(len(movieRatings[u-1]))

#----------------- FUNCTIONS FOR EVALUATION -----------------------------------------------------
# This function takes a list of raw ratings in the form of (user, movie, rating)-tuples.
# In addition it takes the percentage of the raw ratings that should be placed in the test
# set of ratings. The test set is obtained by randomly selecting the given percent of the raw ratings.
# The remaining unselected ratings are returned as the training set. The test set is a list with
# each element having the form (user, movie, rating). The training set has a similar
# form. it is expected that the user will call this function as 
#             [trainingSet, testSet] = partitionRatings(rawRatings, testPercent)
def partitionRatings(rawRatings, testPercent):
    # Create a random sample of 20,000 integers from the range [0, 100,000) 
    hidden = random.sample(range(len(rawRatings)), int(len(rawRatings)*testPercent/100.0))
    # Store this sample in a dictionary
    hiddenDict = {}
    for i in hidden:
        hiddenDict[i] = 0

    testSet = []
    trainingSet = []
    for i in range(len(rawRatings)):
        if i in hiddenDict:
            testSet.append(rawRatings[i])
        else:
            trainingSet.append(rawRatings[i])
    

    return [trainingSet, testSet]

def rmse(actualRatings, predictedRatings):
    sumSquares = 0
    for i in range(len(actualRatings)):
        sumSquares = (actualRatings[i] - predictedRatings[i])**2 + sumSquares

    return math.sqrt(sumSquares/float(len(actualRatings)))
#------------ END EVALUATION FUNCTIONS -------------------------------

#------------ SIMPLE PREDICTION ALGORITHMS ----------------------------

def randomPrediction(user, movie):
    return random.randint(1, 5)

def meanUserRatingPrediction(user, movie, userRatings):
    if userRatings[user-1]:
        return meanUserRating(user, userRatings)
    else:
        return random.randint(1, 5)

def meanMovieRatingPrediction(user, movie, movieRatings):
    if movieRatings[movie-1]:
        return meanMovieRating(movie, movieRatings)
    else:
        return random.randint(1, 5)

def meanRatingPrediction(user, movie, userRatings, movieRatings):
    if userRatings[user-1] and movieRatings[movie-1]:
        return (0.5*meanUserRating(user, userRatings) + 0.5*meanMovieRating(movie, movieRatings))
    elif userRatings[user-1]:
        return meanUserRating(user, userRatings)
    elif movieRatings[movie-1]:
        return meanMovieRating(movie, movieRatings)
    else:
    	return random.randint(1, 5)
#--------------------------Phase 2-------------------------------#

# takes as parameters two ID's of user u and v and and the data structure user
# ratings. This returns a value between 1 and -1 depending on how similar the two
# users have rated each movie
def similarity(u, v, userRatings):
    
    C = [x for x in userRatings[u-1] if x in userRatings[v-1]]     

    numerator = 0
    denominator1 = 0
    denominator2 = 0
    

    if len(C) == 0:
        return 0
    
    for movie in C:
        rim = userRatings[u-1][movie]
        rI  = meanUserRatingPrediction(u,movie,userRatings)
        rjm = userRatings[v-1][movie]
        rJ  = meanUserRatingPrediction(v,movie,userRatings)
        numerator = numerator + (rim-rI)*(rjm-rJ)
        denominator1 = denominator1 +(rim-rI)**2
        denominator2 = denominator2 +(rjm-rJ)**2
    
    denominator = (math.sqrt(denominator1)*math.sqrt(denominator2))
    
    if denominator ==0:
        return 0
    
    return numerator/denominator
    

# takes as a parameter a user u and userRatings tuples and a value k
# it will return a list of (userId, similarity) pairs for k that are the most similar
# to user u

def kNearestNeighbors(u, userRatings, k):
    
    # finds each users similarity to user u
    kNeighbors = [(x,similarity(u,x,userRatings)) for x in range(943) if x!= u]
    
    # sorts kNeighbors by index 1 
    kNeighbors.sort(key=lambda kNeighbors: kNeighbors[1],reverse = True)
    
       
    return kNeighbors[:k]
	    
    
# takes as parameters a user u, a movie m, the dataStrucutre userRatings, 
# and freinds. Friends is the list of k users that are most similar to u
# it then returns a prediction for u based on the collaborative filtering 
# algorithm 
def CFRatingPrediction(u,m, userRatings, friends):
    # U is a set of userID's who are similar to u and have rated movie m
    
    U = [user[0] for user in friends if m in userRatings[user[0]-1]]    
    numerator = 0
    denominator = 0
    
    # will solve the numerator in the similarity algorithm
    for user in U:
        
        rjm = userRatings[user-1][m]
	
        rJ = meanUserRatingPrediction(user,m,userRatings)
        simU = similarity(u,user,userRatings)
        numerator = numerator + (rjm-rJ)*simU 
        denominator = denominator + math.fabs(simU)
    
    # if the denominator is zero then all that is need is the meanUser rating
    if denominator == 0:
        return meanUserRatingPrediction(u,m,userRatings)
    
    # either increases or decreases mean user rating depending on if 
    # users who have rated a movie m rated high or low
    return meanUserRatingPrediction(u,m,userRatings) + numerator/denominator 
    
# smilar to CFRatingPrediction other than a movies mean rating is also taken into acount    
def CFMMRatingPrediction(u,m,userRatings,movieRatings, friends):
    
    U = [ user[0] for user in friends if m in userRatings[user[0]-1]]
    numerator = 0
    denominator = 0
    
    for user in U:
        
        rjm = userRatings[user-1][m]
	
        rJ = meanUserRatingPrediction(user,m,userRatings)
        simU = similarity(u,user,userRatings)
        numerator = numerator + (rjm-rJ)*simU
	
        denominator = denominator + math.fabs(simU)
    # if denominator is zero mean movie rating is added to mean user rating is averaged
    if denominator == 0:
        return (meanMovieRatingPrediction(u,m,movieRatings)+meanUserRatingPrediction(u,m,userRatings))/2
    # mean is equal to mean movie rating + mean user rating + how similar users voted for movie m and averaged
    return (meanMovieRatingPrediction(u,m,movieRatings)+meanUserRatingPrediction(u,m,userRatings) + numerator/denominator )/2   
      


#-----------------------Main Program------------------#

# Read and store in data structures
userList = createUserList()
numUsers = len(userList)
movieList = createMovieList()
numMovies = len(movieList)
rawRatings = readRatings()


# Create training and testing set
[trainingSet, testingSet] = partitionRatings(rawRatings, 20)
[trUserRatings, trMovieRatings] = createRatingsDataStructure(numUsers, numMovies, trainingSet)

# Extract acutal ratings from testing set
actualRatings = [x[2] for x in testingSet]


print("Finding predicted ratings...")

friendsList = [kNearestNeighbors(x, trUserRatings,numUsers) for x in range(943)]

    # CFR and CFMMR with k = 0
print("Computing Collaborative Filtering for k=0")
meanCFRatings = [CFRatingPrediction(x[0],x[1],trUserRatings, []) for x in testingSet]
meanCFMMRatings = [CFMMRatingPrediction(x[0],x[1],trUserRatings,trMovieRatings,[]) for x in testingSet]

print("Computing Collaborative Filtering for k=25")    
    # CFR and CFMMR with k = 25
meanCFRatings25 = [CFRatingPrediction(x[0],x[1],trUserRatings,friendsList[x[0]-1][:25]) for x in testingSet]
meanCFMMRatings25 = [CFMMRatingPrediction(x[0],x[1],trUserRatings,trMovieRatings,friendsList[x[0]-1][:25]) for x in testingSet]

print("Computing Collaborative Filtering for k=300")    
    # CFR and CFMMR with k = 300
meanCFRatings300 = [CFRatingPrediction(x[0],x[1],trUserRatings,friendsList[x[0]-1][:300]) for x in testingSet]
meanCFMMRatings300 = [CFMMRatingPrediction(x[0],x[1],trUserRatings,trMovieRatings,friendsList[x[0]-1][:300]) for x in testingSet]

print("Computing Collaborative Filtering for k =500")    
    # CFR and CFMMR with k = 500
meanCFRatings500 = [CFRatingPrediction(x[0],x[1],trUserRatings,friendsList[x[0]-1][:500]) for x in testingSet]
meanCFMMRatings500 = [CFMMRatingPrediction(x[0],x[1],trUserRatings,trMovieRatings,friendsList[x[0]-1][:500]) for x in testingSet]

print("Computing Collaborative Filtering for all users")    
    # CFR and CFMMR with all users
meanCFRatingsAll = [CFRatingPrediction(x[0],x[1],trUserRatings, friendsList[x[0]-1]) for x in testingSet]
meanCFMMRatingsAll = [CFMMRatingPrediction(x[0],x[1],trUserRatings,trMovieRatings,friendsList[x[0]-1]) for x in testingSet]

print("Computing four original prediction Algorithms")    
    # original 4 prediction algorithms
randomRatings = [randomPrediction(x[0], x[1]) for x in testingSet]
meanMovieRatings = [meanMovieRatingPrediction(x[0], x[1], trMovieRatings) for x in testingSet]
meanUserRatings = [meanUserRatingPrediction(x[0], x[1], trUserRatings) for x in testingSet]
meanRatings = [meanRatingPrediction(x[0], x[1], trUserRatings, trMovieRatings) for x in testingSet]
   
print("results\n")   
    
    
    
    
    
                         

# Compute RMSE for each algorithm and produce output
print("Random Prediction RMSE:", rmse(actualRatings,randomRatings))
print("Simple User-based Mean Prediction RMSE:", rmse(actualRatings,meanUserRatings))
print("Simple Movie-based Mean Prediction RMSE:", rmse(actualRatings,meanMovieRatings))
print("Simple User-Movie-based Mean Prediction RMSE:", rmse(actualRatings,meanRatings))
print("Collaborative filtering RMSE 0: ", rmse(actualRatings,meanCFRatings))
print("Collaborative filtering RMSE 0 with mm: ", rmse(actualRatings,meanCFMMRatings))
print("Collaborative filtering RMSE 25: ", rmse(actualRatings,meanCFRatings25))
print("Collaborative filtering RMSE 25 with mm: ", rmse(actualRatings,meanCFMMRatings25))
print("Collaborative filtering RMSE 300: ", rmse(actualRatings,meanCFRatings300))
print("Collaborative filtering RMSE 300 with mm: ", rmse(actualRatings, meanCFMMRatings300))
print("Collaborative filtering RMSE 500: ", rmse(actualRatings, meanCFRatings500))
print("Collaborative filtering RMSE 500 with mm: ", rmse(actualRatings, meanCFMMRatings500))
print("Collaborative filtering RMSE all: ", rmse(actualRatings, meanCFRatingsAll))
print("Collaborative filtering RMSE all with mm: ", rmse(actualRatings, meanCFMMRatingsAll))
