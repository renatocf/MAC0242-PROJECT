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
	return -1 if $stack < 2;
	my $data = $obj->{"DATA"};
	my $a = pop @$data;
	my $b = pop @$data;
	return -2 unless looks_like_number($a) and looks_like_number($b); 
	push @$data, $a + $b;
	return $stack - 1;
}

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
	push @$data, $b - $a;
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
	push @$data, $a * $b;
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
	push @$data, $b / $a;
	return $stack - 1;
}

return 1;
