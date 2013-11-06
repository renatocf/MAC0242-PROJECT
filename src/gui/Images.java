package gui;

/**
 * Set up a root directory for all stored 
 * images inside the graphical user interface.
 * @see Graphical
 * 
 * @author Renato Cordeiro Ferreira
 */
interface Images
{
    final String ROOT = "/img/";
}
    
/**
 * Items avaiable.
 */
interface Items extends Images
{
    final String Crystal = ROOT + "Crystal.png";
}
    
/**
 * Terrain appearences avaiable.
 */
interface Appearences extends Images
{
    final String Ice   = ROOT + "Ice.png";
    final String Dirt  = ROOT + "Dirt.png";
    final String Sand  = ROOT + "Sand.png";
    final String Snow  = ROOT + "Snow.png";
    final String Grass = ROOT + "Grass.png";
}
