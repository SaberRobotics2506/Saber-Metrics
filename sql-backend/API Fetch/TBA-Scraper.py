import requests

baseURL = 'http://www.thebluealliance.com/api/v3/'
header = {'X-TBA-Auth-Key':'0d0KrYpvEPhoi2zN9wskkcfRNYlDRSqxdey21G7XaLWau0BXJYLfKWLLXWsx7BXB'} 

def getTBA(url): return requests.get(baseURL + url, headers=header).json()

teamsAtChamps = getTBA("event/2022wimi/teams")
matches = getTBA("event/2022wimi/matches")

api_scrapes = [getTBA("event/2022wimi/teams"), getTBA("event/2022wimi/matches")] #Contain all API scrapes in an array

for scrape in api_scrapes: #For each scrape in our set of api scrapes...
	for data_obj in scrape: #For each data object in our scrape...
		if 'team_number' in data_obj:
			print(data_obj['team_number'])
		elif 'match_number' in data_obj:
			print(data_obj['match_number'])
			