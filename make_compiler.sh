#!/bin/sh


DIR=$(pwd)

cd test/javacc/
javacc Parser.jj
javac *.java
if [ $? -ne 0 ]; then exit; fi
echo "========================================"
echo "JAVACC - Parser"
java Parser

cd $DIR
