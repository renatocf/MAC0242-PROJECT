package robot.function;

// Libraries
import stackable.*;
import exception.*;

public class Stk
{
    final private Stack DATA;
    
    Stk(Stack DATA)
    {
        this.DATA = DATA;
    }
    
    //##############################################################
    //##                     STACK FUNCTIONS                     ###
    //##############################################################
    void PUSH(Stackable st)
    {
        this.DATA.push(st);
    }
    
    Stackable POP() throws StackUnderflowException
    {
        return this.DATA.pop();
    }
    
    void DUP() throws StackUnderflowException
    {
      Stackable st = this.DATA.pop(); 
      this.DATA.push(st);
      this.DATA.push(st);
    }
}
