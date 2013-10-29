#!/bin/sh
# reload.sh

if [ $# -eq 0 ]; then
    echo "USAGE: sh reload.sh program1.asm program2.asm ..."
    exit
fi

for f in $*
do
    echo "================================================================="
    
    if ! [ -f $f ]; then
        echo "File does not exist!"
        continue
    fi
    
    echo
    echo "Creating .java file for $f"
    echo "-----------------------------------------------------------------"
    perl bin/parser.pl $f
done

echo
echo "Rebuilding project"
echo "-----------------------------------------------------------------"
ant
