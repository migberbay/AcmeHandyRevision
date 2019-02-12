package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.WelcomeMessage;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class WelcomeMessageServiceTest extends AbstractTest {

	@Autowired
	private WelcomeMessageService welcomeMessageService;

	@Test
	public void testCreate() {

		authenticate("admin");

		WelcomeMessage welcomeMessage;

		welcomeMessage = welcomeMessageService.create();
		
		Assert.isNull(welcomeMessage.getText());
		Assert.isNull(welcomeMessage.getLenguajeCode());

		unauthenticate();
	}

	@Test
	public void testSave() {

		authenticate("admin");

		WelcomeMessage welcomeMessage = new WelcomeMessage();

		WelcomeMessage result;

		welcomeMessage.setText("Text");
		welcomeMessage.setLenguajeCode("lenguaje code");

		result = welcomeMessageService.save(welcomeMessage);

		Assert.isTrue(welcomeMessageService.findAll().contains(result));

		unauthenticate();
	}

	// UPDATE
	// ---------------------------------------------------------------------

	@Test
	public void testUpdate() {

		authenticate("admin");

		WelcomeMessage welcomeMessage = (WelcomeMessage) welcomeMessageService
				.findAll().toArray()[0];

		welcomeMessage.setText("Texto modificado");
		welcomeMessage.setLenguajeCode("Lenguaje modificado");

		WelcomeMessage result = welcomeMessageService.save(welcomeMessage);

		Assert.isTrue(welcomeMessageService.findAll().contains(result));

		unauthenticate();
	}

	// DELETE
	// ---------------------------------------------------------------------

	@Test
	public void testDelete() {

		authenticate("admin");

		WelcomeMessage welcomeMessage = (WelcomeMessage) welcomeMessageService
				.findAll().toArray()[0];

		welcomeMessageService.delete(welcomeMessage);

		Assert.isTrue(!welcomeMessageService.findAll().contains(welcomeMessage));

		unauthenticate();
	}
}
