package com.scm.smart_contact_manager.services;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    // save contacts
    Contact save(Contact contact);

    // update contact
    Contact update(Contact contact);

    // get contact
    List<Contact> getAll();

    // get contact by id
    Contact getById(String id);

    // delete contact
    void delete(String id);

    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user);
    Page<Contact> searchByPhoneNumber(String phoneNo, int size, int page, String sortBy, String order,User user);

    // get contacts by user id
    List<Contact> getByUserId(String userId);

    // get contacts by user
    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);


}
