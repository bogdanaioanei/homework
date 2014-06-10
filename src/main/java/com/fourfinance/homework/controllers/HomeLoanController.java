package com.fourfinance.homework.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourfinance.homework.formData.LoanForm;
import com.fourfinance.homework.services.LoanExtensionService;
import com.fourfinance.homework.services.LoanRiskAnalysisService;
import com.fourfinance.homework.services.LoanService;
import com.fourfinance.homework.services.LoanValidatorService;
import com.fourfinance.homework.services.UserContextService;
import com.fourfinance.homework.services.UserService;
import com.fourfinance.homework.util.IpAddressUtil;
import com.fourfinance.homework.utilBeans.CurrentDate;

@Controller
public class HomeLoanController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserContextService userContextService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanExtensionService loanExtensionService;

	@Autowired
	private LoanRiskAnalysisService loanRiskAnalysisService;

	@Autowired
	private LoanValidatorService loanValidatorService;

	@Autowired
	private CurrentDate currentDateUtil;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		if (!userContextService.isAuthenticated()) {
			return "redirect:/login";
		}

		model.addAttribute("loanModel", new LoanForm());

		return "home";
	}

	@RequestMapping(value = "/loan/request", method = RequestMethod.POST)
	public String requestLoan(@ModelAttribute("loanModel") @Valid LoanForm loanForm, BindingResult bindingResult,
			HttpServletRequest request) {

		if (!userContextService.isAuthenticated()) {
			return "redirect:/login";
		}

		String ipAddress = IpAddressUtil.getIpFromRequest(request);

		if (bindingResult.hasErrors()
				|| loanRiskAnalysisService.loanHasTimeIntervalRisk(loanForm, bindingResult,
						currentDateUtil.getCurrentDateTime())
				|| loanRiskAnalysisService.loanHasTooManyRequestsRisk(bindingResult, ipAddress)) {
			return "home";
		}

		loanService.takeLoan(loanForm, ipAddress);

		return "redirect:/loan/history";
	}

	@RequestMapping(value = "/loan/history", method = RequestMethod.GET)
	public String showLoanHistory(Model model) {

		if (!userContextService.isAuthenticated()) {
			return "redirect:/login";
		}

		model.addAttribute("loanHistory", userService.getLoanHistory());

		return "loan/history";
	}

	@RequestMapping(value = "/loan/extend", method = RequestMethod.POST)
	public String extendLoan(@RequestParam(value = "loanUID") String loanUID, HttpServletResponse response) {

		if (!userContextService.isAuthenticated()) {
			return "redirect:/login";
		}

		if (!loanValidatorService.loanExists(loanUID)) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			return "loan/history";
		}

		loanExtensionService.extendLoan(loanUID);

		return "redirect:/loan/history";
	}
}
