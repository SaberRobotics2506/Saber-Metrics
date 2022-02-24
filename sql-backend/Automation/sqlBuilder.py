####################IMPORTS####################

#Imports from python
import json

#Imports from subprocess
import subprocess

#Imports from os
import os
from os import listdir

#Imports from os.path
from os.path import isfile
from os.path import join

####################CONSTANTS####################

BUILDER_OUTPUT_FILE_NAME = "sqlBuilderOutput.sql" #This is the filename of the build output SQL file
CURRENT_WORKING_DIRECTORY = os.getcwd() #Get current working directory (cwd) of this file
SEARCH_FOR_FILETYPE = ".scout" #The filetype to include in the file searches
TABLE_NAME = "MatchMaster1" #The name of the SQL table we are writing queries for

####################FUNCTIONS####################

def ReadJSON():
    # This function loops over every "*.scout" file and checks it's contents...
    # If the file is in a valid JSON format, we will read it's elements and store them in a dictionary
    # Once we finish storing the files elements we will add the dictionry to a list
    # This function will return a list of dictionaries representing each files' JSON Data
    # EXAMPLE RETURN: [{'firstFileOneInteger': 1, secondFileOneInteger: 2}, {file2Integer: 2}]
    # NOTE: This function will only read the "*.scout" files in the current working directory

	files = os.listdir(CURRENT_WORKING_DIRECTORY) #Read all files from cwd and add to file list
	
	data_list = [] #Declaring an empty list of data dictionaries to append to
	
	for file in files: #Loop through all files in the file list
		if(SEARCH_FOR_FILETYPE in file): #If the filetype is of ".scout"...
			try:
				with open(file) as json_data: #Read json data of file into "json_data"
					data = json.load(json_data) #Save loaded json data to a "data" dictionary
					data_list.append(data) #Append the data dictionary to the list we defined above
			except:
				print("Unable to read \"*.scout\" file becasue it is not in a valid JSON format | ERROR") #Print an error message to the console'
	
	return data_list #Returns a list of dictionaries representing each files' JSON Data

def BuildInsertQueries(data):
	# This function builds SQL queries from each dictionary KeyValuePair in a list of dictionaries
	# This function will loop over every JSON dictionary in the list of dictionaries and then loop over Each KeyValuePair in the dictionary
	# We will then generate an insert querry by taking using the key as the column and the value as the data point
	# EXAMPLE QUERY: INSERT INTO MatchMaster1 (ScoutedBy)VALUES(John Doe)
    
	query_list = [] #Define an empty list to store SQL querries in
	TABLE_NAME = "MatchMaster1" #Store the name of the table to be referenced again later when creating the query
	
	for json_dictionary in data: #For each JSON dictionary in the data list...
		for key, value in json_dictionary.items(): #Loop through all keys and values in the dictionary for this JSON file
			query = "INSERT INTO " + TABLE_NAME #Start constructing this INSERT query using the table name specified
			
			if(type(value) is int): #If the value of the current field is an intager
				query = query + "(" + str(key) + ")VALUES(" + str(value) + ") --Generated Integer Query\n" #Create an INSERT query for this KeyValuePair as an int query
			elif(type(value) is str):
				query = query + "(" + str(key) + ")VALUES('" + str(value) + "') --Generated String Query\n" #Create an INSERT query for this KeyValuePair as a string query
			else:
				query = query + "--Failed to generate query of unkown type" #Create an INSERT query for this KeyValuePair
				
			query_list.append(query) #Append the query we created to the query_list
            
	return query_list #Return the list of queries that we have created
	
def WriteQueryFile(query_list):

	# This function loops through all the queries in the query list and appends them to a string
	# Once appended the string will be written to the file specified in the constant BUILDER_OUTPUT_FILE_NAME
	# This file can then either be ran manually by the user or triggered via another function
	# NOTE: This function does not return anything, and build output is in the current working directory
	
	with open(BUILDER_OUTPUT_FILE_NAME, "w+") as builderOutputFile:
		fileContents = ("--This SQL query file was built with the SQL Builder python script\n\n")
		
		for query in query_list:
			fileContents = fileContents + (str(query))
		
		builderOutputFile.write(fileContents)
		
def RunSQLQuery():
	cwd = os.getcwd() #Get current working directory (cwd) of this file
	subprocess.call(CURRENT_WORKING_DIRECTORY + BUILDER_OUTPUT_FILE_NAME)
	
####################MAIN####################
    
data = ReadJSON()
queries = BuildInsertQueries(data)
WriteQueryFile(queries)

#	RunSQLQuery()