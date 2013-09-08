#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;
use ctrl;
use Overload;

# Pragmas
use strict;
use warnings;

#######################################################################
#                           CONSTRUCTOR                               #
#######################################################################

sub new {
    my $invocant = shift;
    my $class = ref($invocant) || $invocant;
    
    # Each robot virtual machine is composed of several
    # pieces, controlling the execution workflow
    my $obj = {
        "PROG"  => [], # (P) Robot's program
        "DATA"  => [], # (P) Main data stack 
        "PC"    => 0,  # (R) Instruction Register
        "RAM"   => [], # (R) Auxiliar data stack
        "CTRL"  => [], # (R) Control of callbacks
        "LABEL" => {}, # (R) Control of callbacks
    };
    
    bless($obj, $class);
    while(@_)
    {
        # Initialize the robot data
        my ($key, $value) = (shift, shift);
        $key = __PACKAGE__ . "::$key";
        $obj->$key($value) if(defined $value and defined $key->{$value});
    }
    return $obj;
}

#######################################################################
#                               METHODS                               #
#######################################################################

return 1;
