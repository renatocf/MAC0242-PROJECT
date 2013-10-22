#!/usr/bin/perl
package main;
use v5.10;

# Adding modules in @INC
use FindBin qw($Bin);
use lib "$Bin/../lib";

# Pragmas
use strict;
use warnings;

# Packages
use Cortex;

# Function to evaluate if a scalar is a number (or not)
use Scalar::Util qw(looks_like_number); 

# Options
use Getopt::Long;

my $base  = "$Bin/..";
my $src   = "src";
my $item  = "$base/$src/stackable/item";
my $help  = undef; 
my $build = "build/classes";

Getopt::Long::Configure('bundling');
GetOptions(
    "b|build-path=s" => \$build,
    "i|item-path=s"  => \$item,
    "h|help"         => \$help,
    "r|root-path=s"  => \$base,
    "s|src-path=s"   => \$src,
);

# Help/Usage
die << "HELP" if($help);

 parser.pl
 --------------------
 Creates a package in Java for runngin a 
 program in assembly, loaded by Main.java
 
 OPTIONS:
 * -b, --build-path=s
   Path/Directory for .class files.
 * -r, --root-path=s
   Root directory for all the process.
 * -s, --src-path=s
   Path/Directory for .java files.

 AUTHORS
 Karina Suemi, Renato Cordeiro Ferreira, 
 Vinícius Silva.

HELP

if(scalar @ARGV != 1) { 
    die "USAGE: perl parser.pl program.txt\n";
} 

#######################################################################
##                             PARSING                               ##
#######################################################################

my $file = shift;

# Criando parser e programa
my $positronic_brain = new Cortex;
my @prog = $positronic_brain->parse("$file");

########################################################################
##                             PREPROC                                ##
########################################################################

my $max_size_com = 4; # null
my $max_size_arg = 4; # null
my $max_size_lab = 4; # null

# Hashes with values
my %item;
my %attack;
my %address;
my %textual;
my %numeric;
my %direction;

