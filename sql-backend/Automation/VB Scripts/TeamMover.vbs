Option Explicit
Dim teamMover

Set teamMover = CreateObject("Scripting.FileSystemObject")

teamMover.CopyFile Replace(teamMover.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\red.teams", Replace(teamMover.GetAbsolutePathName("."), "sql-backend\Automation\VB Scripts", "app\src\main\assets") & "\red.teams"
teamMover.CopyFile Replace(teamMover.GetAbsolutePathName("."), "VB Scripts", "API Fetch") & "\blue.teams", Replace(teamMover.GetAbsolutePathName("."), "sql-backend\Automation\VB Scripts", "app\src\main\assets") & "\blue.teams"