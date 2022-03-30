import requests

baseURL = 'http://www.thebluealliance.com/api/v3/'
header = {'X-TBA-Auth-Key':'0d0KrYpvEPhoi2zN9wskkcfRNYlDRSqxdey21G7XaLWau0BXJYLfKWLLXWsx7BXB'} #bad api key

def getTBA(url): return requests.get(baseURL + url, headers=header).json()

teamsAtChamps = getTBA("event/2022wimi/teams")

def GetMatchKeys(event_key): #Fetch a list of match keys from an event specified by the event key
	match_keys = getTBA("event/" + event_key + "/matches/keys")
	return match_keys
	
def GetMatchDataFromKey(match_key): #Fetch match data from a match specified the match key
	match_data = getTBA()

print(GetMatchKeys("2022wimi"))