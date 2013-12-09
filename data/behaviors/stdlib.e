/**
 * Just for good luck. 
 * @return The word seven
 */
def string sete();

/**
 *  Look for a enemy base. 
 *  @return coord of the base if finded;
 *  @see stdlib#look
 */
def coord lookEnemyBase();

/**
 *  Drag a near crystal.
 *  @return TRUE if drag, some drag error 
 *          if otherwise
 */
def number dragNearCrystal();

/**
 *  Move the robot until it arrives next to 
 *  destiny or be blocked.
 *  @param  c coord of the destiny
 *  @return TRUE if it arrives, some move error 
 *          if otherwise
 */
def number moveNextTo(coord);

/**
 *  The distance between the robot and the coor.
 *  @param  c target coord
 *  @return distance
 */
def number distance(coord);

/**
 *  Get the way in the oposite direction.
 *  @param  w old way
 *  @return inverted way
 */
def way invertWay(way);

/**
 *  look for a crystal. 
 *  @return coord of the crystal if finded;
 *  @see stdlib#look
 */
def coord lookCrystal();

/**
 *  Attack a obstacle in the way until it is removed. 
 *  @param  c Destiny point
 *  @return same of hit()
 *  @see stdlib#hit
 */
def number removeObstacle(coord);

/**
 *  Attack a obstacle in the way.
 *  @param  c Destiny point
 *  @return same of hit()
 *  @see stdlib#hit
 */
def number hitObstacle(coord);

/**
 *  Move the robot until it arrives or be blocked.
 *  @param  c coord of the destiny
 *  @return TRUE if it arrives, some move error 
 *          if otherwise
 */
def number moveTo(coord);

/**
 *  Try to evade an obstacle on the way.
 *  @param  c coord of the destiny
 *  @return TRUE if success, FALSE otherwise
 */
def number evade(coord);

/**
 *  Get the next way in the anticlockwise order 
 *  @param  w old way
 *  @return next way anticlockwise
 */
def way rotAntiClockWise(way);

/**
 *  Get the next way in the clockwise order 
 *  @param  w old way
 *  @return next way clockwise
 */
def way rotClockWise(way);

/**
 *  Move the robot towards a point. 
 *  @param  c Target point
 *  @return same of move()
 *  @see stdlib#_move
 */
def number moveTowards(coord);

/**
 *  Get a way to a point. 
 *  @param  c Target point
 *  @return way desired
 */
def way straightWayToPoint(coord);

/**
 *  Update the magic variables. 
 */
def void updateVars();

/**
 *  Update the coordinates. 
 *  @param  w Direction of the variation
 *  @return Variation of a coord
 */
def coord  relativeCoord(way);

/**
 *  Move and update &ROBOT. 
 *  @param  w Direction of the movement
 *  @return same of move()
 *  @see stdlib#_move
 */
def number move(way);
