#!/usr/bin/perl
package Cortex;
use v5.14;

# Pragmas
use strict;
use warnings;

#######################################################################
#                        VARIÁVEIS DE PACOTE                          #
#######################################################################

my @ins1 = ('ADD', 'DIV', 'DUP', 'END', 'EQ', 'GE', 'GT', 
            'LE', 'LT', 'MUL', 'NE', 'POP', 'PRN', 'SUB'); # sem arg
my @ins2 = ('PUSH', 'RCL', 'STO'); # arg numérico
my @ins3 = ('JIF', 'JIT', 'JMP'); # arg string

#######################################################################
#                            CONSTRUTOR                               #
#######################################################################

sub new 
{
    my $invocant = shift;
    my $class = ref($invocant) || $invocant;
    return bless {}, $class;
}

#######################################################################
#                              MÉTODOS                                #
#######################################################################

sub parser
{
    my ($obj, $file) = @_;        # Objeto e arquivo
    my (@stack, $line) = ((), 0); # stack e contador de linhas
    
    # Abrindo arquivo
    open(FILE, '<', $file)
    or die("Falha ao abrir o arquivo $file!");

    foreach(<FILE>)
    { 
        chomp; $line++;
        when(/^\s*#/)                  {} # Linha de comentário
        when(/^\s*$/)                  {} # Linha em branco
        when(/^\s*([A-Za-z]\w*):\s*$/) { push @stack, [undef,undef,$1] }
        when(/^\s*             # Espaços
                (?:            
                    ([A-Za-z]  # LABEL: Começa com ao menos uma letra
                    \w*):      # e pode terminar com dígitos|letras e :
                )?             
                \s*            # Espaços
                (?:            
                    ([A-Z]+)   # COMANDO
                    \s+        # Espaço
                    (\w+)      # ARGUMENTO
                )?             
                \s*            # Espaço
                (?:\#.*)?      # Comentário
            $/x)
        { 
            my ($lab, $com, $arg, $err) = ($1, $2, $3, 0);

            # caso algum com seja passado:
            given($com)
            {                   
                when(@ins1) { $err = 1 if($arg ne "");                }
                when(@ins2) { $err = 2 if($arg !~ m/^\d+$/);          }
                when(@ins3) { $err = 3 if($arg !~ m/^[A-Za-z]+\w*$/); }
                default     { $err = 4;                               }
            }
            
            if($err) { &err($line, $err, $com); return undef; } 
            else     { push @stack, [uc $com, $arg, $lab];     }
        }
        
        # Erro de sintaxe
        default { &err($line); return undef; } 
    }
    close(FILE);
    
    # Sem erros, retorne a stack
    return @stack;
}

#######################################################################
#                           MÉTODOS AUXILIARES                        #
#######################################################################

sub err 
{
    my ($line, $err, $com) = @_;
    
    select STDERR;
    print "Erro na linha $line! ";
    
    # Erro de sintaxe simples
    die "Sintaxe: <LABEL:> COMANDO <ARGUMENTO>\n" unless defined $com;
    
    # Problema com comandos
    print "Comando '$com' ";
    given($err) 
    {
        when(1) { say("NÃO precisa de argumento!")          }
        when(2) { say("precisa de argumento NUMÉRICO!")     }
        when(3) { say("precisa de uma PALAVRA (um LABEL)!") }
        when(4) { say("NÃO existe!")                        }
    }
} 

__END__

if(defined retorno)
{   print("CERTINHO! ^^ \n");   }

print("\n");

when(/^\s*(?:([A-Za-z]+\w*):)?\s*(?:([A-Z]+)\s+(\w*))?\s*([#].*)?$/) 
