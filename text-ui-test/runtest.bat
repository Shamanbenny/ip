@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL1.TXT del ACTUAL1.TXT
if exist ACTUAL2.TXT del ACTUAL2.TXT

REM compile the code into the bin folder
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM Run first test case (input1.txt) without tasks.txt
if exist data\tasks.txt del data\tasks.txt

java -classpath ..\bin NightCoder < input1.txt > ACTUAL1.TXT

REM compare output to expected output
FC ACTUAL1.TXT EXPECTED1.TXT
IF ERRORLEVEL 1 (
    echo ********** TEST 1 FAILED **********
) ELSE (
    echo ********** TEST 1 PASSED **********
)

REM Prepare tasks.txt for second test case (input2.txt)
(
    echo T^|0^|Go jogging
    echo T^|1^|Go fishing
    echo D^|0^|Homework^|Tonight
    echo D^|1^|Submit interview application^|1 Feb
    echo E^|0^|My birthday party^|3 Feb 3pm^|5pm
    echo E^|1^|New Year's Celebration^|1 Jan 12pm^|3pm
) > data\tasks.txt

REM Run second test case (input2.txt)
java -classpath ..\bin NightCoder < input2.txt > ACTUAL2.TXT

REM compare output to expected output
FC ACTUAL2.TXT EXPECTED2.TXT
IF ERRORLEVEL 1 (
    echo ********** TEST 2 FAILED **********
) ELSE (
    echo ********** TEST 2 PASSED **********
)

echo ********** TESTING COMPLETE **********