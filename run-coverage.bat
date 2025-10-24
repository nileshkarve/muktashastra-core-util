@echo off
echo Running tests with coverage...
mvn clean test

echo.
echo Coverage report generated at: target\site\jacoco\index.html
echo.
echo Coverage metrics included:
echo - Line coverage: Percentage of executed lines
echo - Branch coverage: Percentage of executed branches/conditions  
echo - Class coverage: Percentage of classes with at least one method executed
echo.
echo Opening coverage report...
start target\site\jacoco\index.html