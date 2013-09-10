#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pragmas
use strict;
use warnings;

# Função que avalia se um escalar e um número, ou não.
use Scalar::Util qw(looks_like_number); 

# Pacote com implementações das funções de assembly.
use functions; 

sub ctrl
{
    ## VARIÁVEIS ######################################################
    my $obj = shift;             # Carrega objeto
    
    my $i    = \$obj->{'PC'};    # Alias para o registrador
    my $lbl  =  $obj->{'LABEL'}; # Alias para os lABELs
    my $prog =  $obj->{'PROG'};  # Alias para o vetor de programas
    
    my $f = 0;                   # Escalar para o nome do comando
    my $arg = 0;                 # Escalar para o argumento do comando
    my $stack = 0;               # Controle da pilha
    
    ## CARREGA LABELS #################################################
    
    $$i = 0; # Zera contador de linhas
    for my $code (@$prog) 
    {
        # Carrega labels e transforma linhas 
        # só de labels em linhas com a string "0"
        $code->[0] = "0" unless defined($code->[0]);
        $$lbl{$code->[2]} = $$i if defined($code->[2]); $$i++;
    }
    
    ## EXECUTA CÓDIGOS ################################################
    $f = $$prog[0][0]; # Carrega primeiro comando
    $$i = 0; 
    
    while($f ne 'END') # Incrementa PC
    {
        $arg = $$prog[$$i][1];       # Carrega argumento
        my $line = $$i+1; $$i++;     # Incrementa PC
        
        if($f ne "0")
        { $stack = $obj->$f($arg, $stack); } # Chama função
        
        # Tratamento de erros com o retorno da função
        given($stack) 
        {        
            when(-1) 
            { die "Falha de segmentação, chefe! Linha $line\n";       }
            
            when(-2) 
            { die "Operacao invalida, chefe! Linha $line\n";          }
            
            when(-3) 
            { die "Não há label com este nome, chefe! Linha $line\n"; }
            
            default
            { $f = $$prog[$$i][0]; } # Carrega próximo comando
        }
    } # while 
}

return 1;
