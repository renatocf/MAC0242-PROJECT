#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

sub JMP
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	my $i = \$obj->{'PC'};
	my $prog = $obj->{'PROG'};
	$$i += $arg;
	$$i -= $arg and return -1 unless defined($$prog[$$i]);
	return $stack;	
}

sub JIT
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	my $i = \$obj->{'PC'};
	my $j = 0;
	my $data = $obj->{"DATA"};
	my $test = pop @$data;
	my $lbl = $obj->{'LABEL'};
	my $prog = $obj->{'PROG'};
	return -1 if $stack < 1;
	return $stack - 1 unless $test;
	if(looks_like_number($arg)) 
	{
		$j = $$i;
		$$i = $arg;
		$$i = $j and return -1 unless defined($$prog[$$i]);
	}
	else 
	{
		if(defined($$lbl{$arg . ":"}))
		{
			$$i = $$lbl{$arg . ":"};
		}
		else
		{
			return -3;
		}
	}
	return $stack - 1;
}

sub JIF
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	my $i = \$obj->{'PC'};
	my $j = 0;
	my $data = $obj->{"DATA"};
	my $test = pop @$data;
	my $lbl = $obj->{'LABEL'};
	my $prog = $obj->{'PROG'};
	return -1 if $stack < 1;
	return $stack - 1 if $test;
	if(looks_like_number($arg)) 
	{
		$j = $$i;
		$$i = $arg;
		$$i = $j and return -1 unless defined($$prog[$$i]);
	}
	else 
	{
		if(defined($$lbl{$arg . ":"}))
		{
			$$i = $$lbl{$arg . ":"};
		}
		else
		{
			return -3;
		}
	}
	return $stack - 1;
}

return 1
