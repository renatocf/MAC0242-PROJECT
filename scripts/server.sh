#!/bin/sh
# server.sh

DIR=$(pwd)
cd build/classes

java arena.WorldServer
# java arena.WorldClient localhost

cd $DIR
