package com.fourfinance.homework.utilBeans.impl;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.fourfinance.homework.utilBeans.CurrentDate;

@Service
public class CurrentDateImpl implements CurrentDate {

	@Override
	public DateTime getCurrentDateTime() {
		return DateTime.now();
	}

}
