package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserAccountService {

	//Managed Repository -----
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Simple CRUD methods -----
	public UserAccount create(String username, String hashedPassword, String authority){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Authority auth = new Authority();
		auth.setAuthority(authority);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		
		
		UserAccount res = new UserAccount();
		res.setAuthorities(auths);
		res.setUsername(username);
		res.setPassword(hashedPassword);
		return res;
	}
	
	public UserAccount create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.		
		UserAccount res = new UserAccount();
		res.setAuthorities(new ArrayList<Authority>());

		return res;
	}
	
	public Collection<UserAccount> findAll(){
		return userAccountRepository.findAll();
	}
	
	public UserAccount findOne(int Id){
		return userAccountRepository.findOne(Id);
	}
	
	public UserAccount save(UserAccount a){
		UserAccount saved;
		String pass = a.getPassword();
		String hashedPass;
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		hashedPass = encoder.encodePassword(pass, null);
		a.setPassword(hashedPass);
		saved = userAccountRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(UserAccount a){

		userAccountRepository.delete(a);
	}
	
	//Other business methods -----
	public UserAccount register (String username, String hashedPassword, String type){
		//type can be either CUSTOMER, HANDYWORKER or SPONSOR(select)
		UserAccount nueva = this.create(username, hashedPassword, type);
		this.save(nueva);
		return nueva;
	}
	
}