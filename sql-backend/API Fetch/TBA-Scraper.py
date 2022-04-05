import requests
import time
import os

api_key = ''

with open('api-key.txt') as filestream: api_key = filestream.readlines() #Load API_KEY from file

for key in api_key: api_key = key #For each key in our api_key list (there is only one) Set our api_key to the extracted key
	
baseURL = 'http://www.thebluealliance.com/api/v3/' #The API we will be scraping
header = {'X-TBA-Auth-Key':api_key} #Create our request header

clear = lambda: os.system('cls') #Create "clear" lamba to communicate with the console

# Print/Update the progress bar
def printProgressBar (iteration, total, prefix = '', suffix = '', decimals = 1, length = 100, fill = '█', printEnd = "\r"):
    """
    Call in a loop to create terminal progress bar
    @params:
        iteration   - Required  : current iteration (Int)
        total       - Required  : total iterations (Int)
        prefix      - Optional  : prefix string (Str)
        suffix      - Optional  : suffix string (Str)
        decimals    - Optional  : positive number of decimals in percent complete (Int)
        length      - Optional  : character length of bar (Int)
        fill        - Optional  : bar fill character (Str)
        printEnd    - Optional  : end character (e.g. "\r", "\r\n") (Str)
    """
    percent = ("{0:." + str(decimals) + "f}").format(100 * (iteration / float(total)))
    filledLength = int(length * iteration // total)
    bar = fill * filledLength + '-' * (length - filledLength)
    print(f'\r{prefix} |{bar}| {percent}% {suffix}', end = printEnd)
    # Print New Line on Complete
    if iteration == total: 
        print()
		
#A magic function that makes a web request. The world may never know what it truly does!
def getTBA(url): return requests.get(baseURL + url, headers=header).json()	

teamsAtChamps = getTBA("event/2022wimi/teams")

#Assemble match scrapes from given match keys
def AssembleMatchScrapes(match_keys):
	
	scrapes = []
	
	for key in match_keys: 	
		scrape = "/match/" + str(key) + "/simple"
		scrapes.append(scrape)
		
	return scrapes

	
#Fetch a list of match keys from an event specified by the event key
def FetchMatchKeys(event_key):
	match_keys = getTBA("event/" + event_key + "/matches/keys")
	return match_keys
	
#Fetch match data by scraping the TBA API for each match
def FetchMatchData(match_scrapes):
	
	match_data = []
	
	total_stages = len(match_scrapes)
	current_stage = 0
	
	for scrape in match_scrapes:
		match_data.append(getTBA(scrape))
		
		current_stage = current_stage + 1
		printProgressBar(current_stage, total_stages, prefix = 'Fetching: ' + str(scrape), suffix = 'Complete ', length = 50)
		
	return match_data

def GetAlliance(match_data):
	print (match_data)


print("Fetching match keys...")
match_keys = FetchMatchKeys("2022wimi")
print("Done!\n")

match_scrapes = AssembleMatchScrapes(match_keys)
match_data = FetchMatchData(match_scrapes)

for data in match_data:
	GetAlliance(data)

print(str(match_keys) + " match keys")
print(str(match_scrapes) + " match scrapes")
print(str(match_data) + " match data")