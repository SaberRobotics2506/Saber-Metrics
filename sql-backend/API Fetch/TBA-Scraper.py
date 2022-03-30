import requests

baseURL = 'http://www.thebluealliance.com/api/v3/'
header = {'X-TBA-Auth-Key':'0d0KrYpvEPhoi2zN9wskkcfRNYlDRSqxdey21G7XaLWau0BXJYLfKWLLXWsx7BXB'} 

def getTBA(url):
	return requests.get(baseURL + url, headers=header).json()

event_key = ""

teamsAtChamps = getTBA("event/2022wimi/teams")

for team in teamsAtChamps:
	#print(team)
	team_number = team['team_number']
	print(team_number)