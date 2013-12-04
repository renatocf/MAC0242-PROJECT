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

/**
 * <b>Assembly functions - class Cast</b><br>
 * Provides the funcions for converting
 * between stackable types
 * 
 * @author Karina Awoki
 * @author Renato Cordeiro Ferreira
 * @author Vinícius Silva
 * @see stackable
 */
final public class Cast
{
    // No instances of this class allowed
    private Cast() {} 
    
    /**
     * Assembly funcion COOR. <br>
     * Takes out the two top of the main stack,
     * checks if they're a num. Creates a coordinate
     * with both I and J.
     *
     * @param rvm Virtual Machine
     */
    static void COOR(RVM rvm)
        throws StackUnderflowException,
               WrongTypeException
    {
        Stackable stkJ, stkI;
        
        try { stkJ = rvm.DATA.pop(); stkI = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        
        if(!(stkI instanceof Num && stkJ instanceof Num))
            throw new WrongTypeException("Num");
        
        rvm.DATA.push(new Coordinate((Num) stkI, (Num) stkJ));
        Debug.printStack(rvm);
    }
    
    /**
     * Assembly funcion GTIJ. <br>
     * Takes out the two top of the main stack,
     * checks if they're a num. Creates a coordinate
     * with both I and J.
     *
     * @param rvm Virtual Machine
     */
    static void GTIJ(RVM rvm)
        throws StackUnderflowException,
               WrongTypeException
    {
        Stackable stk;
        
        try { stk = rvm.DATA.pop(); }
        catch (Exception e) {
            throw new StackUnderflowException();
        }
        
        if(!(stk instanceof Coordinate))
            throw new WrongTypeException("Coordinate");
        
        Coordinate c = (Coordinate) stk;
        
        rvm.DATA.push(new Num(c.getI()));
        rvm.DATA.push(new Num(c.getJ()));
        Debug.printStack(rvm);
    }
}
