package com.fourfinance.homework.utilBeans;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public interface CurrentDate {

	DateTime getCurrentDateTime();
}
