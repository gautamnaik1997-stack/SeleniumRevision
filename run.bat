@echo off

echo ==================================
echo SeleniumRevision Automation Execution
echo ==================================

cd /d K:\SeleniumRevision\SeleniumRevision
mvn clean test

echo.
echo Execution Completed
echo.

pause