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
def getTBA(url): return requests.get(baseURL + url, headers=header).json()	

teamsAtChamps = getTBA("event/2022wimi/teams")

#Assemble match scrapes from given match keys
def AssembleMatchScrapes(match_keys):
	
	scrapes = []
	
	for key in match_keys:
		print("Assembling API scrape from key: " + str(key))
		scrape = "/match/" + str(key) + "/simple"
		print("Appending [ " + str(scrape) + " ] to scrape list...")
		scrapes.append(scrape)
	
	time.sleep(1)
	print("\n\n")
	return scrapes
	
#Fetch a list of match keys from an event specified by the event key
def FetchMatchKeys(event_key):
	print("Fetching match keys from upstream...")
	match_keys = getTBA("event/" + event_key + "/matches/keys")
	print(match_keys)
	print("Done!")
	time.sleep(1)
	print("\n\n")
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
		
	
	print(match_data)
	
	time.sleep(1)
	print("\n\n")
	return match_data

def ParseMatchData(match_data):
	
	parsed_data = []
	
	for data in match_data:
		print("Dumping: " + str(data))
		data = json.dumps(data)
		print("Parsing: " + str(data))
		parsed_data.append(json.loads(str(data)))
		print("Done!")
	
	time.sleep(1)
	print("\n\n")
	return parsed_data
	
def ExtractRedTeams(parsed_data):
	
	red_teams = []
	
	for i in range(len(parsed_data)):
		print("Finding red alliance entry at index " + str(i) + "...")
		entry = parsed_data[i]
		print("Reading: " + str(entry))
		red_teams.append(entry["alliances"]["red"]["team_keys"])
		print("Appending: " + str(red_teams[i]))
		print("Done!")
	
	time.sleep(1)
	print("\n\n")
	return red_teams

def ExtractBlueTeams(parsed_data):
	
	blue_teams = []
	
	for i in range(len(parsed_data)):
		print("Finding blue alliance entry at index " + str(i) + "...")
		entry = parsed_data[i]
		print("Reading: " + str(entry))
		blue_teams.append(entry["alliances"]["red"]["team_keys"])
		print("Appending: " + str(blue_teams[i]))
		print("Done!")
	
	time.sleep(1)
	print("\n\n")
	return blue_teams
	
def SerializeTeams(red_teams, blue_teams):
	with open('red.teams', 'w', encoding='utf-8') as filestream:
		print("Opened filestream " + str(filestream))
		json.dump(red_teams, filestream, ensure_ascii=False, indent=4)
		print("File Written: 'red.teams' with contents " + str(red_teams))
	print("Closed Filestream: " + str(filestream))
	
	with open('blue.teams', 'w', encoding='utf-8') as filestream:
		print("Opened filestream " + str(filestream))
		json.dump(red_teams, filestream, ensure_ascii=False, indent=4)
		print("File Written: 'blue.teams' with contents " + str(blue_teams))
		print("Closed Filestream: " + str(filestream))
	
	print("\n\n")
	
match_keys = FetchMatchKeys("2022wimi")
match_scrapes = AssembleMatchScrapes(match_keys)
match_data = FetchMatchData(match_scrapes)
parsed_data = ParseMatchData(match_data)

red_teams = ExtractRedTeams(parsed_data)
blue_teams = ExtractBlueTeams(parsed_data)

SerializeTeams(red_teams, blue_teams)