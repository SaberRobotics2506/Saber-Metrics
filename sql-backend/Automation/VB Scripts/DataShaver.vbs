' This VBScript is responsible for shaving the data values from frc predicated strings to integers
' In the JSON/GSON files outputed by API-Scraper.py script.
' @Author IBXCODECAT, DOM76

Option Explicit
Dim GSONCFileInteractor, GSONCRedFile, GSONCBlueFile, strRedFileContents, strBlueFileContents, GSONCRedWriteOut, GSONCBlueWriteOut

Set GSONCFileInteractor = CreateObject("Scripting.FileSystemObject")

Set GSONCRedFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams")
strRedFileContents = GSONCRedFile.ReadAll
GSONCRedFile.Close


'NOTE: 'chr(34) is the double quote character and chr(13) is the (CR LF) line break
strRedFileContents = Replace(strRedFileContents, chr(34) + "frc", "") 'Replace `"frc` with nothing, therefore removing it
strRedFileContents = Replace(strRedFileContents, chr(34) + ",", ",") 'Replace `",` with nothing, therefore removing it
strRedFileContents = Replace(strRedFileContents, chr(34) + chr(13), "") 'Replace `" ` with nothing, therefore removing it

Set GSONCBlueFile = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams")
strBlueFileContents = GSONCBlueFile.ReadAll
GSONCBlueFile.Close

'NOTE: 'chr(34) is the double quote character and chr(13) is the (CR LF) line break
strBlueFileContents = Replace(strBlueFileContents, chr(34) + "frc", "") 'Replace `"frc` with nothing, therefore removing it
strBlueFileContents = Replace(strBlueFileContents, chr(34) + ",", ",") 'Replace `",` with nothing, therefore removing it
strBlueFileContents = Replace(strBlueFileContents, chr(34) + chr(13), "") 'Replace `" ` with nothing, therefore removing it

Set GSONCRedWriteOut = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams", 2)
GSONCRedWriteOut.write strRedFileContents
GSONCRedWriteOut.Close

Set GSONCBlueWriteOut = GSONCFileInteractor.OpenTextFile(Replace(GSONCFileInteractor.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams", 2)
GSONCBlueWriteOut.write strBlueFileContents
GSONCBlueWriteOut.Close