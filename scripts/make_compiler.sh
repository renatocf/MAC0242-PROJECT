#!/bin/sh
# make_compiler.sh

DIR=$(pwd)

cd build/classes/
if [ $? -ne 0 ]; then exit; fi
echo "========================================"
echo "JAVACC - Parser"
java parser.positron.Positron

cd $DIR
