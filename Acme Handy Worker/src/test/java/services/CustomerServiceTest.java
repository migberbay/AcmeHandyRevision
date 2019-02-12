package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.Customer;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class CustomerServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SocialProfileService socialProfileService;
	
	@Autowired
	private UserAccountService accountService;
	// Tests ----------------------------------------------------------------------

	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateCustomer(){
		Customer customer;
		customer = customerService.create();	
		Assert.isNull(customer.getAddress());
		Assert.isNull(customer.getEmail());
		Assert.isNull(customer.getMiddleName());
		Assert.isNull(customer.getName());
		Assert.isNull(customer.getPhone());
		Assert.isNull(customer.getPhoto());
		Assert.isNull(customer.getSurname());
		Assert.isTrue(customer.getSocialProfiles().isEmpty());
		Assert.isTrue(customer.getFixUpTasks().isEmpty());
		Assert.notNull(customer.getUserAccount());
							
		super.authenticate(null);
	}

	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveCustomer(){
		Customer customer,saved;
		Collection<Customer> customers;
		customer = customerService.create();						
		
		customer.setName("Juan");
		customer.setSurname("Serna");
		customer.setEmail("juaparser@gmail.com");
		customer.setPhone("678534953");
		customer.setAddress("Calle de la Chincheta nº10");
		customer.setMiddleName("Parra");
		customer.setPhoto("http://www.linkedIn.com");

		SocialProfile savedpr;
		SocialProfile socialProfile = socialProfileService.create();
		socialProfile.setLink("http://www.twitter.com/Juan");
		socialProfile.setNick("juaparser");
		socialProfile.setSocialNetwork("Twitter");
		savedpr = socialProfileService.save(socialProfile);
		customer.getSocialProfiles().add(savedpr);

		UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("nuevocustomer");
		userAccount.setPassword("customer12");
		UserAccount savedua = accountService.save(userAccount);
		customer.setUserAccount(savedua);

		saved = customerService.save(customer);
		
		customers = customerService.findAll();
		Assert.isTrue(customers.contains(saved));
				
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------

	@Test 
	public void testUpdateCustomer(){
		Customer customer,saved;
		Collection<Customer> customers;
		super.authenticate("customer1");						
		customer = (Customer) customerService.findAll().toArray()[0];
		customer.setName("Lucas");
		
		saved = customerService.save(customer);
		
		customers = customerService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(customers.contains(saved));

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	

}