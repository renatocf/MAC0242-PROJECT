#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

#       Sendo A o topo da pilha e B o proximo item,
#todas as comparacoes são da forma "B o A" sen-
#do 'o' pertencendo a {==, <=, <, >=, >, !=}   

#Todas as implementacoes sao analogas a EQ

sub EQ
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;  #Devolve falha de segmentacao se não houver 2+ elementos na pilha 
    my $data = $obj->{"DATA"};
    my $a = pop @$data; #topo da pilha
    my $b = pop @$data; #Proximo da pilha
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b == $a) ? 1:0); #devolve o resultado do teste
    return $stack - 1;
}

sub GT
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b > $a) ? 1:0);
    return $stack - 1;
}

sub GE
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b >= $a) ? 1:0);
    return $stack - 1;
}

sub LT
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b < $a) ? 1:0);
    return $stack - 1;
}

sub LE
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b <= $a) ? 1:0);
    return $stack - 1;
}

sub NE
{
    my $obj = shift;
    shift;
    my $stack = shift;
    return -1 if $stack < 2;
    my $data = $obj->{"DATA"};
    my $a = pop @$data;
    my $b = pop @$data;
    return -2 unless looks_like_number($a) and looks_like_number($b); 
    push @$data, (($b != $a) ? 1:0);
    return $stack - 1;
}

return 1;
