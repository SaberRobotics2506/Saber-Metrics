'This VBScript is responsible for converting the JSON files outputed by API-Scraper.py into a format readable by Android's GSON component

'@Dominic write here!

Option Explicit
Dim GSONCFileInteractor

Set GSONCFileInteractor = CreateObject("Scripting.FileSystemObject")

Set GSONCRedFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams")
Do Until GSONCRedFile.AtEndOfStream
	strRedFileContents = GSONCRedFile.ReadLine
Loop
GSONCFile.Close
strRedFileContents = Replace(chr(34), "") 'chr(34) is the double quote character
strRedFileContents = Replace("frc", "")

Set GSONCBlueFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams")
Do Until GSONCBlueFile.AtEndOfStream
	strBlueFileContents = GSONCBlueFile.ReadLine
Loop
GSONCFile.Close
strBlueFileContents = Replace(chr(34), "") 'chr(34) is the double quote character
strBlueFileContents = Replace("frc", "")

If GSONCFileInteractor.FileSystemObject.FileExists(GSONCFileInteractor.GetAbsolutePathName(".") & "red.teams")
	Set GSONCRedWriteOut = GSONCFileInteractor.OpenTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "red.teams")
Else
	Set GSONCRedWriteOut = GSONCFileInteractor.CreateTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "red.teams")
End If
GSONCRedWriteOut.write strRedFileContents

If GSONCFileInteractor.FileSystemObject.FileExists(GSONCFileInteractor.GetAbsolutePathName(".") & "blue.teams")
	Set GSONCBlueWriteOut = GSONCFileInteractor.OpenTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "blue.teams")
Else
	Set GSONCBlueWriteOut = GSONCFileInteractor.CreateTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "blue.teams")
End If
GSONCBlueWriteOut.write strBlueFileContents