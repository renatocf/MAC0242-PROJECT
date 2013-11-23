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
package stackable;

// Libraries
import exception.*;

@Deprecated
public class Stack
{
    private int size = 20, top = 0;
    private Stackable[] vector = new Stackable[size];
    
    void doubleVector()
    {
        Stackable vectorAux[] = new Stackable[this.size];
        this.size *= 2;
        System.arraycopy (this.vector, 0, vectorAux, 0, this.top);
        vector = new Stackable[this.size];
        System.arraycopy (vectorAux, 0, this.vector, 0, this.top);
        vectorAux = null;
    }

    public int getTopIndex()
    {
        return this.top;
    }
    
    public boolean empty()
    {
        return (top == 0) ? (true) : (false);
    }

    public void push(Stackable item)
    {
        this.vector[this.top] = item;
        this.top++;

        if(this.top > this.size/2)
        {
            this.doubleVector();
        }
    }

    public Stackable pop() throws StackUnderflowException
    {
        if(!this.empty())
        {
            this.top--;
            return this.vector[this.top];
        }
        throw new StackUnderflowException();
    }
}
