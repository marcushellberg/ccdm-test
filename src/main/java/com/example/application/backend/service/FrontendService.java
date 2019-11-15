package com.example.application.backend.service;

import java.util.List;

import com.example.application.backend.entity.Contact;
import com.vaadin.flow.server.connect.VaadinService;
import com.vaadin.flow.server.connect.auth.AnonymousAllowed;

/**
 * FrontendService
 */
@VaadinService
@AnonymousAllowed
public class FrontendService {

  private ContactService contactService;

  FrontendService(ContactService contactService) {
    this.contactService = contactService;
  }

  public List<Contact> getContacts(String stringFilter) {
    return contactService.findAll(stringFilter);
  }
}