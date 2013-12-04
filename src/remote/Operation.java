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
package remote;

// Libraries
import arena.*;
import robot.*;
import exception.*;
import stackable.*;

/**
 * <b>Remote - Operation</b> <br>
 * Validates the type and arguments of
 * a given operation and works as an 
 * interface betwen the arena and a RVM.
 * 
 * @author Karina Suemi 
 * @author Renato Cordeiro
 * @author Vinícius Silva 
 * @see robot.RVM 
 * @see arena.World
 */
public class Operation implements Request
{
    final private   Stackable[] arg;
    final private   String action;
    final private   RVM robot;
    private boolean rightType;
    
    /**
     * Class constructor.
     * @param action Action to be executed
     * @param arg    Required arguments
     * 
     * @throws WrongTypeException
     * @throws InvalidOperationException
     */
    public Operation(RVM robot, String action, Stackable[] arg)
        throws InvalidOperationException,
               WrongTypeException
    {
        this.arg    = arg;
        this.robot  = robot;
        this.action = action;
        
        switch (this.action)
        {
            case "MOVE" : rightType = MOVE(); break;
            case "DRAG" : rightType = DRAG(); break;    
            case "DROP" : rightType = DROP(); break;
            case "HIT"  : rightType = HIT (); break;
            case "LOOK" : rightType = LOOK(); break;
            case "SEE"  : rightType = SEE (); break;
            case "ASK"  : rightType = ASK (); break; 
            case "SEND" : rightType = SEND(); break; 
            case "SKIP" : rightType = SKIP(); break; 
            default     : throw new InvalidOperationException(this.action);
        }
        if(!rightType)
        {
            throw new WrongTypeException("[OPERATION] Argument to operation "
                + this.action + ", but argument was " + arg.toString());
        }
    }
    
    public Operation(RVM robot, String action, Stackable arg)
        throws InvalidOperationException,
               WrongTypeException
    {
        this(robot, action, new Stackable[] { arg });
    }
    
    // Getters
    /** @return String with the action's name */
    public String      getAction   () { return this.action; }
    /** @return String with the action's argument's name */
    public Stackable[] getArgument () { return this.arg;    }
    
    // Verify arguments
    private boolean SEE  () { return true;                             }
    private boolean SKIP () { return true;                             }
    private boolean SEND () { return true;                             }
    private boolean MOVE () { return this.arg[0] instanceof Direction; }
    private boolean DRAG () { return this.arg[0] instanceof Direction; }
    private boolean DROP () { return this.arg[0] instanceof Direction; }
    private boolean LOOK () { return this.arg[0] instanceof Direction; }
    private boolean ASK  () { return this.arg[0] instanceof Text;      }
    
    private boolean HIT  () 
    { 
        // Argument 0: attack type; Argument 1: Number of directions
        if(!(this.arg[0] instanceof Attack)) return false;
        if((this.arg[1] instanceof Num))
        {
            // Check if the number of directions is consistent
            Num ndirs = (Num) arg[1];
            if(ndirs.getNumber() != arg.length-2) return false;
            
            // Check if the arguments are directions
            for(int i = 2; i < arg.length; i++)
                if(!(this.arg[i] instanceof Direction)) return false;
        }
        else if(this.arg[1] instanceof Coordinate) {}
        else return false;
 
        // Otherwise, it's all right       
        return true;
    }
}
