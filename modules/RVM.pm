#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

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
        __PACKAGE__ . "::PROG"  => [], # (P) Robot's program
        __PACKAGE__ . "::DATA"  => [], # (P) Main data stack 
        __PACKAGE__ . "::PC"    => 0,  # (R) Instruction Register
        __PACKAGE__ . "::RAM"   => [], # (R) Auxiliar data stack
        __PACKAGE__ . "::CTRL"  => [], # (R) Control of callbacks
        __PACKAGE__ . "::LABEL" => {}, # (R) Control of callbacks
    };
    
    bless($obj, $class);
    while(@_)
    {
        # Initialize the robot data
        my ($key, $value) = (shift, shift);
        $obj->$key($value) if(defined $value and defined $key->{$value});
    }
    return $obj;
}

#######################################################################
#                               METHODS                               #
#######################################################################

sub PUSH {
    my $obj = shift;
    my $data = shift;
    return $obj->("DATA", $data);
}

sub POP {
    my $obj = shift;
    my $data = $obj->("DATA");
    my $value = pop @{$data};
    return $data;
}
