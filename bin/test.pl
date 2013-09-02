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
use RVM::RVM;

#######################################################################
#                                TESTS                                #
#######################################################################

my $obj = new RVM;
say "$obj";
