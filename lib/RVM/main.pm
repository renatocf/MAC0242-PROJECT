#!/usr/bin/perl
use v5.14;
use RVM;

# Pragmas
use strict;
use warnings;

package main;

my $robo = new RVM;
$robo->monteste();
$robo->ctrl();
