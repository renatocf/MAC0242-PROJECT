#!/usr/bin/perl
package Cortex;
use v5.10;

# Pragmas
use strict;
use warnings;

#######################################################################
#                        PACKAGE VARIABLES                            #
#######################################################################

my @ins1 = ('ADD', 'DIV', 'DUP', 'END', 'EQ', 'GE', 'GT', 'MOD',  # no 
            'LE', 'LT', 'MUL', 'NE', 'POP', 'PRN', 'SUB', 'RET',  # arg
            'MOVE', 'DRAG', 'DROP', 'HIT', 'LOOK', 'ITEM','SEE',
            'SEEK');  
my @ins2 = ('RCL', 'STO');                   # arg: numeric (only)
my @ins3 = ('JMP', 'JIF', 'JIT', 'CALL');    # arg: numeric/string
my @stk  = ('crystal', 'stone');             # stackables
my @atk  = ('ranged',  'melee'); 

#######################################################################
#                            CONSTRUCTOR                              #
#######################################################################

sub new 
{
    my $invocant = shift;
    my $class = ref($invocant) || $invocant;
    return bless {}, $class;
}

#######################################################################
#                              METHODS                                #
#######################################################################

sub parse
{
    my ($obj, $file) = @_;   # Object and file
    my @stack; my $line = 0; # Stack and line counter
    
    # Opening file
    open(FILE, '<', $file)
    or die("Failed opening $file!");

    foreach(<FILE>)
    { 
        chomp; $line++;
        when(/^\s*#/)                  {} # Comment line
        when(/^\s*$/)                  {} # Blank line
        when(/^\s*([A-Za-z]\w*):\s*$/) { push @stack, [undef,undef,$1] }
        when(/^\s*                       # Spaces
                (?:                
                    ([A-Za-z]            # LABEL: Starts with at least
                    \w*):                # one character, others 0 or
                                         # more alphanumerics and :
                )?                 
                \s*                      # Spaces
                (?:                
                    ([A-Z]+)             # COMANDO
                    (?:
                        \s+              # Spaces
                        (
                            \(x\)\w+     # ATTACK    }
                            |            #           }
                            ->\w*        # DIRECTION } ARGUMENT
                            |            #           }
                            \w+          # STRING    } 
                            |            #           }
                            {\w+}        # STACKABLE }
                        )
                    )?
                )?             
                \s*                      # Spaces
                (?:\#.*)?                # Comemnts
            $/x)
        { 
            my ($lab, $com, $arg, $err) = ($1, $2, $3, 0);
            
            # If there is any command
            if($com eq "PUSH")
            {
                if    ($arg =~ m/^\d+$/)                {} # Numeric
                elsif ($arg =~ m/^[A-Za-z]+\w*$/)       {} # String
                elsif ($arg =~ m/^(?:->[NS]?[WE]|->)$/) {} # Direction
                elsif ($arg =~ m/^{(\w+)}$/)               # Stackable
                {
                    $err = 1; # If matches, return
                              # to the default value
                    # map { $err = -1 if $_ eq $1 } @stk; 
                    foreach (@stk) { $err = 0 if $_ eq lc $1 }
                }                                        
                elsif ($arg =~ m/^\(x\)(\w+)$/)            # Attack
                {
                    $err = 1; # If matches, return
                              # to the default value
                    # map { $err = -1 if $_ eq $1 } @stk; 
                    foreach (@atk) { $err = 0 if $_ eq lc $1 }
                }                                          
                else  { $err = 0; }                       # Default
                
            }
            else                                     
            {
                given($com)
                {                   
                    when(@ins1) { $err = 2 if(defined $arg);              }
                    when(@ins2) { $err = 3 if($arg !~ m/^\d+$/);          }
                    when(@ins3) { $err = 4 if($arg !~
                                              m/^(?:[A-Za-z]+\w*|\d+)$/); }
                    default     { $err = 5;                               }
                }
            }
            
            if($err) { &err($line, $err, $com); return undef; } 
            else     { push @stack, [uc $com, $arg, $lab];     }
        }
        
        # Syntax error
        default { &err($line, $_); return undef; } 
    }
    close(FILE);
    
    # No errors. Return stack
    return @stack;
}

#######################################################################
#                           MÉTODOS AUXILIARES                        #
#######################################################################

sub err 
{
    my ($line, $err, $com) = @_;
    
    select STDERR;
    print "Error on line $line! Error $err ";
    
    # Simple syntax error
    die "Syntax: <LABEL:> COMMAND <ARGUMENT>\n" unless defined $com;
    
    # Problems with commands
    print "Command '$com' ";
    given($err) 
    {
        when(1) { say("needs a STACKABLE argument!") }
        when(2) { say("needs NO argument!")          }
        when(3) { say("needs a NUMERIC argument!")   }
        when(4) { say("needs a LABEL argument!")     }
        when(5) { say("does not exist!")             }
    }
} 

__END__

if(defined retorno)
{   print("CERTINHO! ^^ \n");   }

print("\n");

when(/^\s*(?:([A-Za-z]+\w*):)?\s*(?:([A-Z]+)\s+(\w*))?\s*([#].*)?$/) 
