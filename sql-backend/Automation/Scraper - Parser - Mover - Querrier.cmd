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


echo Running API Fetch/runTBA.cmd...
cd "API Fetch"
start /W /max runTBA.cmd
cd ..

echo Executing "VB Scripts/TeamMover.vbs"
cd "VB Scripts"
start /W /max TeamMover.vbs
cd ..

echo Executing "SQL Builder/run-py.cmd"
cd "SQL Builder"
start /W /max run-py.cmd
cd ..