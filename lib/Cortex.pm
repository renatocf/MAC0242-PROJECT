#!/usr/bin/perl
package Cortex;
use v5.14;

# Pragmas
use strict;
use warnings;


 my @pilha;
 my @instrucoes1 = ('POP', 'DUP', 'ADD', 'SUB', 'MUL', 'DIV', 'EQ', 'GT', 'GE', 'LT', 'LE', 'NE', 'END', 'PRN'); # sem argumento
 my @instrucoes2 = ('PUSH', 'STO', 'RCL'); # argumento numérico
 my @instrucoes3 = ('JMP', 'JIT', 'JIF'); # argumento string

# boolenas
 my $true;
 my $true1;
 my $true2;

 my $label;
 my $comando;
 my $argumento;

 my $aux = 0;
 my $linha = 0;

sub retorno
{
	# abrindo arquivo
	open(FILE, '<', "teste.txt") or die('Falha ao abrir o ARQUIVO!');

	foreach (<FILE>)
	{ 
		$linha ++;
		when( /^\s*([#].*)?$/){ }         # linha exclusiva de comentário
		when( /^\s*$/){ }                 # linha em branco
		when( /^\s*(?:(\w*):)?\s*(?:([A-Z]+)\s+(\d+||\w+))?\s*([#].*)?$/) 
		{ 
			$label = $1;
			$comando = $2;
			$argumento = $3;

		       	if(defined $comando)
			{
				# caso algum comando seja passado:
				$true1 = 1;
				for( @instrucoes1)
				{
					# verificando se o comando é do tipo que não precisa de argumento
					if( $_ eq $comando)
					{
						$true1 = 0;
					}
				}
				if($argumento ne "" && $true1 == 0)
				{
					# caso haja um comando que não precisa de argumento, com argumento
					printf(" Erro na linha $linha!    O comando '$comando' NÃO precisa de argumento! \n");
					$aux = 1;
				}
		      
				$true = 1;
				for( @instrucoes2)
				{
					# verificando se o comando é do tipo que precisa de argumento numérico
					if( $_ eq $comando)
					{
						$true = 0;
					}
				}
				if($argumento !~ m/\d+/ && $true == 0)
				{
					# caso haja um comando que precise de argumento numérico e este não seja passado
					printf(" Erro na linha $linha!    O comando '$comando' precisa de argumento NUMÉRICO! \n");
					$aux = 1;
				}

				$true2 = 1;
				for( @instrucoes3)
				{
					# verificando se o comando é do tipo que precisa de argumento String
					if( $_ eq $comando)
					{
						$true2 = 0;
					}
				}
			      	if($argumento !~ m/\w+/ && $true2 == 0)
			      	{
					# caso haja um comando que precise de argumento String e este não seja passado
					print(" Erro na linha $linha!    O comando '$comando' precisa de argumento em forma de PALAVRA (um label)! \n");
				 	$aux = 1;
			      	}

			      	if($true == 1 && $true2 == 1 && $true1 == 1)
			      	{
					# caso o comando não pertença a nenhum tipo de comando existente
				 	print(" Erro na linha $linha!    O comando '$comando' NÃO existe! \n");
				 	$aux = 1;
			      	}
			      	elsif (($argumento =~ m/\d+/ && $true == 0) || 
				      ($argumento =~ m/\w+/ && $true2 == 0) || 
				      ($argumento eq "" && $true1 == 0) )
			      	{
					# caso o comando exista e seja compatível com o seu tipo de argumento
				 	push(@pilha, [$comando, $argumento, $label]);
			      	}
			}
			else
			{
				# caso nenhum comando seja passado - apenas label
				push(@pilha, [$comando, $argumento, $label]);
			}
		}
		default 
		{
			# caso a sintaxe não seja do formato necessário
			print(" Erro na linha $linha! \n");
			$aux = 1;
		}
	}
	close(FILE);

	if($aux == 1)
	{
		# caso haja erro de sintaxe
		undef;
	}
	else
	{
		# caso a sintaxe esteja da forma correta
		@pilha;
	}
}



if(defined retorno)
{   print("CERTINHO! ^^ \n");   }

print("\n");
