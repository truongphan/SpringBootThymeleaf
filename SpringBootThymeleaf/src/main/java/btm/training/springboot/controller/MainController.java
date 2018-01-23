package btm.training.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import btm.training.springboot.form.PersonForm;
import btm.training.springboot.model.Person;

@Controller
public class MainController {
	
	static List<Person> persons = new ArrayList<>();
	
	static {
		persons.add(new Person("Truong", "Phan"));
		persons.add(new Person("Tru", "Le"));
	}

	@Value("${message.hello}")
	private String message;
	
	@Value("${message.error}")
	private String errorMessage;
	
	@RequestMapping(value= {"/", "/index"}, method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("message", message);
		return "index";
	}
	
	@RequestMapping(value="/personList", method=RequestMethod.GET)
	public String personList(Model model) {
		
		model.addAttribute("persons", persons);
		return "personList";
	}
	
	@RequestMapping(value={"/addPerson"}, method=RequestMethod.GET)
    public String showAddPersonPage(Model model) {
 
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
 
        return "addPerson";
    }
	
	@RequestMapping(value="/addPerson", method=RequestMethod.POST)
	public String addPerson(Model model, 
			@ModelAttribute("personForm") PersonForm personForm) {
		
		String firstName = personForm.getFirstName();
		String lastName = personForm.getLastName();
		
		
		if (firstName != null && firstName.length() > 0
				&& lastName != null && lastName.length() > 0) {
			persons.add(new Person(firstName, lastName));
			
			return "redirect:/personList";
		}
		
		model.addAttribute("errorMessage", errorMessage);
		return "addPerson";
	}
}
