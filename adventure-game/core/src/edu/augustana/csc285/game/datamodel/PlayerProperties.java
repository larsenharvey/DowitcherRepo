package edu.augustana.csc285.game.datamodel;
import java.util.HashMap;
/**
 * 
 * @author Dat Tran
 *
 */

public class PlayerProperties {
	private HashMap<Property, Integer> properties;
	public static final int INITIAL_HEALTH = 10;
	public static final int INITIAL_MORALE = 10;
	public static final int INITIAL_GOLD = 400;
	public static final int INITIAL_DAY = 0;

	/**
	 * post: usually used to create a new copy of the properties
	 * 
	 * @param other
	 *            is the PlayerProperties
	 */
	public PlayerProperties(PlayerProperties other) {
		properties = new HashMap<Property, Integer>(other.properties);
	}

	public PlayerProperties(int health, int morale, int gold, int day) {
		properties = new HashMap<Property, Integer>();
		properties.put(Property.HEALTH, health);
		properties.put(Property.MORALE, morale);
		properties.put(Property.GOLD, gold);
		properties.put(Property.DAY, day);
	}

	/**
	 * post: create a default PlayerProperties, should only be used at beginning
	 * of game
	 */
	public PlayerProperties() {
		properties.put(Property.HEALTH, INITIAL_HEALTH);
		properties.put(Property.MORALE, INITIAL_MORALE);
		properties.put(Property.GOLD, INITIAL_GOLD);
		properties.put(Property.DAY, INITIAL_DAY);
	}

	public HashMap<Property, Integer> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<Property, Integer> properties) {
		this.properties = properties;
	}

	/**
	 * 
	 * @param other:
	 *            other properties set, usually in the option to see if it is
	 *            visible
	 * @return: true if this has greater quantity in each element than other
	 */
	public boolean checkProperties(PlayerProperties other) {
		if (other.properties != null) {
			for (Property property : other.properties.keySet()) {
				Integer temp = other.properties.get(property);
				if (temp != null) {
					if (properties.get(property) == null || temp > properties.get(property)) {
						return false;
					}
				}
			}
			return true;
		}
		return true;
	}

	/**
	 * @param other:
	 *            other properties set, usually in the option to update player
	 *            properties post: add properties to this by the properties in
	 *            other
	 */
	public void addProperties(PlayerProperties other) {
		if (other !=null && other.properties!=null) {
		for (Property property : other.properties.keySet()) {
			Integer temp1 = other.properties.get(property);
			Integer temp2 = properties.get(property);
			if (temp1 != null) {
				temp1 += temp2;
			} else {
				temp1 = temp2;
			}
			properties.put(property, temp1);
		}}
	}

	/**
	 * 
	 * @param other:
	 *            other properties set, usually in the option to update player
	 *            properties post: subtract properties from this by the
	 *            properties in other
	 * @throws IllegalArgumentException
	 *             if there is not enough in this to subtract from
	 */
	public void subtractProperties(PlayerProperties other) {
		if (!checkProperties(other)) {
			throw new IllegalArgumentException("other properties is greater than this properties");
		} else {
			if (other !=null && other.properties!=null) {
			for (Property property : other.properties.keySet()) {
				Integer temp1 = other.properties.get(property);
				Integer temp2 = properties.get(property);
				properties.put(property, temp1 - temp2);
			}
		}
	}
}
}
