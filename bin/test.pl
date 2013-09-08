#!/usr/bin/perl
package main;
use v5.14;

# Adding modules in @INC
use FindBin qw($Bin);
use lib "$Bin/../lib";
use lib "$Bin/../lib/RVM";

# Pragmas
use strict;
use warnings;

# Packages
use Cortex;
use RVM::RVM;

#######################################################################
#                                TESTS                                #
#######################################################################

my $brain = new Cortex;
my @prog = $brain->parser("$Bin/test.txt");

my $robot = new RVM;
$robot->{PROG} = \@prog;

say "$robot";   # Imprime para ver se carregou certo
$robot->ctrl();
