package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.Contact;
import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.ContactForm;
import com.scm.smart_contact_manager.forms.ContactSearchForm;
import com.scm.smart_contact_manager.helper.AppConstants;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.services.ContactService;
import com.scm.smart_contact_manager.services.ImageService;
import com.scm.smart_contact_manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    // add contact page : handler
    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm = new ContactForm();
//        contactForm.setName("Anand Pratap Singh");
//        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
                              Authentication authentication, HttpSession httpSession){
        // process the form data

        // validate form
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));

            httpSession.setAttribute("message", Message.builder()
                    .content("Please correct the errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form -> contact
        User user = userService.getUserByEmail(username);

        // process contact image
        // upload image

       // logger.info("file information: {}", contactForm.getContactImage().getOriginalFilename());

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);

            contact.setPicture(fileUrl);
            contact.setCloudinaryImagePublicId(fileName);
        }

        contactService.save(contact);

        System.out.println(contactForm);


        // set message to be visible on the view
        httpSession.setAttribute("message", Message.builder()
                .content("You have successfully saved the contact")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/add";
    }


    // view contacts
    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                               @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                               @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication){

        // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
                                @RequestParam(value = "page",defaultValue = "0") int page,
                                @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
                                @RequestParam(value = "direction",defaultValue = "asc") String direction,
                                Model model, Authentication authentication){

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
             pageContact = contactService.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phoneNumber")){
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }


    // delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId,
                                HttpSession session){

        contactService.delete(contactId);
        logger.info("contactId {} deleted",contactId);

        session.setAttribute("message",Message.builder()
                .content("Content is deleted successfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts";
    }

    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId,
                                Model model){

        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm",contactForm);
        model.addAttribute("contactId",contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value="/update/{contactId}",method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "user/update_contact_view";
        }

        // update contact
        var con = contactService.getById(contactId);

        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        //con.setPicture(contactForm.getPicture());

        // process image
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        }
        else {
            logger.info("file is empty");
        }

        var updatedContact = contactService.update(con);

        logger.info("Updated contact: {}",updatedContact);
        model.addAttribute("message",Message.builder().content("Contact updated").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}
