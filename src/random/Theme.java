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
package random;

/**
 * <b>Theme</b><br>
 * Simple abstract class to define Map Themes.
 *
 * @author Renato Cordeiro Ferreira
 * @author Vinicius Silva
 */
abstract class Theme
{
    // Package leven (only)
    abstract char[][] generateMatrix (int side);
    abstract char[][] putBases    (char[][] map);
    abstract char[][] putCrystals (char[][] map);
    abstract char[][] putRocks    (char[][] map);
    abstract char[][] putStones   (char[][] map);
    abstract char[][] putTrees    (char[][] map);
}
