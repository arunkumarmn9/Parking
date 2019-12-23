/**
 * 
 */
package org.classklap.parking.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.classklap.parking.dao.ParkingDataManager;
import org.classklap.parking.dao.ParkingLevelDataManager;
import org.classklap.parking.model.Vehicle;
import org.classklap.parking.model.strategy.NearestFirstParkingStrategy;
import org.classklap.parking.model.strategy.ParkingStrategy;

/**
 * @author Arun Kumar M N
 */
public class MemoryParkingManager<T extends Vehicle> implements ParkingDataManager<T>
{
	private Map<Integer, ParkingLevelDataManager<T>> levelParkingMap;
	
	private static MemoryParkingManager instance = null;
	
	public static <T extends Vehicle> MemoryParkingManager<T> getInstance(List<Integer> parkingLevels,
			List<Integer> capacityList, List<ParkingStrategy> parkingStrategies)
	{
		// Make sure the each of the lists are of equal size
		if (instance == null)
		{
			synchronized (MemoryParkingManager.class)
			{
				if (instance == null)
				{
					instance = new MemoryParkingManager<T>(parkingLevels, capacityList, parkingStrategies);
				}
			}
		}
		return instance;
	}
	
	private MemoryParkingManager(List<Integer> parkingLevels, List<Integer> capacityList,
			List<ParkingStrategy> parkingStrategies)
	{
		if (levelParkingMap == null)
			levelParkingMap = new HashMap<>();
		for (int i = 0; i < parkingLevels.size(); i++)
		{
			levelParkingMap.put(parkingLevels.get(i), MemoryParkingLevelManager.getInstance(parkingLevels.get(i),
					capacityList.get(i), new NearestFirstParkingStrategy()));
			
		}
	}
	
	@Override
	public int parkCar(int level, T vehicle)
	{
		return levelParkingMap.get(level).parkCar(vehicle);
	}
	
	@Override
	public boolean leaveCar(int level, int slotNumber)
	{
		return levelParkingMap.get(level).leaveCar(slotNumber);
	}
	
	@Override
	public List<String> getStatus(int level)
	{
		return levelParkingMap.get(level).getStatus();
	}
	
	public int getAvailableSlotsCount(int level)
	{
		return levelParkingMap.get(level).getAvailableSlotsCount();
	}
	
	@Override
	public List<String> getRegNumberForColor(int level, String color)
	{
		return levelParkingMap.get(level).getRegNumberForColor(color);
	}
	
	@Override
	public List<Integer> getSlotNumbersFromColor(int level, String color)
	{
		return levelParkingMap.get(level).getSlotNumbersFromColor(color);
	}
	
	@Override
	public int getSlotNoFromRegistrationNo(int level, String registrationNo)
	{
		return levelParkingMap.get(level).getSlotNoFromRegistrationNo(registrationNo);
	}
	
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}
	
	public void doCleanup()
	{
		for (ParkingLevelDataManager<T> levelDataManager : levelParkingMap.values())
		{
			levelDataManager.doCleanUp();
		}
		levelParkingMap = null;
		instance = null;
	}
}
