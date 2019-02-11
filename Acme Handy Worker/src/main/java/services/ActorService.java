package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private BoxService boxService;

	// Simple CRUD methods -----

	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor actor) {

		Actor result;

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		// Actor oldActor = findOne(actor.getId());

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(userAccount)
				|| userAccount.getAuthorities().contains(authority));

		result = actorRepository.save(actor);
		return result;
	}

	public void delete(Actor actor) {

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(userAccount));
		
		actorRepository.delete(actor);
		
		userAccountService.delete(userAccount);
	}

	// Other business methods -----

	public Actor getByUserAccountId(UserAccount ua) {
		return actorRepository.findByUserAccountId(ua);
	}

	private void createSystemBoxes(Actor actor) {


		Box inbox, outbox, spambox, trashbox;

		inbox = boxService.create(actor);
		outbox = boxService.create(actor);
		spambox = boxService.create(actor);
		trashbox = boxService.create(actor);

		inbox.setName("inbox");
		outbox.setName("outbox");
		spambox.setName("spambox");
		trashbox.setName("trashbox");

		inbox.setSystemBox(true);
		outbox.setSystemBox(true);
		spambox.setSystemBox(true);
		trashbox.setSystemBox(true);

		boxService.save(inbox);
		boxService.save(outbox);
		boxService.save(spambox);
		boxService.save(trashbox);

	}

	public void registerHandyWorker() {

		HandyWorker handyWorker = handyWorkerService.create();

		Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);

		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		handyWorker.setUserAccount(userAccount);
	}

	public void registerSponsor() {

		Sponsor sponsor = sponsorService.create();

		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);

		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		sponsor.setUserAccount(userAccount);
	}

	public void registerCustomer() {

		Customer customer = customerService.create();

		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);

		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		customer.setUserAccount(userAccount);
	}

}