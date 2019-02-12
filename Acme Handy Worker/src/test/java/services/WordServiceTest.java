package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Word;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class WordServiceTest extends AbstractTest{

	// Service under test
	
	@Autowired
	private WordService wordService;
	
	// Test
	
	@Test
	public void testCreate(){
		
		super.authenticate("admin");
		
		Word res = wordService.create();
		
		Assert.isNull(res.getContent());
		Assert.isNull(res.getType());
		
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("admin");
		
		Word res = wordService.create();
		
		res.setContent("Ubisoft");
		res.setType("NEGATIVE");
		
		
		Word saved = wordService.save(res);
		Assert.isTrue(wordService.findAll().contains(saved));
		
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		
		super.authenticate("admin");
		
		Word res = (Word) wordService.findAll().toArray()[0];
		
		wordService.delete(res);
		Assert.isTrue(!wordService.findAll().contains(res));
		
		super.authenticate(null);
	}
	
	@Test
	public void testFindSpam(){
		
		Collection<Word> res = wordService.findSpamWords();
		
		Assert.notNull(res);
		Assert.notEmpty(res);
	}
	
	@Test
	public void testFindPositive(){
		
		Collection<Word> res = wordService.findPositiveWords();
		
		Assert.notNull(res);
		Assert.notEmpty(res);
	}
	
	@Test
	public void testFindNegative(){
		
		Collection<Word> res = wordService.findNegativeWords();
		
		Assert.notNull(res);
		Assert.notEmpty(res);
	}
}
