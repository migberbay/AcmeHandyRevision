package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Administrator;
import domain.Customer;
import domain.FixUpTask;
import domain.SocialProfile;


@Service
@Transactional
public class CustomerService {

	//Managed Repository -----
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	public Customer create(){

		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		Customer customer = new Customer();
		customer.setUserAccount(user);
		customer.setSocialProfiles(new ArrayList<SocialProfile>());
		customer.setFixUpTasks(new ArrayList<FixUpTask>());
		customer.setIsBanned(false);
		customer.setIsSuspicious(false);
		
		return customer;
	}
	
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Customer findOne(int Id){
		return customerRepository.findOne(Id);
	}
	
	public Customer save(Customer c){
//		Collection<Customer> customers;
//		System.out.println("entramos a save customer");
//		if(c.getId()!=0){
//			System.out.println("entra aqui?");
//			UserAccount userAccount = LoginService.getPrincipal();
//			Assert.isTrue(userAccount.equals(c.getUserAccount()));
//		}
//		Customer saved;
//		System.out.println("por aqui sigue");
//		if(c.getId()==0){
//			UserAccount ua = LoginService.getPrincipal();
//			UserAccount uasaved = userAccountService.save(ua);
//			System.out.println("UserAccount Guardada: "+uasaved + " " +uasaved.getUsername());
//			System.out.println("Existe en todas: "+ userAccountService.findAll().contains(uasaved));
//			c.setUserAccount(uasaved);
//		}
//		System.out.println("no entiendo nada");
//		saved = customerRepository.saveAndFlush(c);
//		customers = customerRepository.findAll();
//		Assert.isTrue(customers.contains(saved));
		
		Customer result;

//		Assert.isTrue(LoginService.hasRole("ADMIN"));

		result = customerRepository.saveAndFlush(c);
		return result;
		
	}
/*	
	public void delete(Customer a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerRepository.delete(a);
	}*/
	
	//Other business methods -----
	
	public Customer findByUserAccountId(Integer Id){
		Customer c;
		c = customerRepository.findByUserAccountId(Id);
		return c;
	}
	
	public Collection<Customer> TopThreeInComplaints(){
//		List<Customer> top = new ArrayList<>();
//		Map<Customer, Integer> numberofcomplaints = new HashMap<>();
//		
//		for (Customer cu : this.findAll()) {
//			numberofcomplaints.put(cu, 0);
//		}
//		
//		for (Complaint c : complaintService.findAll()) {
//			numberofcomplaints.put(c.getCustomer(), numberofcomplaints.get(c.getCustomer())+1);
//		}
//		
//		for (Entry<Customer, Integer> entry : numberofcomplaints.entrySet()) {
//			
//			
//		}
		Collection<Customer> top = customerRepository.topThreeInComplaints();
		
		

		return top;
	}
	
	public Collection<Customer> getCustomersWMoreTasksThanAvg(){
		Collection<Customer> res;
		res = customerRepository.getCustomersWMoreTasksThanAvg();
		return res;
	}
	
}