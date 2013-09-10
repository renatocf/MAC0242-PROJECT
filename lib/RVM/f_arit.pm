#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

sub ADD
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2; #Devolve falha de segmentação se não houver 2+ elementos na pilha 
    my $data = $obj->{"DATA"};  
    my $a = pop @$data; #topo da pilha
    my $b = pop @$data; #próximo da pilha
    return -2 unless looks_like_number($a) and looks_like_number($b); #Devolve operação inválida se ouver uma string
    push @$data, $a + $b;   #Soma os dois números do topo da pilha
    return $stack - 1;
}

#As funcoes SUB MUL e DIV são identicas a funcao ADD, exceto em suas penultimas linhas#

sub SUB
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, $b - $a;       #subtrai o valor no topo da pilha do próximo da pilha
    return $stack - 1;
}


sub MUL
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, $a * $b;   #multiplica os dois números do topo da pilha
    return $stack - 1;
}

sub DIV
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, $b / $a; #divide o próximo da pilha pelo valor no topo da pilha 
    return $stack - 1;
}

return 1;
