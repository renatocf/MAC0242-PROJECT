#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;


sub STO
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	return -1 if $stack < 1;
	my $data = $obj->{"DATA"};
	my $ram = $obj->{"RAM"};
	@$ram[$arg] = pop @$data;
	return $stack - 1;
}

sub RCL
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	my $data = $obj->{"DATA"};
	my $ram = $obj->{"RAM"};
	return -1 unless defined($$ram[$arg]);
  push @$data, $$ram[$arg];
	return $stack + 1;
}

return 1;
