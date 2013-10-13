#!/bin/sh
# fast.sh

if [ $1 = build ]
then
    ant clean
    ant
    sh reload.sh test/test.asm
fi
if [ $1 = compile ]
then
    ant
fi

if [ $1 = debug ]
then
    ant
    java -jar dist/MAC0242-Project.jar -v | less 
fi

if [ $2 = calma ]
then
    echo Estou calmo!
else
    java -jar dist/MAC0242-Project.jar
fi
