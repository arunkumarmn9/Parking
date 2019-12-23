/**
 * 
 */
package org.classklap.parking.model.strategy;

/**
 * @author Arun Kumar M N
 */
public interface ParkingStrategy
{
	public void add(int i);
	
	public int getSlot();
	
	public void removeSlot(int slot);
}
