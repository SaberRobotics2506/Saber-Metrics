import requests
import time
import os
import json

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
def getTBA(url): 
	try: #Attempt to make a web request...
		return requests.get(baseURL + url, headers=header).json()
	except:
		print("Error fetching data, please verify your token or check your internet connection.")
		exit(1) #Terminate the application with exit code 1 (error)

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
		printProgressBar(current_stage, total_stages, prefix = 'Downloading: ' + str(scrape), suffix = 'Complete ', length = 50)
	
	return match_data

def ParseMatchData(match_data):
	
	parsed_data = []
	
	for data in match_data:
		data = json.dumps(data)
		parsed_data.append(json.loads(str(data)))
	
	return parsed_data
	
def ExtractRedTeams(parsed_data):
	
	red_teams = []
	
	for i in range(len(parsed_data)):
		entry = parsed_data[i]
		red_teams.append(entry["alliances"]["red"]["team_keys"])
	
	time.sleep(1)
	return red_teams

def ExtractBlueTeams(parsed_data):
	
	blue_teams = []
	
	for i in range(len(parsed_data)):
		entry = parsed_data[i]
		blue_teams.append(entry["alliances"]["red"]["team_keys"])
	
	return blue_teams
	
RED_FILE = "red.teams"
BLUE_FILE = "blue.teams"

def SerializeTeams(red_teams, blue_teams):
	with open(RED_FILE, 'w', encoding='utf-8') as filestream:
		json.dump(red_teams, filestream, ensure_ascii=False, indent=4)
		
	with open(BLUE_FILE, 'w', encoding='utf-8') as filestream:
		json.dump(blue_teams, filestream, ensure_ascii=False, indent=4)
				

# This function is responsible for correcting the JSON format into GSON
# By replacing the square brackets with curly brackets		
def CorrectJSONBrackets(red_teams, blue_teams):
	
	RED_FILE = "red.teams" #the file for storing red alliance team data
	BLUE_FILE = "blue.teams" #the file for storing blue alliance team data
	
	#Read the *.teams file into a a new string [red_contents]
	with open(RED_FILE, 'r') as filestream:
		red_contents = filestream.readlines()
		filestream.close()
	
	#Read the *.teams file into a a new string [blue_contents
	with open(BLUE_FILE, 'r') as filestream:
		blue_contents = filestream.readlines()
		filestream.close()
		
	
	#Correct Malformed JSON Characters in red context
	red_contents = "{" + str(red_contents[1:-2]) + "}"
	red_contents = red_contents.replace("\\n", "\n")
	red_contents = red_contents.replace("', '", "")
	red_contents = red_contents.replace("['", "\n")
	red_contents = red_contents.replace("']", "]")
	
	#Correct Malformed JSON CHaracters in blue context
	blue_contents = "{" + str(blue_contents[1:-2]) + "}"
	blue_contents = blue_contents.replace("\\n", "\n")
	blue_contents = blue_contents.replace("', '", "")
	blue_contents = blue_contents.replace("['", "\n")
	blue_contents = blue_contents.replace("']", "]")	
			
	#Give arrays thier JSON labels in the *.teams file for the red alliance
	counter = 0 
	index = red_contents.find("[")
	
	while(index != -1): #-1 = char not found
		counter = counter + 1
		concat = "\"Match " + str(counter) + "\": "
		
		red_contents = red_contents[:index] + concat + red_contents[index:]
		index = red_contents.find("[", index + len(concat) + 5, len(red_contents))
	
	
	#Give arrays thier JSON labels in the *.teams file for the blue alliance
	counter = 0
	index = blue_contents.find("[")
	
	while(index != -1): #-1 = char not found
		counter = counter + 1
		concat = "\"Match " + str(counter) + "\": "
		
		blue_contents = blue_contents[:index] + concat + blue_contents[index:]
		index = blue_contents.find("[", index + len(concat) + 5, len(blue_contents))
		
		
	#Write the *.teams file for the red alliance back into the file system
	with open(RED_FILE, 'w', encoding='utf-8') as filestream:
		filestream.write(red_contents)
		filestream.close()
		
	#Write the *.teams file for the blue alliance back into the file system
	with open(BLUE_FILE, 'w', encoding='utf-8') as filestream:
		filestream.write(blue_contents)
		filestream.close()
	
match_keys = FetchMatchKeys("2022wimi")
match_scrapes = AssembleMatchScrapes(match_keys)
match_data = FetchMatchData(match_scrapes)
parsed_data = ParseMatchData(match_data)

red_teams = ExtractRedTeams(parsed_data)
blue_teams = ExtractBlueTeams(parsed_data)

SerializeTeams(red_teams, blue_teams)
CorrectJSONBrackets(red_teams, blue_teams)