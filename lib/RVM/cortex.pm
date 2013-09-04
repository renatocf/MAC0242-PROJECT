#!/usr/bin/perl
package cortex;
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
 my $a;
 my $b;
 my $c;
 my $aux = 0;

sub retorno
{


open(FILE, '<', "teste.txt") or die('Falha ao abrir o ARQUIVO!');


while (<FILE>)
{
   if( s/^[ ]*[\t]*([#].*)?$//)
   {
print("-------------------------------\n");
   }
   elsif( s/^[\s\t]*$//){}
   elsif( s/^[ ]*[\t]*(?:([A-Za-z]*):)?[ ]*[\t]*(?:([A-Z]+)(?:[ ]+||[\t]+)([0-9]+||[A-Za-z]+))?[ ]*[\t]*([#].*)?$//)
   { 
      $a  = $1;
      $b = $2;
      $c = $3;     
print("$a, $b, $c");
      $true1 = 1;
      for( @instrucoes1)
      {
         if( $_ eq $b)
         {
            $true1 = 0;
         }
      }
      if($c ne "" && $true1 == 0)
      {
         printf(" Saiiiiii daquiiii \n");
         $aux = 1;
      }

      
      $true = 1;
      for( @instrucoes2)
      {
         if( $_ eq $b)
         {
            $true = 0;
         }
      }
      if($c !~ m/[0-9]+/ && $true == 0)
      {
         printf(" Saiiiiii \n");
         $aux = 1;
      }

      $true2 = 1;
      for( @instrucoes3)
      {
         if( $_ eq $b)
         {
            $true2 = 0;
         }
      }
      if($c !~ m/[A-Za-z]+/ && $true2 == 0)
      {
         print("Eroooooo \n");
         $aux = 1;
      }

      if($true == 1 && $true2 == 1 && $true1 == 1)
      {
         print("Errandooooooo $a $b $c   \n");
         $aux = 1;
      }
      elsif(($c =~ m/[0-9]+/ && $true == 0) || ($c =~ m/[A-Za-z]+/ && $true2 == 0) || ($c eq "" && $true1 == 0) )
      {
print("***************\n");
         push(@pilha, [$b, $c, $a]);
      }
   }
   elsif( s/^[ ]*[\t]*([A-Za-z]*):[ ]*[\t]*$//) 
   {
      print("Heey\n");
      push(@pilha, ['','',$1]);
   }
   else 
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
print("---------$lala[1][1]\n");