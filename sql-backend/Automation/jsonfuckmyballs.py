	RED_FILE = "red.teams"
	BLUE_FILE = "blue.teams"
	
	red_contents = ""
	blue_contents = ""
	
	with open(RED_FILE, 'r') as filestream:
		red_contents = filestream.readlines()
		filestream.close()
		
	with open(BLUE_FILE, 'r') as filestream:
		blue_contents = filestream.readlines()
		filestream.close()

	index = 0
	counter = 0
	
	for char in red_contents:
		index = index + 1
		
		if char == "[":
			counter = counter + 1
			red_contents = red_contents[:index - 1] + "\"Match " + str(counter) + "\": " + red_contents[index:]
			
	index = 0
	counter = 0
	
	for char in blue_contents:
		index = index + 1
		
		if char == "[":
			counter = counter + 1
			blue_contents = blue_contents[:index - 1] + "\"Match " + str(counter) + "\"" + blue_contents[index:]
	
	print(red_contents)