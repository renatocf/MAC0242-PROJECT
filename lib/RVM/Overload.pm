#!/usr/bin/perl
package RVM;
use v5.14;

# Pragmas
use strict;
use warnings;
use overload 
    q("") => \&as_string;

#######################################################################
#                          AUXILIAR METHODS                           #
#######################################################################

# Overload double quote
sub as_string 
{
    my $obj = shift;
    my $string = "";
    my @substring;
    
    for my $key (sort keys %{$obj})
    {
        my $value = $obj->{$key};
        $string = sprintf "%-*s => ", 5, $key;
        given(ref $value)
        {
            when("ARRAY") 
            { 
                my $i = 0;
                # cada elemento do subarray:
                for my $t (@$value) 
                {
                    # Se ele for um array, o imprimiremos de forma
                    # especial (um abaixo do outro):
                    if(ref $t eq "ARRAY") 
                    { 
                        # Números com dois dígitos, sempre!
                        $string .= sprintf("\n\t%0.2d: ", $i++);
                        for my $element (@$t) { 
                            # Elementos não definidos não são impressos
                            $string .= "$element " if defined $element; 
                        }
                    }
                    # Elementos simples
                    else { $string .= "$t, "; }
                }
            }
            when("HASH")  { $string .= print_hash($value) }
            default       { $string .= $value }
        }
        push @substring, $string;
    }
    $string = join "\n", @substring;
    return $string;
}

sub print_hash 
{
    my ($ref, $string) = (shift, "");
    foreach my $k (sort keys %$ref)
    { $string .= "\n$_ => $$ref{$k}"; }
    return $string;
}

1;
