/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.gamedef;

/**
 * This interface identifies an object as a Coordinate.
 * It also defines the coordinate types.
 * 
 * MODIFIABLE: NO
 * MOVEABLE: YES
 * REQUIRED: YES
 * 
 * You may extend this interface for your internal use, but this is the public interface
 * that all clients will use.
 */
public interface Coordinate
{
	static enum CoordinateType {
		// Standard hex coordinates
		// The distance from (0,0)->(-1, 2) is 2; (-1, 2)->(2, -2) is 4.
		HEX, 
		
		// Standard squares where distance is measure as shortest combination of
		// orthogonal and diagonal moves. Examples: (1,1)->(2,2) is distance 1,
		// (1,2)->(3,5) is distance 3
		SQUARE,
		
		// Standard triangle. Distance is the shortest number of sides that must
		// be crossed to get to another coordinate. 
		// Examples: (1, 3)->(3, 4) is 3; (1, 2)->(1, 4) is 2
		TRIANGLE
	};
}
