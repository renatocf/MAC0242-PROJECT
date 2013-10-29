#!/bin/bash
# install_ivy.bash

RES="\033[0m"       
BPURPLE="\e[1;35m"
BYELLOW="\e[1;33m"
BGREEN="\e[1;32m"
BBLUE="\e[1;34m"
BRED="\e[1;31m"

#######################################################################
##                      INSTALLING PACKAGES                          ##
#######################################################################

USER=$(whoami)
if [ $USER == "root" ];
then
    ## SYSTEM INSTALL #################################################
    echo -e "${BGREEN}Dowloading ivy${RES}"
    apt-get install ivy
    echo -e "${BGREEN}Creating symbolic link${RES}"
    ln -s -T /usr/share/java/ivy.jar /usr/share/ant/lib/ivy.jar
else
    ## LOCAL INSTALL ##################################################
    LOCAL=$(pwd)
    cd # Go to home dir
    
    if [ -f "build.xml" ]; then
        echo -e "${BRED}build.xml already exists on $HOME${RES}"
        echo -e "${BRED}Please, remove it before proceeding.${RES}"
        cd $LOCAL
        exit
    fi
    
    echo -e "${BGREEN}Dowloading ivy${RES}"
    wget http://ant.apache.org/ivy/history/latest-milestone/samples/build.xml
    ant
    
    echo -e "${BGREEN}Creating library directory${RES}"
    mkdir -p .ant/lib
    mv ivy/ivy.jar .ant/lib/ivy.jar
    rmdir ivy
    
    cd $LOCAL # Go back to original dir
fi
