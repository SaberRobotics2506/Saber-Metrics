import json

jsonFile = "test.json"
with open(jsonFile) as json_data:
	data = json.load(json_data)
	print(data)