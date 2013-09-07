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
		when( /^\s*(?:([A-Za-z]+\w*):)?\s*(?:([A-Z]+)\s+(\w*))?\s*([#].*)?$/) 
		{ 
			$label = $1;
			$comando = $2;
			$argumento = $3;

		       	if(defined $comando)
			{
				# caso algum comando seja passado:
				given($comando)
				{					
					when( @instrucoes1)
					{
						if($argumento ne "")
						{
							# caso haja um comando que não precisa de argumento, com argumento
							printf(" Erro na linha $linha!    O comando '$comando' NÃO precisa de argumento! \n");
							$aux = 1;
						}
						else { push(@pilha, [uc $comando, $argumento, $label]); }
					}
					when( @instrucoes2)
					{
						if($argumento !~ m/^\d+$/)
						{
							# caso haja um comando que precise de argumento numérico e este não seja passado
							printf(" Erro na linha $linha!    O comando '$comando' precisa de argumento NUMÉRICO! \n");
							$aux = 1;
						}
						else { push(@pilha, [uc $comando, $argumento, $label]); }
					}
					when( @instrucoes3)
					{
						if($argumento !~ m/^[A-Za-z]+\w*$/)
				      		{
							# caso haja um comando que precise de argumento String e este não seja passado
							print(" Erro na linha $linha!    O comando '$comando' precisa de argumento em forma de PALAVRA (um label)! \n");
						 	$aux = 1;
				      		}
						else { push(@pilha, [uc $comando, $argumento, $label]); }
					}
				      	

				      	default
				      	{
						# caso o comando não pertença a nenhum tipo de comando existente
					 	print(" Erro na linha $linha!    O comando '$comando' NÃO existe! \n");
					 	$aux = 1;
				      	}
				}
			}
			else
			{
				# caso nenhum comando seja passado - apenas label
				push(@pilha, [uc $comando, $argumento, $label]);
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
