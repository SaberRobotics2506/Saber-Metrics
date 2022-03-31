import requests

api_key = ''

with open('api-key.txt') as filestream:
    api_key = filestream.readlines()

for key in api_key: #For each key in our api_key list (there is only one)
	api_key = key	#Set our api_key to the extracted key

print(api_key)
	
baseURL = 'http://www.thebluealliance.com/api/v3/'
header = {'X-TBA-Auth-Key':api_key} #bad api key

print(header)

def getTBA(url): return requests.get(baseURL + url, headers=header).json()

teamsAtChamps = getTBA("event/2022wimi/teams")

def AssembleMatchAPIScrapes(match_keys):
	scrapes = []
	
	for key in match_keys:
		scrape = "/match/" + str(key)
		scrapes.append(scrape)
		
	return scrapes

def AssembleMatchKeys(event_key): #Fetch a list of match keys from an event specified by the event key
	match_keys = getTBA("event/" + event_key + "/matches/keys")
	return match_keys
	
def GetMatchData(match_scrape): #Fetch match data from a match specified the match key
	match_data = getTBA(match_scrape)
	return match_data

match_keys = AssembleMatchKeys("2022wimi")
match_scrapes = AssembleMatchAPIScrapes(match_keys)

print(GetMatchData(match_scrapes[0]))
	
print(str(match_keys) + " match keys")
print(str(match_scrapes) + " match scrapes")