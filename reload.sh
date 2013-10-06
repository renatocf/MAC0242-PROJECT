#!/bin/sh
# reload.sh

if [ $# != 1 ]; then
    echo "Usage reload.sh program.txt"
    exit
fi

FILE=$1
if ! [ -f $FILE ]; then
    echo "File does not exist!"
    exit
fi

echo
echo "Creating .java file for $FILE"
echo "-----------------------------------------------------------------"
perl bin/parser.pl $FILE

echo
echo "Rebuilding project"
echo "-----------------------------------------------------------------"
ant
