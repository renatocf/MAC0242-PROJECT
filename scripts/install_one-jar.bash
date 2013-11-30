#!/bin/bash
# install_OneJar.bash

########################################################################
# Copyright 2013 KRV                                                   #
#                                                                      #
# Licensed under the Apache License, Version 2.0 (the "License");      #
# you may not use this file except in compliance with the License.     #
# You may obtain a copy of the License at                              #
#                                                                      #
#  http://www.apache.org/licenses/LICENSE-2.0                          #
#                                                                      #
# Unless required by applicable law or agreed to in writing, software  #
# distributed under the License is distributed on an "AS IS" BASIS,    #
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      #
# implied.                                                             #
# See the License for the specific language governing permissions and  #
# limitations under the License.                                       #
########################################################################

RES="\033[0m"       
BPURPLE="\e[1;35m"
BYELLOW="\e[1;33m"
BGREEN="\e[1;32m"
BBLUE="\e[1;34m"
BRED="\e[1;31m"

#######################################################################
##                      INSTALLING PACKAGES                          ##
#######################################################################

echo -e "${BGREEN}Dowloading One-JAR™${RES}"
wget https://sourceforge.net/projects/one-jar/files/one-jar/one-jar-0.97/one-jar-ant-task-0.97.jar/download \
    -O one-jar-ant-task-0.97.jar 

echo -e "${BGREEN}Creating file directory${RES}"
mkdir -p one-jar
mv ./one-jar-ant-task-0.97.jar one-jar/

echo -e "${BGREEN}Extracting files${RES}"
cd one-jar
jar -xvf one-jar-ant-task-0.97.jar
cd ..
