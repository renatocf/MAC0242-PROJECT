/**********************************************************************/
/* Copyright 2013 KRV                                                 */
/*                                                                    */
/* Licensed under the Apache License, Version 2.0 (the "License");    */
/* you may not use this file except in compliance with the License.   */
/* You may obtain a copy of the License at                            */
/*                                                                    */
/*  http://www.apache.org/licenses/LICENSE-2.0                        */
/*                                                                    */
/* Unless required by applicable law or agreed to in writing,         */
/* software distributed under the License is distributed on an        */
/* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,       */
/* either express or implied.                                         */
/* See the License for the specific language governing permissions    */
/* and limitations under the License.                                 */
/**********************************************************************/
package robot;

// Libraries
import stackable.*;
import exception.*;
import parameters.*;
import stackable.item.*;

/**
 * <b>Assembly functions - class Tests</b><br>
 * Provides the funcions for making 
 * boolean comparisons between elements
 * inside the stack of the virtual Machine.
 * Also verifies if an element is or not Nil.
 * 
 * @author Renato Cordeiro Ferreira
 * @see Ctrl
 */
final public class Tests
{
    // No instances of this class allowed
    private Tests() {} 
    
    /**
     * Assembly funcion NIL. <br>
     * Takes out the top of the main stack 
     * and pushes 1 if it is Nil and 0, 
     * otherwise.
     *
     * @param rvm Virtual Machine
     */
    static void NIL(RVM rvm)
        throws StackUnderflowException,
               WrongTypeException
    {
        Stackable stk;
        
        try { stk = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        
        // Return Num(0) if the top is not Nil
        rvm.DATA.push(new Num(stk instanceof Nil ? 1 : 0));
    }
    
    /** 
     * Interface for compartison operations,
     * Dummy interface with the aim of being a 
     * model for all the comparison operations.
     */
    private interface Cmp
    {
        boolean cmp(double a, double b);
    }

    /**
     * Assembly funcion EQ. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack.
     * <p>
     * If numerical, checks if their value are
     * the same. In the other case, Checks if
     * they have the same type.
     * 
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void EQ(RVM rvm) 
        throws WrongTypeException, StackUnderflowException
    {
        // Default answers
		boolean res;
        Num no = new Num(0), yes = new Num(1);
           
        // Stores the arguments to be tested
        Stackable arg2 = rvm.DATA.pop(), arg1 = rvm.DATA.pop();
        
        if(arg1 instanceof Num && arg2 instanceof Num)
        {
            // Downcasts to number if the type is correct
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            
            // Result of the comparison
            res = num1.getNumber() == num2.getNumber();
        }
        else if(arg1 instanceof Direction && arg2 instanceof Direction)
        {
            // Downcasts to direction if the type is correct
            Direction d1 = (Direction) arg1, d2 = (Direction) arg2;
            
            // Gets the direction
            String s1 = d1.toString(), s2 = d2.toString();
            
            // Result of the comparison
			res = s1.equals(s2);
        }
        else if(arg1 instanceof Coordinate && arg2 instanceof Coordinate)
        {
            // Downcasts to direction if the type is correct
            Coordinate c1 = (Coordinate) arg1, c2 = (Coordinate) arg2;
            
            // Gets the direction
            String s1 = c1.toString(), s2 = c2.toString();
            
            // Result of the comparison
			res = s1.equals(s2);
        }
        else
        {
            String class1 = arg1.getClass().getName();
            String class2 = arg2.getClass().getName();
			
            // Result of the comparison
			res = class1.equals(class2);
        }
            
		// Push true or false accordingly to the comparison
		if(res) rvm.DATA.push(yes);
		else	rvm.DATA.push(no);
			
		// Debug
		Debug.printCmp(rvm, res);
    }
    
    /**
     * Assembly funcion NEQ. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack.
     * <p>
     * If numerical, checks if their value are
     * different. In the other case, Checks if
     * they have different types.
     * 
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void NE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        // Default answers
		boolean res;
        Num no = new Num(0), yes = new Num(1);
           
        // Stores the arguments to be tested
        Stackable arg1 = rvm.DATA.pop(), arg2 = rvm.DATA.pop();
        
        if(arg1 instanceof Num && arg2 instanceof Num)
        {
            // Downcasts to number if the type is correct
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            
            // Result of the comparison
            res = !(num1.getNumber() == num2.getNumber());
        }
        else if(arg1 instanceof Direction && arg2 instanceof Direction)
        {
            // Downcasts to direction if the type is correct
            Direction d1 = (Direction) arg1, d2 = (Direction) arg2;
            
            // Gets the direction
            String s1 = d1.toString(), s2 = d2.toString();
            
            // Result of the comparison
			res = !s1.equals(s2);
        }
        else if(arg1 instanceof Coordinate && arg2 instanceof Coordinate)
        {
            // Downcasts to direction if the type is correct
            Coordinate c1 = (Coordinate) arg1, c2 = (Coordinate) arg2;
            
            // Gets the direction
            String s1 = c1.toString(), s2 = c2.toString();
            
            // Result of the comparison
			res = !s1.equals(s2);
        }
        else
        {
            String class1 = arg1.getClass().getName();
            String class2 = arg2.getClass().getName();
			
			res = !class1.equals(class2);
        }
		
		// Push true or false accordingly to the comparison
		if(res) rvm.DATA.push(yes); 
		else    rvm.DATA.push(no);
			
		// Debug
		Debug.printCmp(rvm, res);
    }
    
    /**
     * General-model funcion compare. <br>
     * Makes the correspondent funcion 
     * comparison between the two elements
     * int the top of the main stack.
     * 
     * @param  rvm Virtual Machine
     * @param  cmp Anonymous inner class that
     *             implements Cmp interface
     * 
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void compare(RVM rvm, Cmp cmp) 
        throws WrongTypeException, StackUnderflowException
    {
        // Default answers
        Num no = new Num(0), yes = new Num(1);
           
        // Stores the arguments to be tested
        Stackable arg2 = rvm.DATA.pop(), arg1 = rvm.DATA.pop();
        
        if(arg1 instanceof Num && arg2 instanceof Num)
        {
            // Downcasts to number if the type is correct
            Num num1 = (Num) arg1, num2 = (Num) arg2;
            
            // Result of the comparison
            boolean res = cmp.cmp(num1.getNumber(), num2.getNumber());
			
			// Debug
			Debug.printCmp(rvm, res);
            
            // Push true or false accordingly to the comparison
            if(res) { rvm.DATA.push(yes); } else { rvm.DATA.push(no); }
        }
        else throw new WrongTypeException("Num");
    }
    
    /**
     * Assembly funcion GT. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is greater than the 
     * second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void GT(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a > b; } }
        );
    }
    
    /**
     * Assembly funcion GE. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is greater or equal 
     * than the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void GE(RVM rvm) 
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a >= b; } }
        );
    }
    
    /**
     * Assembly funcion LT. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is lower than the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void LT(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a < b; } }
        );
    }
    
    /**
     * Assembly funcion LE. <br>
     * Compares the two top most elements of 
     * the virtual machine's main stack to 
     * see if the top is lower or equal than 
     * the second.
     *
     * @param  rvm Virtual Machine
     * @throws WrongTypeException
     * @throws StackUnderflowException
     */
    static void LE(RVM rvm)
        throws StackUnderflowException, WrongTypeException
    {
        compare(rvm, new Cmp() {
            public boolean cmp(double a, double b) { return a <= b; } }
        );
    }
}
