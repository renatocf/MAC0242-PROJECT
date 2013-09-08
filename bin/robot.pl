#!/usr/bin/perl
package main;
use v5.14;

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
