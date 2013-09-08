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
	return $stack - 1 if $stack == 0;
	my $data = $obj->{"DATA"};
	my $txt = 0;
	$txt = pop @$data;
	print $txt . "\n";
	return $stack - 1;
}

return 1;
