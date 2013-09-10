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
    push @{$data}, $arg; #Empilha o argumento
    return $stack + 1;  
}

sub POP 
{
    my $obj = shift;
    shift;
    my $stack = shift;
    my $data = $obj->{"DATA"};
    return -1 if $stack < 1; #Devolve falha de segmentacao, caso a pilha esteja vazia
    my $value = pop @{$data}; #Desempilha um numero ou string
    return $stack - 1;
}

sub DUP 
{
    my $obj = shift;
    shift;
    my $stack = shift;
    my $data = $obj->{"DATA"};
    return -1 if $stack < 1; #Devolve falha de segmentacao, caso a pilha esteja vazia
    push @{$data}, $$data[-1]; #Duplica o topo da pilha
    return $stack + 1;  
}

return 1;
