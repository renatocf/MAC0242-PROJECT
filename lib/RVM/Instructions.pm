#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

#######################################################################
#                            INSTRUCTIONS                             #
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

1;
