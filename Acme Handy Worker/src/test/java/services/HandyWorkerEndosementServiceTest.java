package services;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.HandyWorker;
import domain.HandyWorkerEndorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})

@Transactional
public class HandyWorkerEndosementServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------------------------------	
			@Autowired
			private HandyWorkerEndorsementService handyWorkerEndorsementService;
			
			@Autowired
			private CustomerService customerService;			
			
			// Tests ----------------------------------------------------------------------
			
			// CREATE ---------------------------------------------------------------------
			
			@Test
			public void testCreateHandyWorkerEndorsementService(){
				HandyWorkerEndorsement handyWorkerEndorsement;
				super.authenticate("handyworker1");
				handyWorkerEndorsement = handyWorkerEndorsementService.create();
				Assert.isNull(handyWorkerEndorsement.getText());
				Assert.isNull(handyWorkerEndorsement.getCustomer());
				Assert.isNull(handyWorkerEndorsement.getHandyWorker());
				super.authenticate(null);
			}
			
			
			// SAVE -----------------------------------------------------------------------
			
			@Test 
			public void testSaveHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement, saved;
//				HandyWorker hw;
				Customer c;
				Collection<HandyWorkerEndorsement> handyWorkerEndorsements;
				super.authenticate("handyworker1");						
				handyWorkerEndorsement = handyWorkerEndorsementService.create();					
//				hw = handyWorkerService.findOne(15726);
				c = (Customer) customerService.findAll().toArray()[0];
//				
//				Date current = new Date(System.currentTimeMillis() - 1000);
//				
//				handyWorkerEndorsement.setMoment(current);
				handyWorkerEndorsement.setText("Esto es un texto de prueba");
//				handyWorkerEndorsement.setHandyWorker(hw);
				handyWorkerEndorsement.setCustomer(c);
				
				saved = handyWorkerEndorsementService.save(handyWorkerEndorsement);					

				handyWorkerEndorsements = handyWorkerEndorsementService.findAll();				

				Assert.isTrue(handyWorkerEndorsements.contains(saved));
				super.authenticate(null);
			}
			

			// UPDATE ---------------------------------------------------------------------
			
			@Test 
			public void testUpdateHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement;
				super.authenticate("handyworker2");						
				handyWorkerEndorsement = (HandyWorkerEndorsement) handyWorkerEndorsementService.findAll().toArray()[0];				
				handyWorkerEndorsement.setText("Texto de prueba 2");	

				handyWorkerEndorsementService.save(handyWorkerEndorsement);				

				super.authenticate(null);
			}
			
			// DELETE ---------------------------------------------------------------------

			@Test 
			public void testDeleteHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement;
				Collection<HandyWorkerEndorsement> handyWorkerEndorsements;
				super.authenticate("handyworker3");								

				handyWorkerEndorsement = (HandyWorkerEndorsement) handyWorkerEndorsementService.findAll().toArray()[0];			

				handyWorkerEndorsementService.delete(handyWorkerEndorsement);									
				handyWorkerEndorsements = handyWorkerEndorsementService.findAll();						
				Assert.isTrue(!handyWorkerEndorsements.contains(handyWorkerEndorsement));								
				super.authenticate(null);
			}
			
			@Test
			public void testScoreHandyWorkerEndorsements(){
				Map<HandyWorker,Double> res = handyWorkerEndorsementService.getScoreHandyWorkerEndorsement();
				Assert.notNull(res);
				//System.out.println(res);
			}

}
