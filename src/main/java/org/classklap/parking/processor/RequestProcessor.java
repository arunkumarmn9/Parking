/**
 * 
 */
package org.classklap.parking.processor;

import org.classklap.parking.constants.Constants;
import org.classklap.parking.exception.ErrorCode;
import org.classklap.parking.exception.ParkingException;
import org.classklap.parking.model.Car;
import org.classklap.parking.service.AbstractService;
import org.classklap.parking.service.ParkingService;

/**
 * @author Arun Kumar M N
 */
public class RequestProcessor implements AbstractProcessor
{
	private ParkingService parkingService;
	
	public void setParkingService(ParkingService parkingService) throws ParkingException
	{
		this.parkingService = parkingService;
	}
	
	@Override
	public void execute(String input) throws ParkingException
	{
		int level = 1;
		String[] inputs = input.split(" ");
		String key = inputs[0];
		switch (key)
		{
			case Constants.CREATE_PARKING_LOT:
				try
				{
					int capacity = Integer.parseInt(inputs[1]);
					parkingService.createParkingLot(level, capacity);
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
				}
				break;
			case Constants.PARK:
				parkingService.park(level, new Car(inputs[1], inputs[2]));
				break;
			case Constants.LEAVE:
				try
				{
					int slotNumber = Integer.parseInt(inputs[1]);
					parkingService.unPark(level, slotNumber);
				}
				catch (NumberFormatException e)
				{
					throw new ParkingException(
							ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
				}
				break;
			case Constants.STATUS:
				parkingService.getStatus(level);
				break;
			case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
				parkingService.getRegNumberForColor(level, inputs[1]);
				break;
			case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
				parkingService.getSlotNumbersFromColor(level, inputs[1]);
				break;
			case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
				parkingService.getSlotNoFromRegistrationNo(level, inputs[1]);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void setService(AbstractService service)
	{
		this.parkingService = (ParkingService) service;
	}
}
