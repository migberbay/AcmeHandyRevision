package services;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import domain.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;


@Service
@Transactional
public class FinderService {

	//Managed Repository -----
	@Autowired
	private FinderRepository finderRepository;
	
	//Supporting Services -----
	
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ConfigurationService configurationService;
	
	//Constructors -----
	public FinderService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Finder create(){
		Finder result;
		result = new Finder();
		result.setFixUpTasks(new HashSet<FixUpTask>());
		return result;
	}

	
	public Collection<Finder> findAll(){
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Finder findOne(int finderId){
		Finder finder;
		finder = this.finderRepository.findOne(finderId);
		return finder;
	}
	/*This method takes into consideration that the finder may be expired
	* and act accordingly*/
	public Finder findByPrincipal(){
		Finder result;
		HandyWorker principal= handyWorkerService.findByPrincipal();
		result = findByHandyWorker(principal);
		/*In case is expired, we set all its parameters to null*/
		if(result.getMoment() == null || isVoid(result) || isExpired(result)){
			result = setAllToParametersToNullAndSave(result);
		}
		return result;
	}
	
	public Finder save(Finder finder){
		Finder result;
		Assert.notNull(finder);
		if (finder.getId() != 0) {
			Assert.isTrue(this.esDeActorActual(finder));
			/*In case the finder is empty, we empty all its parameters*/
			if(isVoid(finder)){
				finder.setMoment(null);
				finder.setFixUpTasks(new HashSet<FixUpTask>());
			/*In case finder is not empty, we filter fixUpTasks and then save the results in the finder*/
			}else{
				finder.setMoment(DateUtils.addMilliseconds(new Date(),-1));
				filterFixUpTasks(finder);
			}

		}else{
			/*Checking that the HandyWorker has not a Finder already
				(Probably not necessary with '@OneToOne(optional = false)' annotation)*/
			Assert.isNull(findByHandyWorker(finder.getHandyWorker()));
			/*Checking that all attributes but handyWorker and moment are null*/
			Assert.isTrue(isVoid(finder));
			/*Checking that moment is null, no results are cached*/
			Assert.isNull(finder.getMoment());
		}
		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}

	public Finder findByHandyWorker(HandyWorker handyWorker){
		Assert.notNull(handyWorker);
		Finder result;

		result = finderRepository.findByHandyWorker(handyWorker.getId());

		return result;
	}
	
	//Other business methods -----
	
	private Boolean esDeActorActual(final Finder finder) {
		Boolean result;

		final HandyWorker principal = this.handyWorkerService.findByPrincipal();
		final HandyWorker handyWorkerFromFinder = finderRepository.findOne(finder.getId()).getHandyWorker();

		result = principal.equals(handyWorkerFromFinder);
		return result;
	}

	public Boolean isVoid(final Finder finder){
		Boolean result;

		result = (finder.getKeyword() == null || finder.getKeyword() == "")
				&& finder.getMinPrice() == null
				&& finder.getMaxPrice() == null
				&& finder.getStartDate() == null
				&& finder.getEndDate() == null
				&& finder.getCategory() == null
				&& finder.getWarranty() == null;

		return result;
	}

	private Finder setAllToParametersToNullAndSave(Finder finder){
		Assert.isTrue(finder.getMoment() == null || isExpired(finder));
		Finder result;

		finder.setMoment(null);
		finder.setKeyword(null);
		finder.setMinPrice(null);
		finder.setMaxPrice(null);
		finder.setStartDate(null);
		finder.setEndDate(null);

		finder.setCategory(null);
		finder.setWarranty(null);
		finder.setFixUpTasks(null);

		result = save(finder);
		return result;
	}

	public Boolean isExpired(Finder finder){
		Boolean result = true;
		Configuration configuration = configurationService.find();
		Double cacheTimeInHours = configuration.getFinderCacheTime();
		Date expirationMoment =  new Date();
			/*Adding Hours*/
			expirationMoment= DateUtils.addHours(expirationMoment, cacheTimeInHours.intValue());
			/*Adding Hours*/
			expirationMoment = DateUtils.addMinutes(expirationMoment,
				Double.valueOf(60 * (cacheTimeInHours - cacheTimeInHours.intValue())).intValue());

			result = finder.getMoment().after(expirationMoment);

		return result;
	}

	/*Don't declare finder parameter as final*/
	public Finder filterFixUpTasks(Finder finder){
		String keyword;
		Double maxPrice, minPrice;
		Date startDate, endDate;
		Category category;
		Warranty warranty;
		Integer categoryId, warrantyId;

		Collection<FixUpTask> fixUpTasks;

		/*Extract finder parameters to filter fixUpTasks*/
		keyword = finder.getKeyword();

		maxPrice = finder.getMaxPrice();
		minPrice = finder.getMinPrice();

		startDate = finder.getStartDate();
		endDate = finder.getEndDate();

		category = finder.getCategory();
		warranty = finder.getWarranty();

		/*We do this to avoid a null pointer exception*/
		if (category==null){
			categoryId = null;
		}else{
			categoryId = category.getId();
		}
		if (warranty==null){
			warrantyId = null;
		}else{
			warrantyId = warranty.getId();
		}

		fixUpTasks = finderRepository.filterFixUpTasks(keyword, minPrice,
				maxPrice, startDate, endDate, categoryId, warrantyId);
		finder.setFixUpTasks(fixUpTasks);

		return finder;
	}

}