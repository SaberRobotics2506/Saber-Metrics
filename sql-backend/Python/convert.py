#Imports from python
import json

#Imports from os
import os
from os import listdir

#Imports from os.path
from os.path import isfile
from os.path import join

def ReadAllData():
	searchForFileType = ".scout" #The filetype to include in the file search
	cwd = os.getcwd() #Get current working directory (cwd)
	files = os.listdir(cwd) #Read all files from cwd and add to file list
	
	scoutData = "" #Create scoutData variable
	
	for file in files: #Loop through all files in the file list
		if(searchForFileType in file): #If the filetype is of ".scout"...
			with open(file) as json_data: #Read json data of file into "json_data"
				data = json.load(json_data) #Save loaded json data to "data"
				print(data)
				
ReadAllData()

#with open(jsonFile) as json_data:
#	data = json.load(json_data)
	
	

