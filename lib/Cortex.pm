#!/usr/bin/perl
package Cortex;
use v5.10;

########################################################################
# Copyright 2013 KRV                                                   #
#                                                                      #
# Licensed under the Apache License, Version 2.0 (the "License");      #
# you may not use this file except in compliance with the License.     #
# You may obtain a copy of the License at                              #
#                                                                      #
#  http://www.apache.org/licenses/LICENSE-2.0                          #
#                                                                      #
# Unless required by applicable law or agreed to in writing, software  #
# distributed under the License is distributed on an "AS IS" BASIS,    #
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      #
# implied.                                                             #
# See the License for the specific language governing permissions and  #
# limitations under the License.                                       #
########################################################################

# Pragmas
use strict;
use warnings;

#######################################################################
#                        PACKAGE VARIABLES                            #
#######################################################################

my @ins1 = ('ADD' , 'DIV' , 'DUP' , 'END' , 'EQ'  , 'GE'  , 'GT'  , 
            'MOD' , 'LE'  , 'LT'  , 'MUL' , 'NE'  , 'POP' , 'PRN' , 
            'SUB' , 'RET' , 'MOVE', 'DRAG', 'DROP', 'HIT' , 'LOOK', 
            'ITEM', 'SEE' , 'SEEK', 'ASK' , 'NOP' , 'READ', 'WRT' ,
            'SWAP'); # arg: none
my @ins2 = ('RCL' , 'STO');                  # arg: numeric  (only)
my @ins3 = ('JMP' , 'JIF' , 'JIT' , 'CALL'); # arg: address/string
my @ins4 = ('ALOC', 'FREE', 'GET' , 'SET' ); # arg: var name (only)

my @stk  = ('crystal', 'stone');             # stackables
my @atk  = ('ranged',  'melee');             # attacks

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
        when(/^\s*                   # Spaces
                (?:                
                    ([A-Za-z]        # LABEL: Starts with at least
                    \w*):            # one character, others 0 or
                                     # more alphanumerics and :
                )?                 
                \s*                  # Spaces
                (?:                
                    ([A-Z]+)         # COMANDO
                    (?:
                        \s+          # Spaces
                        (
                            [+-]?\d+ # NUMBER    }
                            |        #           }
                            &\d+     # ADDRESS   }
                            |        #           }
                            \[\w+\]  # VARIABLE  }
                            |        #           }
                            \(x\)\w+ # ATTACK    } ARGUMENT
                            |        #           }
                            ->\w*    # DIRECTION }
                            |        #           }
                            "\w+"    # STRING    } 
                            |        #           }
                            {\w+}    # STACKABLE }
                            |        #           }
                            \w+      # LABEL     } 
                        )
                    )?
                )?             
                \s*                  # Spaces
                (?:\#.*)?            # Comemnts
            $/x)
        { 
            my ($lab, $com, $arg, $err) = ($1, $2, $3, 0);
            
            # If there is any command
            if($com eq "PUSH")
            {
                if    ($arg =~ m/^[+-]?\d+$/)           {} # Numeric
                elsif ($arg =~ m/^(?:->[NS]?[WE]|->)$/) {} # Direction
                elsif ($arg =~ m/^"(\w+)"$/) { $arg = $1 } # String
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
                else  { $err = 0; }                        # Default
                
            }
            else                                     
            {
                given($com)
                {                   
                    when(@ins1) { $err = 2 if defined $arg                }
                    when(@ins2) { $err = 3 if $arg !~ m/^[+-]?\d+$/       }
                    when(@ins3) { $err = 4 if $arg !~ m/(&)?\d+|\w+$/;
                                  $arg = "ADDRESS($arg)" if defined $1    }
                    when(@ins4) { $err = 5 if $arg !~ m/^\[\w+\]$/        }
                    default     { $err = 6                                }
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
        when(1) { say("needs a STACKABLE argument!")        }
        when(2) { say("needs NO argument!")                 }
        when(3) { say("needs a NUMERIC argument!")          }
        when(4) { say("needs a LABEL or ADDRESS argument!") }
        when(5) { say("needs a STRING argument")            }
        when(6) { say("does not exist!")                    }
    }
} 

__END__

if(defined retorno)
{   print("CERTINHO! ^^ \n");   }

print("\n");

when(/^\s*(?:([A-Za-z]+\w*):)?\s*(?:([A-Z]+)\s+(\w*))?\s*([#].*)?$/) 
