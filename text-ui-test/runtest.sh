#!/usr/bin/env bash

# Create bin directory if it doesn't exist
if [ ! -d "../bin" ]; then
    mkdir ../bin
fi

# Delete output from previous run
rm -f ACTUAL1.TXT ACTUAL2.TXT

# Compile the code into the bin folder, terminate if error occurs
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/nightcoder/NightCoder.java; then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# Run first test case (input1.txt) without tasks.txt
if [ -e "data/tasks.txt" ]; then
    rm data/tasks.txt
fi

java -classpath ../bin nightcoder.NightCoder < input1.txt > ACTUAL1.TXT

# convert to UNIX format
cp EXPECTED1.TXT EXPECTED1-UNIX.TXT
dos2unix ACTUAL1.TXT EXPECTED1-UNIX.TXT

# Compare output to expected output
if ! diff ACTUAL1.TXT EXPECTED1-UNIX.TXT > /dev/null; then
    echo "********** TEST 1 FAILED **********"
else
    echo "********** TEST 1 PASSED **********"
fi

# Prepare tasks.txt for second test case (input2.txt)
cat <<EOL > data/tasks.txt
T|0|Go jogging
T|1|Go fishing
D|0|Homework|Tonight
D|1|Submit interview application|1 Feb
E|0|My birthday party|3 Feb 3pm|5pm
E|1|New Year's Celebration|1 Jan 12pm|3pm
EOL

# Run second test case (input2.txt)
java -classpath ../bin nightcoder.NightCoder < input2.txt > ACTUAL2.TXT

# convert to UNIX format
cp EXPECTED2.TXT EXPECTED2-UNIX.TXT
dos2unix ACTUAL2.TXT EXPECTED2-UNIX.TXT

# Compare output to expected output
if ! diff ACTUAL2.TXT EXPECTED2-UNIX.TXT > /dev/null; then
    echo "********** TEST 2 FAILED **********"
else
    echo "********** TEST 2 PASSED **********"
fi

# convert to UNIX format
cp EXPECTED2_TASKS.TXT EXPECTED2_TASKS-UNIX.TXT
cp data/tasks.txt ACTUAL2_TASKS-UNIX.TXT
dos2unix ACTUAL2_TASKS-UNIX.TXT EXPECTED2_TASKS-UNIX.TXT

# Ensure the final data/tasks.txt matches EXPECTED2_TASKS.TXT
if ! diff ACTUAL2_TASKS-UNIX.TXT EXPECTED2_TASKS-UNIX.TXT > /dev/null; then
    echo "********** TEST 2 FINAL TASKS CHECK FAILED **********"
else
    echo "********** TEST 2 FINAL TASKS CHECK PASSED **********"
fi

echo "********** TESTING COMPLETE **********"
