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

Set GSONCRedWriteOut = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams", 2)
GSONCRedWriteOut.write strRedFileContents
GSONCRedWriteOut.Close

Set GSONCBlueWriteOut = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams", 2)
GSONCBlueWriteOut.write strBlueFileContents
GSONCBlueWriteOut.Close