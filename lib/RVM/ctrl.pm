#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;
use funcoes;

# Pragmas
use strict;
use warnings;
use Scalar::Util qw(looks_like_number);

sub monteste
{
	my $obj = shift;
	my $prog = [];
	my $i = 0;
	my $j = 0;
	
	do {
		my $txt = <>;
		$$prog[$i] = [$2,$3,$1] if ($txt =~ m/(\w+:)?\s*(\w+)\s*(\d+|\w+)?/);
		$i++;
		$j = $2;
	} while($j ne 'END');
	
	$obj->{'PROG'} = $prog;
}

sub ctrl
{
	my $obj = shift;
	my $prog = $obj->{'PROG'};
	my $i = \$obj->{'PC'};
	my $f = 0;
  my $arg = 0;
	my $lbl = $obj->{'LABEL'};
	my $stack = 0;
	my $check = $$prog[$$i][0];
	$$i = 0;
	$check = 0 unless defined($$prog[$$i][0]);
	while($check ne 'END')
	{
		$f = $$prog[$$i][0];
		$arg = $$prog[$$i][1];
		$$lbl{$$prog[$$i][2]} = $$i if defined($$prog[$$i][2]);
		$$i++;
		if(defined($f))
		{
			$stack = $obj->$f($arg, $stack);
		}
		
		if($stack == -1)
		{
			print "Falha de segmentação, chefe! Linha $$i\n";
			last;
		}
		
		if($stack == -2)
		{
			print "Operacao invalida, chefe! Linha $$i\n";
			last;
		}
		
		if($stack == -3)
		{
			print "Não há label com este nome, chefe! Linha $$i\n";
			last;
		}
		$check = $$prog[$$i][0];
		$check = 0 unless defined($$prog[$$i][0]);
	}

}

return 1;
