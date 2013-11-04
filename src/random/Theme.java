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