# Get items from item directory
opendir ITEMS, $item;
map  { s/\.java//; $item{$_} = 0 } 
grep { not /^\./ and not /^Item/ and -f "$item/$_"} readdir ITEMS;
closedir ITEMS;

my ($t, $n, $a) = (0, 0, 0);
for my $line (@prog)
{
    next if not defined $line;
    
    # Split line fields
    my ($func, $arg, $label) = @$line;
    
    # Calculate maximum function and label
    # (each +2 are came from the double quotes)
    if(defined $func and $max_size_com < length($func) + 2)
    { $max_size_com = length($func) + 2; }
    
    if(defined $label and $max_size_lab < length($label)+ 2)
    { $max_size_lab = length($label) + 2; }
    
    # Creating variables
    if(defined $arg)
    {
        # Address argument
        if($arg =~ /^0x(\d+)/)
        {
            $arg = $1;
            if(not exists $address{$arg})
            {
                $a++; $line->[1] = "adr$a";
                $address{$arg} = 
                    [ $a, "Addr adr$a = new Addr($arg);" ];
            }
            else { $line->[1] = "addr$address{$arg}[0]"; }
        }
        
        # Numeric argument
        elsif( looks_like_number($arg) )
        {
            if(not exists $numeric{$arg})
            {
                $n++; $line->[1] = "num$n";
                $numeric{$arg} = 
                    [ $n, "Num num$n = new Num($arg);" ];
            }
            else { $line->[1] = "num$numeric{$arg}[0]"; }
        }
        
        # Direction
        elsif($arg =~ /^->/)
        {
            $arg = uc $arg; 
            $arg =~ s/->//; 
            $line->[1] = "d$arg";
            $direction{$arg} = "Direction d$arg = new Direction(\"$arg\");";
        }
        
        # Attack argument
        elsif($arg =~ /^\(x\)(\w+)$/)
        {
            $arg = uc $arg; 
            $arg =~ s/\(X\)//; 
            $line->[1] = "x$arg";
            $direction{$arg} = "Attack x$arg = new Attack(\"$arg\");";
        }
        
        # Stackable (item) argument
        elsif($arg =~ /^{(\w+)}$/)
        {
            while(my ($key, $value) = each %item)
            {
                my $stk = lc $1;
                if($stk =~ /$key/i) 
                    { $item{$key} = 1; $line->[1] = uc $key; }
            }
        }
        
        # String argument
        else
        { 
            if(not exists $textual{$arg})
            {
                $t++; $line->[1] = "msg$t";
                $textual{$arg} = 
                    [ $t, "Text msg$t = new Text(\"$arg\");" ];
            }
            else { $line->[1] = "msg$textual{$arg}[0]"; }
        }
    }
}


# Checks maximum argument size (items)
for my $var(keys %item)
{
    next unless($item{$var}); # Ignores items that not appeared
    if(length $var > $max_size_arg) 
    { $max_size_arg = length $var; }
}                

# Checks maximum argument size (attack)
for my $var(keys %attack) 
{ 
    if(length $var > $max_size_arg+1) 
    { $max_size_arg = length $var; }
}

# Checks maximum argument size (others)
for my $var($t, $n, $a)
{
    if(length $var > $max_size_arg+3) 
    { $max_size_arg = length $var; }
}

########################################################################
##                          PRINTING PARSER                           ##
########################################################################
mkdir "$base/$src/parser";
my $java_file = $file;
$java_file =~ s|.*/(.*)\..*|$1|;
$java_file = ucfirst lc $java_file;
say "FILE: $java_file";

open(my $PARSER, ">", "$base/$src/parser/$java_file.java");
select $PARSER;

# Preamble
say << "PREAMBLE";
 package parser;

// Default Libraries
import java.util.Vector;

// Libraries
import robot.*;
import exception.*;
import stackable.*;
import stackable.item.*;
PREAMBLE

say << "PARSER_H";
/**
 * Parser for program $file.
 * This file is automatically 
 * generated by parser.pl
 * \@author parser.pl
 */
final public class $java_file
{
    // No instances of this class allowed
    private $java_file() {}
    
    /** 
     * Method for uploading the program.
     */
    public static Vector<Command> upload()
        throws InvalidOperationException
    {
        Vector<Command> PROG = new Vector<Command>();
PARSER_H

# Print sorted numerical, textual, direction and stackable variables
if(scalar keys %address) 
{
    my %address_numeric;
    
    while(my ($key, $value) = each %address)
    {
        my ($var, $address) = ($value->[0], $value->[1]);
        $address_numeric{$var} = $address;
    }   
    
    say " " x 8, "// Address variables";
    for my $var (sort keys %address_numeric) 
    { say " " x 8, $address_numeric{$var}; }
    print "\n";
}

if(scalar keys %numeric) 
{
    my %numbered_numeric;
    
    while(my ($key, $value) = each %numeric)
    {
        my ($var, $number) = ($value->[0], $value->[1]);
        $numbered_numeric{$var} = $number;
    }   
    
    say " " x 8, "// Numerical variables";
    for my $var (sort keys %numbered_numeric) 
    { say " " x 8, $numbered_numeric{$var}; }
    print "\n";
}

if(scalar keys %textual)
{
    say " " x 8, "// Textual variables";
    for my $txt (sort keys %textual) 
    { say " " x 8, $textual{$txt}[1]; }
    print "\n";
}

if(scalar keys %direction)
{
    say " " x 8, "// Direction variables";
    for my $dir (sort keys %direction) 
    { say " " x 8, $direction{$dir}; }
    print "\n";
}

# Printing items (if used)
while(my ($key, $value) = each %item)
{
    next if not $item{$key}; # No items found
    
    my $class = ucfirst $key;
    my $object = uc $key;
    
    say " " x 8, "// $class variable";
    say " " x 8, "$class $object = new $class();";
    print "\n";
}

# Printing program
for my $line (@prog)
{
    print " " x 8; 
    print "PROG.add(new Command( ";
    (defined $line->[0]) 
        ? (printf "%-*s , ", $max_size_com, "\"$line->[0]\"") 
        : (printf "%-*s , ", $max_size_com, "null");
    (defined $line->[1]) 
        ? (printf "%-*s , ", $max_size_arg,  $line->[1]) 
        : (printf "%-*s , ", $max_size_arg,  "null"); 
    (defined $line->[2]) 
        ? (printf "%-*s",    $max_size_lab, "\"$line->[2]\"") 
        : (printf "%-*s",    $max_size_lab, "null");
    print " ));";
    
    print "\n";
}

# Printing program's end
say << "PARSER_B";
        PROG.add(null);

        return PROG;
    }
}
PARSER_B

select STDOUT;
close $PARSER;

########################################################################
##                          COMPILING CODE                            ##
########################################################################

my $build_xml = << "BUILD_XML";
<?xml version="1.0" encoding="utf-8"?>
<project name="Parser.java" default="compile" basedir="$base">
    <description>
        Automatic generated xml to compile java parser.
    </description>

    <!-- Build directory -->
    <property name="src.dir"   location="$src/parser"/>
    <property name="build.dir" location="$build"/>
    
    <!-- Creating directories for build -->
    <target name="init">
        <mkdir dir="\${build.dir}" />
    </target>
    
    <!-- Compile messages and rules -->
    <target name="compile" depends="init">
        <javac destdir = "\${build.dir}"
               srcdir  = "\${src.dir}"
               classpath = "$base/$src"
               includeAntRuntime = "no"
        />
    </target>
    
    <!-- Clean files -->
    <target name="clean">
        <delete dir="\${src.dir}" />
        <delete dir="\${build.dir}" />
    </target>

</project>
BUILD_XML

# Creating parser.xml for compilation
unless(-f "$base/$src/parser/Parser.xml")
{
    open (XML, ">", "$base/$src/parser/Parser.xml");
    say   XML $build_xml;
    close XML;
}

# Building file
system("ant -buildfile $base/$src/parser/Parser.xml");
