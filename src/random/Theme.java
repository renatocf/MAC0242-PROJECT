package random;

/**
 * <b>Theme</b><br>
 * Simple interface to gather Map Themes.
 *
 * @author Vinicius Silva
 */
public interface Theme
{
    char[][] generateMatrix (int side);
}

/**
 * <b>Thematic</b><br>
 * Simple interface extend Theme, demmanding
 * from the Themes having a way of putting 
 * bases when creating a map.
 *
 * @author Renato Cordeiro Ferreira
 */
interface Thematic extends Theme
{
    char[][] putBases (char[][] map);
}
