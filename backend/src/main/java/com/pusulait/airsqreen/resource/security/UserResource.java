package com.pusulait.airsqreen.resource.security;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.config.constants.Constants;
import com.pusulait.airsqreen.domain.co.UserCO;
import com.pusulait.airsqreen.domain.dto.ErrorDTO;
import com.pusulait.airsqreen.domain.dto.SystemErrorDTO;
import com.pusulait.airsqreen.domain.dto.security.UserDTO;
import com.pusulait.airsqreen.domain.dto.security.user.AccountDTO;
import com.pusulait.airsqreen.domain.dto.security.user.RegisterDTO;
import com.pusulait.airsqreen.domain.enums.ErrorType;
import com.pusulait.airsqreen.domain.security.user.RoleRight;
import com.pusulait.airsqreen.domain.security.user.User;
import com.pusulait.airsqreen.domain.security.user.UserRole;
import com.pusulait.airsqreen.repository.security.user.UserRepository;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.SystemErrorService;
import com.pusulait.airsqreen.service.security.UserService;
import com.pusulait.airsqreen.service.thirdparty.mail.MailJetHtmlEmailService;
import com.pusulait.airsqreen.util.HeaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@Slf4j
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailJetHtmlEmailService mailService;

    @Autowired
    private SystemErrorService systemErrorService;


    /**
     * POST  /countries -> Create a new user.
     */
    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if (userDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "idexists",
                    "A new user cannot already have an ID")).body(null);
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.created(new URI(Constants.API_URL_USER + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("user", result.getId().toString()))
                .body(result);
    }


    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_ACCOUNT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccount() {

        HashSet<String> roles = new HashSet<String>();
        User user = userService.getUserWithAuthorities();

        for (UserRole userRole : user.getUserRoleList()) {
            for (RoleRight roleRight : userRole.getRole().getRoleRightList()) {
                roles.add(roleRight.getRight().getName());
            }
        }

        /*
        HashMap<UserType, Long> profiles = new HashMap<UserType, Long>();
        for (UserUserProfile uup : userUserProfileRepository.findByUserId(user.getId())) {
            profiles.put(uup.getUserProfile().getUserType(), uup.getUserProfile().getId());
        }
*/


        return Optional.ofNullable(user)
                .map(newUser -> {
                    return new ResponseEntity<>(
                            new AccountDTO(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getActivated(), user.getNickname(), new ArrayList<String>(roles)),
                            HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /* admin */
    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getAllUsers(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<UserDTO> users = userService.findAll(pageable);
        return new ResponseEntity<>(assembler.toResource(users), HttpStatus.OK);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER + Constants.URL_SEARCH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSearch(@RequestParam(value="email", required=false) String email,@RequestParam(value="firstname", required=false) String firstname, @RequestParam(value="lastname", required=false) String lastname, Pageable pageable, PagedResourcesAssembler assembler) {
        log.debug("REST request to get getAllSearch");
        try{
            UserCO userCO = new UserCO();
            userCO.setLastname(lastname);
            userCO.setEmail(email);
            userCO.setFirstname(firstname);

            return new ResponseEntity<>(assembler.toResource(userService.getAllSearch(userCO, pageable)), HttpStatus.OK);
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ErrorType.ALL_USER_SEARCH, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("userService.getAllSearch",ex.getMessage()),HttpStatus.CONFLICT);
        }
    }


    @Timed
    @ResponseBody
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER+"/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        try{
            UserDTO user = userService.findOneWithRoles(id);
            return Optional.ofNullable(user)
                    .map(result -> new ResponseEntity<>(
                            result,
                            HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex){
            systemErrorService.save(new SystemErrorDTO(ErrorType.GET_USER, SecurityUtils.getCurrentLogin()));
            return new ResponseEntity<>(new ErrorDTO("userService.get", ex.getMessage()), HttpStatus.CONFLICT);
        }
    }


    @Timed
    @RequestMapping(value = Constants.URL_ADMIN + Constants.URL_USER+"/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO, @PathVariable Long id) throws Exception {

        log.debug("REST request to update User : {}", userDTO);
        if (userDTO.getId() == null) {
            return createUser(userDTO);
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("user", userDTO.getId().toString()))
                .body(result);

       // return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);

    }


    /**
     * POST  /register : register the user.
     *
     * @param registerDTO
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or email is already in use
     */
    @Timed
    @RequestMapping(value = Constants.URL_V1 + "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {

        return userRepository.findOneByUsername(registerDTO.getUsername().toLowerCase())
                .map(user -> new ResponseEntity<>(new ErrorDTO("User Exist", "login already in use" ),HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(registerDTO.getEmail())
                        .map(user -> new ResponseEntity<>(new ErrorDTO("User Exist", "email address already in use"), HttpStatus.BAD_REQUEST))
                        .orElseGet(() -> {
                            try{
                                User user = userService.register(registerDTO);
                                mailService.sendActivationEmail(user);
                                return new ResponseEntity<>(HttpStatus.CREATED);
                            } catch (Exception ex){
                                systemErrorService.save(new SystemErrorDTO(ErrorType.REGISTER, Constants.ANONYMOUS_ACCOUNT ));
                                return new ResponseEntity<>(new ErrorDTO("userService.register", ex.getMessage()), HttpStatus.CONFLICT);
                            }
                        })
                );
    }




}
