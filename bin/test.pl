#!/usr/bin/perl
package main;
use v5.14;

# Adding modules in @INC
use FindBin qw($Bin);
use lib "$Bin/../lib/";
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
my @prog = $brain->gen_program("test.txt");

$/ = "\n"; my $i = 1;
print_prog(\@prog);

sub print_prog {
    my $aref = shift; my $i = 0;
    for my $line (@$aref) {
        print "$i: ";
        foreach my $token (@$line) {
            print "$token " if defined $token; 
        }
        print "\n"; $i++;
    }
}

say "\nRVM ", '-' x 20, "\n";
my $obj = new RVM;

$obj->{PROG} = \@prog;

say "$obj";
