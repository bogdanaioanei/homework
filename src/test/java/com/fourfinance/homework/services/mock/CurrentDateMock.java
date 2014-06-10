package com.fourfinance.homework.services.mock;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fourfinance.homework.utilBeans.CurrentDate;

@Component
public class CurrentDateMock implements CurrentDate {

	private DateTime currentTime;

	public DateTime getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(DateTime currentTime) {
		this.currentTime = currentTime;
	}

	/**
	 * Return invalid date for loan request
	 * */
	@Override
	public DateTime getCurrentDateTime() {
		if (currentTime == null) {
			return DateTime.now();
		}
		
		return currentTime;
	}

}
