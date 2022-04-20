@echo off
goto check_Permissions

:check_Permissions
    echo Administrative permissions required. Detecting permissions...
    
    net session >nul 2>&1
    if %errorLevel% == 0 (
        echo Success: Administrative permissions confirmed.
    ) else (
        echo Failure: Current permissions inadequate.
	pause > nul
    )
   
cd %~dp0
cls


echo Executing "API Fetch/TBA-Scraper.py"
cd "API Fetch"
python TBA-Scraper.py
cd ..

echo Executing "VB Scripts/GSON_Converter.vbs"
cd "VB Scripts"
start /W /max GSON_Converter.vbs
cd ..

echo Executing "VB Scripts/TeamMover.vbs"
cd "VB Scripts"
start /W /max TeamMover.vbs
cd ..


echo Executing "SQL Builder/sqlBuilder.py"
cd "SQL Builder"
python sqlBuilder.py
cd ..

pause > nul