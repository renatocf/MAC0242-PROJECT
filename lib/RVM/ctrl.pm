#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;
use functions; #Pacote com implementações das funções de assembly.

# Pragmas
use strict;
use warnings;
use Scalar::Util qw(looks_like_number); #funcao que avalia se um escalar e um numero, ou nao.

sub ctrl
{
        
    # Variáveis
    
    my $obj = shift;
    my $prog = $obj->{'PROG'}; 
    my $i = \$obj->{'PC'};
    my $f = 0;                                  #escalar que guarda o nome do comando
  my $arg = 0;                              #escalar que guarda o argumento do comando
    my $lbl = $obj->{'LABEL'};
    my $stack = 0;                          #controle da pilha
    
    # Preparação para o loop
    
    $f = $$prog[$$i][0];                   #carrega comando
    $$i = 0;                               #inicia PC
    $f = "0" unless defined($$prog[$$i][0]); #para evitar uma comparacao invalida no while 
    
    # Loop
    
    while($f ne 'END')
    {
        $arg = $$prog[$$i][1];                                                                  #carrega argumento
        $$lbl{$$prog[$$i][2]} = $$i if defined($$prog[$$i][2]); #carrega label
        $$i++;                                                                                          #incrementa PC
        if($f ne "0")
        {
            $stack = $obj->$f($arg, $stack);   #Chama funcao
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
        $f = $$prog[$$i][0]; #carrega próximo comando
        $f = "0" unless defined($$prog[$$i][0]);
    }

}

return 1;
