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
my $help  = undef; 
my $build = "build/classes";

Getopt::Long::Configure('bundling');
GetOptions(
    "b|build-path=s" => \$build,
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
my %textual;
my %numeric;
my %direction;

my $n = 0;
for my $line (@prog)
{
    # Split line fields
    my ($func, $arg, $label) = @$line;
    
    # Creating variables
    if(defined $arg)
    {
        if( looks_like_number($arg) ) 
        {
            $line->[1] = "n$arg";
            $numeric{$arg} = "Num n$arg = new Num($arg);";
        }
        elsif($arg =~ /^->/)
        {
            $arg = uc $arg; $arg =~ s/->//; $line->[1] = "d$arg";
            $direction{$arg} = "Direction d$arg = new Direction(\"$arg\");";
        }
        else{ 
            if(not exists $textual{$arg})
            {
                $n++; $line->[1] = "msg$n";
                $textual{$arg} = 
                    [ $n, "Text msg$n = new Text(\"$arg\");" ];
            }
            else { $line->[1] = "msg$textual{$arg}[0]"; }
        }
    }
}

########################################################################
##                          PRINTING PARSER                           ##
########################################################################
mkdir "$base/$src/parser"; 
open(my $PARSER, ">", "$base/$src/parser/Parser.java");
select $PARSER;

# Preamble
say << "PREAMBLE";
 package parser;

// Default libraries
import stackable.*;
import robot.*;
import java.util.Vector;
PREAMBLE

say << "PARSER_H";
/**
 * Parser for program $file.
 * This file is automatically 
 * generated by parser.pl
 * \@author parser.pl
 */
public class Parser
{
    /** 
     * Method for uploading the program.
     */
    public Vector<Command> upload()
    {
        Vector<Command> PROG = new Vector<Command>();
PARSER_H

# Print sorted numerical and textual variables
if(scalar keys %numeric) 
{
    say " " x 8, "// Numerical variables";
    for my $num (sort keys %numeric) { say " " x 8, $numeric{$num}; }
    print "\n";
}

if(scalar keys %textual)
{
    say " " x 8, "// textual variables";
    for my $txt (sort keys %textual) { say " " x 8, $textual{$txt}[1]; }
    print "\n";
}

if(scalar keys %direction)
{
    say " " x 8, "// Direction variables";
    for my $dir (sort keys %direction) { say " " x 8, $direction{$dir}; }
    print "\n";
}

# Printing program
for my $line (@prog)
{
    print " " x 8; 
    print "PROG.add(new Command(";
    (defined $line->[0]) 
        ? (printf "%-*s , ", 6, "\"$line->[0]\"") : (print "null   , ");
    (defined $line->[1]) 
        ? (printf "%-*s , ", 4,  $line->[1]) : (print "null , ");
    (defined $line->[2]) 
        ? (printf "%s", "\"$line->[2]\"") : (print "null");
    print "));";
    
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

# Creating parser.xml for compilation
open(my $xml, ">", "$base/$src/parser/Parser.xml");
select $xml;

say << "BUILD_XML";
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
select STDOUT; 
close $xml;

# Building file
system("ant -buildfile $base/$src/parser/Parser.xml");
