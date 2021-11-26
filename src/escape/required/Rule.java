/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.required;

import java.util.Objects;

/**
 * Interface for the rule implementations. It also contains a static
 * enumeration for the rule names. How you implement the actual  implementations
 * is up to you and the design decisions you make.
 * 
 * MODIFIABLE: NO
 * MOVEABLE: YES
 * REQUIRED: YES
 * 
 * You may extend this interface for your internal use, but this is the public interface
 * that all clients will use.
 */
public interface Rule
{
	public static enum RuleID {POINT_CONFLICT, REMOVE, SCORE, TURN_LIMIT};
	
	/**
	 * @return the RuleID
	 */
	RuleID getId();

	/**
	 * @return If this is a rule with a value (e.g. TURN_LIMIT), then this returns that value. 
	 * If it is has no value (e.g. REMOVE), return 0
	 */
	int getIntValue();
}
