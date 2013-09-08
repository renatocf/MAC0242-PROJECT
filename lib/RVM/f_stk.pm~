#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

sub PUSH 
{
    my $obj = shift;
    my $arg = shift;
    my $stack = shift;
    my $data = $obj->{"DATA"};
    push @{$data}, $arg;
    return $stack + 1;	
}

sub POP 
{
    my $obj = shift;
    shift;
    my $stack = shift;
    my $data = $obj->{"DATA"};
    my $value = pop @{$data};
    return $stack - 1;
}

sub DUP 
{
    my $obj = shift;
    shift;
    my $stack = shift;
    my $data = $obj->{"DATA"};
    push @{$data}, $$data[-1];
    return $stack + 1;	
}

return 1;
