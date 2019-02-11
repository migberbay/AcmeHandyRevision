package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WelcomeMessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.WelcomeMessage;

@Service
@Transactional
public class WelcomeMessageService {

	@Autowired
	private WelcomeMessageRepository welcomeMessageRepository;

	public WelcomeMessage create() {

		WelcomeMessage res = new WelcomeMessage();
		return res;
	}

	public Collection<WelcomeMessage> findAll() {
		return welcomeMessageRepository.findAll();
	}

	public WelcomeMessage findOne(int Id) {
		return welcomeMessageRepository.findOne(Id);
	}

	public WelcomeMessage save(WelcomeMessage welcomeMessage) {

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		WelcomeMessage result;

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		result = welcomeMessageRepository.save(welcomeMessage);
		return result;
	}

	public void delete(WelcomeMessage welcomeMessage) {

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		welcomeMessageRepository.delete(welcomeMessage);
	}

}