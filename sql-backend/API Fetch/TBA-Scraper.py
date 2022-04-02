import requests

api_key = ''

with open('api-key.txt') as filestream: api_key = filestream.readlines() #Load API_KEY from file

for key in api_key: api_key = key #For each key in our api_key list (there is only one) Set our api_key to the extracted key
	
baseURL = 'http://www.thebluealliance.com/api/v3/' #The API we will be scraping
header = {'X-TBA-Auth-Key':api_key} #Create our request header

#A magic function that makes a web request. The world may never know what it truly does!
def getTBA(url): return requests.get(baseURL + url, headers=header).json()

teamsAtChamps = getTBA("event/2022wimi/teams")

#Assemble match scrapes from given match keys
def AssembleMatchScrapes(match_keys):
	
	scrapes = []
	
	for key in match_keys: 
		scrape = "/match/" + str(key)
		scrapes.append(scrape)
		
	return scrapes
	
#Fetch a list of match keys from an event specified by the event key
def FetchMatchKeys(event_key):
	match_keys = getTBA("event/" + event_key + "/matches/keys")
	return match_keys
	
#Fetch match data by scraping the TBA API for each match
def FetchMatchData(match_scrapes):
	
	match_data = []
	
	for scrape in match_scrapes:
		match_data.append(getTBA(scrape))
		print(match_data)
		
	return match_data


match_keys = FetchMatchKeys("2022wimi")
match_scrapes = AssembleMatchScrapes(match_keys)
match_data = FetchMatchData(match_scrapes)

print(str(match_keys) + " match keys")
print(str(match_scrapes) + " match scrapes")
print(str(match_data) + " match data")