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

public interface Returns
{   
    public final static int TRUE            =  1;
    public final static int SUCCEDED        =  1;

    public final static int FALSE           =  0;
    public final static int NOT_SUCCEDED    =  0;
    public final static int NO_TARGET       =  0;
    public final static int NO_ENERGY       =  0; //= -1;
    public final static int END_OF_MAP      =  0; //= -2;
    public final static int BLOCKED         =  0; //= -3;
    public final static int FULL_SLOTS      =  0; //= -3;
    public final static int EMPTY_SLOTS     =  0; //= -3;
    public final static int OUT_OF_RANGE    =  0; //= -3;
    public final static int FRIENDLY_FIRE   =  0; //= -4;
    public final static int INVALID_ACTION  =  0; //= -5;
}
