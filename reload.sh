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

perl bin/parser.pl $FILE
ant
