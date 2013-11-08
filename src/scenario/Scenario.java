package scenario;

// Libraries
import gui.Printable;
import players.Player;

/** 
 * <b>Scenario</b><br>
 * General interface for scenarios that
 * appear in the arena.
 */
public interface Scenario extends Printable
{
    /**
     * Remaining HP to the scenario 
     * be removed from the map.
     */
    public int getHP();
    
    /** 
     * Amount of damage taken by a scenario.
     * If the scenario should not be destroyed,
     * this method may have no action over it HP.
     * @param damage Total of points to
     *               be subtracted from 
     *               the HP.
     * @return Scenario's remining HP.
     */
    public int takeDamage(int damage);
    
    /**
     * Prints the scenario in a more legible
     * way, with debug purposes.
     */
    public String toString();
    
    /** 
     * Returns the team of a given scenario
     * (ordinary elements should be Nature).
     * @return Player that owns the scenario
     */
    public Player getTeam();
}
