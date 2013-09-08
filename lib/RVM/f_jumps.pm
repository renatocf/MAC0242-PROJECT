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
	$$i += $arg;     #Acrescenta o argumento no PC
	
	#Devolve Falha de segmentacao, caso pule para um indice que nao existe
	$$i -= $arg and return -1 unless defined($$prog[$$i]); 
	
	return $stack;	
}

sub JIT
{
	my $obj = shift;
	my $arg = shift;
	my $stack = shift;
	my $i = \$obj->{'PC'};
	my $j; #Variavel que lembra o PC se houver erro
	my $data = $obj->{"DATA"};
	my $test = pop @$data; #Variavel que sera testada
	my $lbl = $obj->{'LABEL'}; 
	my $prog = $obj->{'PROG'};
	
	return -1 if $stack < 1; #Devolve Falha de segmentacao caso a pilha esteja vazia
	return $stack - 1 unless $test; #Não faz nada se o teste for falso
	
	#Caso o usuario informe a linha numericamente
	
	if(looks_like_number($arg)) 
	{
		$j = $$i;
		$$i = $arg;
		#Devolve falha de segmentação caso pule para uma linha inexistente 
		$$i = $j and return -1 unless defined($$prog[$$i]);  
	}
	
	#Caso o usuário informe um label
	
	else 
	{
		if(defined($$lbl{$arg}))
		{
			$$i = $$lbl{$arg};
		}
		else
		{
			return -3; #Se não houver o label informado ele devolve um erro
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
	my $j; #Variavel que lembra o PC se houver erro
	my $data = $obj->{"DATA"};
	my $test = pop @$data; #Variavel que sera testada
	my $lbl = $obj->{'LABEL'};
	my $prog = $obj->{'PROG'};
	
	return -1 if $stack < 1; #Devolve Falha de segmentacao caso a pilha esteja vazia
	return $stack - 1 if $test; #Não faz nada se o teste for verdadeiro
	
	#Caso o usuario informe a linha numericamente
	
	if(looks_like_number($arg)) 
	{
		$j = $$i;
		$$i = $arg;
		#Devolve falha de segmentacao caso pule para uma linha inexistente
		$$i = $j and return -1 unless defined($$prog[$$i]);
	}
	
	#Caso o usuário informe um label
	
	else 
	{
		if(defined($$lbl{$arg}))
		{
			$$i = $$lbl{$arg};
		}
		else
		{
			return -3;   #Se não houver o label informado ele devolve um erro
		}
	}
	return $stack - 1;
}

return 1
