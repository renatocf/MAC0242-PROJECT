#!/usr/bin/perl
package Cortex;
use v5.14;

# Pragmas
use strict;
use warnings;


 my @pilha;
 my @instrucoes1 = ('POP', 'DUP', 'ADD', 'SUB', 'MUL', 'DIV', 'EQ', 'GT', 'GE', 'LT', 'LE', 'NE', 'END', 'PRN');
 my @instrucoes2 = ('PUSH', 'STO', 'RCL');
 my @instrucoes3 = ('JMP', 'JIT', 'JIF');

 my $true;
 my $true2;
 my $true1;

 my $label;
 my $comando;
 my $argumento;
 my $aux = 0;

sub retorno
{
	open(FILE, '<', "teste.txt") or die('Falha ao abrir o ARQUIVO!');

	foreach (<FILE>)
	{
		when( /^[\s]*([#].*)?$/){}
		when( /^[\s]*$/){}
		when( /^[\s]*(?:([\w]*):)?[\s]*(?:([A-Z]+)[\s]+([\d]+||[\w]+))?[\s]*([#].*)?$/)
		{ 
			$label = $1;
			$comando = $2;
			$argumento = $3;

		       	if(defined $comando)
			{
				$true1 = 1;
				for( @instrucoes1)
				{
					if( $_ eq $comando)
					{
						$true1 = 0;
					}
				}
				if($argumento ne "" && $true1 == 0)
				{
					printf(" Saiiiiii daquiiii \n");
					$aux = 1;
				}
		      
				$true = 1;
				for( @instrucoes2)
				{
					if( $_ eq $comando)
					{
						$true = 0;
					}
				}
				if($argumento !~ m/[\d]+/ && $true == 0)
				{
					printf(" Saiiiiii \n");
					$aux = 1;
				}

				$true2 = 1;
				for( @instrucoes3)
				{
					if( $_ eq $comando)
					{
						$true2 = 0;
					}
				}
			      	if($argumento !~ m/[\w]+/ && $true2 == 0)
			      	{
					print("Eroooooo \n");
				 	$aux = 1;
			      	}

			      	if($true == 1 && $true2 == 1 && $true1 == 1)
			      	{
				 	print("Errandooooooo  \n");
				 	$aux = 1;
			      	}
			      	elsif (($argumento =~ m/[\d]+/ && $true == 0) || 
				      ($argumento =~ m/[\w]+/ && $true2 == 0) || 
				      ($argumento eq "" && $true1 == 0) )
			      	{
				 	push(@pilha, [$comando, $argumento, $label]);
			      	}
			}
			else
			{
			  push(@pilha, [$comando, $argumento, $label]);
			}
		}
		when( /^[ ]*[\t]*([\w]*):[ ]*[\t]*$/) 
		{
			print("Heey\n");
			push(@pilha, ['','',$1]);
		}
		default 
		{
			print("Código $_ inválido!\n");
			$aux = 1;
		}
	}
	close(FILE);

	if($aux == 1)
	{
		undef;
	}
	else
	{
		@pilha;
	}
}




my @lala = &retorno;

