package com.fourfinance.homework.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public final class IpAddressUtil {

	private IpAddressUtil() {

	}

	public static String getIpFromRequest(HttpServletRequest request) {
		String addrHeader = request.getHeader("X-Forwarded-For");

		String remoteAddr = "";

		if (!StringUtils.isEmpty(addrHeader)) {
			String[] addresses = addrHeader.split(",");
			if (addresses.length > 0) {
				remoteAddr = addresses[0];
			}
		}

		if (StringUtils.isEmpty(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		} else {
			remoteAddr = remoteAddr.trim();
		}
		return remoteAddr;
	}
}
