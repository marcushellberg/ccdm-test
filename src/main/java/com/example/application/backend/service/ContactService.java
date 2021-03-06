package com.example.application.backend.service;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.example.application.backend.entity.ContactStatus;
import com.example.application.backend.repository.CompanyRepository;
import com.example.application.backend.repository.ContactRepository;
import com.example.application.backend.service.restimport.ImportResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContactService {
  private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
  private ContactRepository contactRepository;
  private CompanyRepository companyRepository;
  private RestTemplate restTemplate;

  public ContactService(ContactRepository contactRepository, CompanyRepository companyRepository,
      RestTemplate restTemplate) {
    this.contactRepository = contactRepository;
    this.companyRepository = companyRepository;
    this.restTemplate = restTemplate;
  }

  /**
   * @return all available Contact objects.
   */
  public List<Contact> findAll() {
    return contactRepository.findAll();
  }

  /**
   * Finds all Contact's that match given filter.
   *
   * @param stringFilter filter that returned objects should match or null/empty
   *                     string if all objects should be returned.
   * @return list a Contact objects
   */
  public List<Contact> findAll(String stringFilter) {
    if (stringFilter == null || stringFilter.isEmpty()) {
      return contactRepository.findAll();
    } else {
      return contactRepository.search(stringFilter);
    }
  }

  /**
   * @return the amount of all contacts in the system
   */
  public long count() {
    return contactRepository.count();
  }

  /**
   * Deletes a Contact from a system
   *
   * @param contact the Contact to be deleted
   */
  public void delete(Contact contact) {
    contactRepository.delete(contact);
  }

  /**
   * Persists or updates Contact in the system. Also assigns an identifier for new
   * Contact instances.
   *
   * @param contact
   */
  public void save(Contact contact) {
    if (contact == null) {
      LOGGER.log(Level.SEVERE, "Contact is null. Are you sure you have connected your form to the application?");
      return;
    }
    contactRepository.save(contact);
  }

  public void importContacts() {
    ImportResponse imported = restTemplate
        .getForEntity("https://randomuser.me/api/?inc=name,email&results=10", ImportResponse.class).getBody();

    if (imported != null && imported.getResults() != null) {
      contactRepository.saveAll(imported.getResults().stream().map(result -> {
        Contact contact = new Contact();
        contact.setFirstName(result.getName().getFirst());
        contact.setLastName(result.getName().getLast());
        contact.setEmail(result.getEmail());
        contact.setStatus(ContactStatus.ImportedLead);
        return contact;
      }).collect(Collectors.toList()));
    }
  }

  /**
   * Sample data generation
   */
  @PostConstruct
  public void populateTestData() {
    if (companyRepository.count() == 0) {
      companyRepository.saveAll(Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
          .map(Company::new).collect(Collectors.toList()));
    }

    if (contactRepository.count() == 0) {
      Random r = new Random(0);
      List<Company> companies = companyRepository.findAll();
      contactRepository.saveAll(Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen", "Koen Johansen",
          "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson", "Emily Stewart",
          "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson", "Eileen Walker", "Katelyn Martin",
          "Israel Carlsson", "Quinn Hansson", "Makena Smith", "Danielle Watson", "Leland Harris", "Gunner Karlsen",
          "Jamar Olsson", "Lara Martin", "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen",
          "Solomon Olsen", "Jaydan Jackson", "Bernard Nilsen").map(name -> {
            String[] split = name.split(" ");
            Contact contact = new Contact();
            contact.setFirstName(split[0]);
            contact.setLastName(split[1]);
            contact.setCompany(companies.get(r.nextInt(companies.size())));
            contact.setStatus(ContactStatus.values()[r.nextInt(ContactStatus.values().length)]);
            String email = (contact.getFirstName() + "." + contact.getLastName() + "@"
                + contact.getCompany().getName().replaceAll("[\\s-]", "") + ".com").toLowerCase();
            contact.setEmail(email);
            return contact;
          }).collect(Collectors.toList()));
    }
  }
}