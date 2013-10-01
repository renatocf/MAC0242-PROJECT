import stackable.*;

class Main
{
  public static void main(String[] args) throws StackUnderflowException
  {
    Stack s = new Stack();
    
    for(int i = 0; i < 100 ; i++)
    {
        s.push(new Num(i));
    }
    
    //! for(int i = 0; i < 110; i++)
    for(int i = 0; i < 10 ; i++)
    {
      System.out.println(s.pop());
    } 
  }
}
