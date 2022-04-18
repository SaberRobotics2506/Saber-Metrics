'This VBScript is responsible for converting the JSON files outputed by API-Scraper.py into a format readable by Android's GSON component

'@Dominic write here!

Option Explicit
Dim GSONCFileInteractor, GSONCRedFile, GSONCBlueFile, strRedFileContents, strBlueFileContents, GSONCRedWriteOut, GSONCBlueWriteOut

Set GSONCFileInteractor = CreateObject("Scripting.FileSystemObject")

Set GSONCRedFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams")
strRedFileContents = GSONCRedFile.ReadAll
GSONCRedFile.Close
strRedFileContents = Replace(strRedFileContents, chr(34), "") 'chr(34) is the double quote character
strRedFileContents = Replace(strRedFileContents, "frc", "")

Set GSONCBlueFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams")
strBlueFileContents = GSONCBlueFile.ReadAll
GSONCBlueFile.Close
strBlueFileContents = Replace(strBlueFileContents, chr(34), "") 'chr(34) is the double quote character
strBlueFileContents = Replace(strBlueFileContents, "frc", "")

If GSONCFileInteractor.FileExists(GSONCFileInteractor.GetAbsolutePathName(".") & "\red.teams") Then
	Set GSONCRedWriteOut = GSONCFileInteractor.OpenTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "\red.teams", 2)
Else
	Set GSONCRedWriteOut = GSONCFileInteractor.CreateTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "\red.teams", 2)
End If
GSONCRedWriteOut.write strRedFileContents

If GSONCFileInteractor.FileExists(GSONCFileInteractor.GetAbsolutePathName(".") & "\blue.teams") Then
	Set GSONCBlueWriteOut = GSONCFileInteractor.OpenTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "\blue.teams", 2)
Else
	Set GSONCBlueWriteOut = GSONCFileInteractor.CreateTextFile(GSONCFileInteractor.GetAbsolutePathName(".") & "\blue.teams", 2)
End If
GSONCBlueWriteOut.write strBlueFileContents