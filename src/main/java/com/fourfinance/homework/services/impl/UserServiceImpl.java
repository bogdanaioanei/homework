package com.fourfinance.homework.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourfinance.homework.dto.LoanDto;
import com.fourfinance.homework.dto.LoanExtensionDto;
import com.fourfinance.homework.entities.Authority;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.entities.LoanExtension;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.enums.UserRoleEnum;
import com.fourfinance.homework.formData.UserRegisterForm;
import com.fourfinance.homework.repositories.AuthoritiesRepository;
import com.fourfinance.homework.repositories.UserRepository;
import com.fourfinance.homework.services.UserContextService;
import com.fourfinance.homework.services.UserService;
import com.fourfinance.homework.viewModels.LoanHistoryModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SystemWideSaltSource systemWideSaltSource;

	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthoritiesRepository authRepository;

	@Autowired
	private UserContextService userContextService;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void createNewUser(UserRegisterForm userForm) {
		User user = userFormToUser(userForm);
		userRepository.save(user);

		Authority userAuth = new Authority();
		userAuth.setRole(UserRoleEnum.valueOf(userForm.getRole()));
		userAuth.setUser(user);
		authRepository.save(userAuth);

		user.setAuthority(userAuth);
		userRepository.save(user);
	}

	private User userFormToUser(UserRegisterForm userForm) {
		User newUser = new User();

		newUser.setFirstName(userForm.getFirstName());
		newUser.setLastName(userForm.getLastName());
		newUser.setEmail(userForm.getEmail());
		newUser.setUsername(userForm.getUsername());
		newUser.setPassword(shaPasswordEncoder.encodePassword(userForm.getPassword(),
				systemWideSaltSource.getSystemWideSalt()));

		newUser.setEnable(Boolean.TRUE);

		return newUser;
	}

	@Override
	@Transactional
	public LoanHistoryModel getLoanHistory() {

		User user = userContextService.getCurrentUser();

		LoanHistoryModel loanHistory = new LoanHistoryModel();
		loanHistory.setFirstName(user.getFirstName());
		loanHistory.setLastName(user.getLastName());
		loanHistory.setLoans(loansToLoanDtoList(user.getLoans()));

		return loanHistory;
	}

	private List<LoanDto> loansToLoanDtoList(List<Loan> loans) {

		List<LoanDto> loanDtoList = new ArrayList<LoanDto>();

		for (Loan loan : loans) {
			LoanDto loanDto = new LoanDto();
			loanDto.setLoanAmount(loan.getLoanAmount());
			loanDto.setAmountPaid(loan.getAmountPaid());
			loanDto.setAmountLeftToPay(loan.getAmountLeftToPay());
			loanDto.setExpirationDate(loan.getExpirationDate());
			loanDto.setInitialInterestRate(loan.getInitialInterestRate());
			loanDto.setCurrentInterestRate(loan.getCurrentInterestRate());
			loanDto.setLoanUID(loan.getLoanUID());

			List<LoanExtensionDto> loanExtensionDtoList = new ArrayList<LoanExtensionDto>();

			for (LoanExtension loanExtension : loan.getLoanExtensions()) {
				LoanExtensionDto loanExtensionDto = new LoanExtensionDto();
				loanExtensionDto.setNewExpirationDate(loanExtension.getNewExpirationDate());
				loanExtensionDto.setInterestRateMultiplier(loanExtension.getInterestRateMultiplier());
				loanExtensionDto.setNewInterestRate(loanExtension.getNewInterestRate());
				loanExtensionDto.setLoanExtensionUID(loanExtension.getLoanExtensionUID());

				loanExtensionDtoList.add(loanExtensionDto);
			}

			loanDto.setLoanExtensions(loanExtensionDtoList);
			loanDtoList.add(loanDto);
		}

		return loanDtoList;
	}

}
