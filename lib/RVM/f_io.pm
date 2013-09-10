#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

sub PRN
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return $stack - 1 if $stack == 0; #devolve falha de segmentação se a pilha estiver vazia
    my $data = $obj->{"DATA"};
    my $txt = pop @$data;  #string a ser impressa
    print $txt . "\n";
    return $stack - 1;
}

return 1;
