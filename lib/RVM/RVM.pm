#!/usr/bin/perl
package RVM v1.0.0;
use v5.14;

# Pacotes
use ctrl;
use Overload;

# Pragmas
use strict;
use warnings;

#######################################################################
#                           CONSTRUCTOR                               #
#######################################################################

sub new {
    my $invocant = shift;
    my $class = ref($invocant) || $invocant;
    
    # Each robot virtual machine is composed of several
    # pieces, controlling the execution workflow
    my $obj = {
        "PROG"  => [], # (P) Programa
        "DATA"  => [], # (P) Pilha principal de memória
        "PC"    => 0,  # (R) Registrador de Instrução
        "RAM"   => [], # (R) Memória auxiliar    
        "CTRL"  => [], # (R) Controle de retornos (callbacks)
        "LABEL" => {}, # (R) Hash com labels     
    };
    
    bless($obj, $class);
    while(@_)
    {
        # Inicializa os dados do robô
        my ($key, $value) = (shift, shift);
        $key = __PACKAGE__ . "::$key";
        $obj->$key($value) if(defined $value and defined $key->{$value});
    }
    return $obj;
}

#######################################################################
#                          GETTERS AND SETTERS                        #
#######################################################################

sub upload {
    my $obj = shift;
    $obj->{PROG} = shift;
} 

return 1;
