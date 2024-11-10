package com.scm.smart_contact_manager.repositories;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {


    // find contacts by user
    // custom finder method
    Page<Contact> findByUser(User user, Pageable pageable);

   // List<Contact> findByUserId(String userId);

    // custom query method
//    @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId")
//    List<Contact> findByUserId(@Param("userId") String userId);

    List<Contact> findByUser_UserId(String userId);

    Page<Contact> findByUserAndNameContaining(User user,String nameKeyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user,String emailKeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumberKeyword, Pageable pageable);
}
