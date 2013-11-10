#!/usr/bin/perl
package main;
use v5.14;

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

# Adicionando módulos no @INC
use FindBin qw($Bin);
use lib "$Bin/../lib";
use lib "$Bin/../lib/RVM";

# Pragmas
use strict;
use warnings;

# Pacotes
use Cortex;
use RVM::RVM;

# Opções
use Getopt::Long;
my $help = undef; 
my $show_robot = undef;
GetOptions("help" => \$help, 
           "show" => \$show_robot
);

# Uso
if(scalar @ARGV != 1) {
    die "USAGE: perl robot.pl program.txt\n";
}

#######################################################################
#                                TESTS                                #
#######################################################################

my $file = shift;

# Criando parser e programa
my $positronic_brain = new Cortex;
my @prog = $positronic_brain->parse("$file");

# Criando robô e carregando programa
my $robot = new RVM;
$robot->upload(\@prog);

# Imprime para ver se carregou certo
if($show_robot) { say "$robot"; } 

# Executa os comandos
$robot->ctrl();
