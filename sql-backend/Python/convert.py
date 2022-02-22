#Imports from python
import json

#Imports from os
import os
from os import listdir

#Imports from os.path
from os.path import isfile
from os.path import join

# This function loops over every "*.scout" file and checks it's contents...
# If the file is in a valid JSON format, we will read it's elements and store them in a dictionary
# Once we finish storing the files elements we will add the dictionry to a list
# This function will return a list of dictionaries representing each files' JSON Data
# Example return [{'firstFileOneInteger': 1, secondFileOneInteger: 2}, {file2Integer: 2}]
# NOTE: This function will only read the "*.scout" files in the current working directory

def ReadJSON():
	searchForFileType = ".scout" #The filetype to include in the file search
	cwd = os.getcwd() #Get current working directory (cwd) of this file
	files = os.listdir(cwd) #Read all files from cwd and add to file list
	
	data_list = [] #Declaring an empty list of data dictionaries to append to
	
	for file in files: #Loop through all files in the file list
		if(searchForFileType in file): #If the filetype is of ".scout"...
		
			try: #Attempt the following
				with open(file) as json_data: #Read json data of file into "json_data"
					data = json.load(json_data) #Save loaded json data to a "data" dictionary
					data_list.append(data) #Append the data dictionary to the list we defined above
			except: #If we failed to 
				print("Unable to read \"*.scout\" file becasue it is not in a valid JSON format | ERROR") #Print an error message to the console
	
	return data_list #Returns a list of dictionaries representing each files' JSON Data

def CreateSQLQuerry(data):
	for json_dictionary in data: #For each JSON dictionary in the data list...
		for key, value in json_dictionary.items(): #Loop through all keys and values in the dictionary for this JSON file
			print(key)
			print(value)
		
data = ReadJSON()
print(data)

CreateSQLQuerry(data)
	
	

