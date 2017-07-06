package com.pusulait.airsqreen.service.security;

import com.codahale.metrics.annotation.Timed;
import com.pusulait.airsqreen.domain.co.UserCO;
import com.pusulait.airsqreen.domain.dto.security.UserDTO;
import com.pusulait.airsqreen.domain.dto.security.UserRoleDTO;
import com.pusulait.airsqreen.domain.dto.security.user.RegisterDTO;
import com.pusulait.airsqreen.domain.dto.security.user.UserViewDTO;
import com.pusulait.airsqreen.domain.security.user.User;
import com.pusulait.airsqreen.domain.security.user.UserRole;
import com.pusulait.airsqreen.repository.security.user.UserRepository;
import com.pusulait.airsqreen.repository.security.user.UserRoleRepository;
import com.pusulait.airsqreen.security.SecurityUtils;
import com.pusulait.airsqreen.service.exceptions.UserNotExistException;
import com.pusulait.airsqreen.util.RandomUtil;
import com.pusulait.airsqreen.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
@Slf4j
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserRoleRepository userRoleRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Save a user.
     *
     * @return the persisted entity
     */
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        User realUser = null;
        if(userDTO.getId()!=null) {
            realUser = userRepository.getOne(userDTO.getId());
        } else{
            realUser = new User();
        }

        User result = userRepository.save(UserDTO.toEntity(realUser,userDTO));


            List<UserRole> userRoles = userRoleRepository.findByUserId(userDTO.getId());
            List<UserRoleDTO> userRoleList = userDTO.getUserRoleList();

            if (userRoleList!=null && !userRoleList.isEmpty()) {
                for (UserRoleDTO item : userRoleList) {
                    List<UserRole>  anyRoles = userRoles.stream()
                            .filter(p -> p.getUserId() == item.getUserId() && p.getRoleId()==item.getRoleId())
                            .collect(Collectors.toList());
                    if (anyRoles == null || anyRoles.isEmpty()) {
                        userRoleRepository.save(UserRoleDTO.toEntity(new UserRole(),item));
                    }
                }
                for (UserRole item : userRoles) {
                    List<UserRoleDTO>  anyRoles = userRoleList.stream()
                            .filter(p -> p.getUserId() == item.getUserId() && p.getRoleId()==item.getRoleId())
                            .collect(Collectors.toList());
                    if (anyRoles==null || anyRoles.isEmpty()) {
                        userRoleRepository.delete(item.getId());
                    }
                }
            } else if (userRoles !=null && !userRoles.isEmpty()) {
                for (UserRole item : userRoles) {
                    userRoleRepository.delete(item.getId());
                }
            }

        return UserDTO.toDTOWithRoles(result);
    }


    /**
     * get one user by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserDTO findOne(Long id) {
        log.debug("Request to get User : {}", id);
        UserDTO userDTO = UserDTO.toDTOWithRoles(userRepository.findOne(id));
        return userDTO;
    }

    /**
     * get one user by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserDTO findOneWithRoles(Long id) {
        log.debug("Request to get User : {}", id);
        UserDTO userDTO = UserDTO.toDTOWithRoles(userRepository.findOne(id));
        return userDTO;
    }
    /**
     * delete the  user by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User currentUser = null;

        Optional<User> optional = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        if (optional != null) {
            currentUser = optional.get();
            for (UserRole userRole : currentUser.getUserRoleList()) {
                userRole.getRole().getRoleRightList().size();
            }  // eagerly load the association
        }
        return currentUser;
    }

    @Timed
    @Transactional(readOnly = true)
    public User getCurrentUser() throws  Exception {

        Optional<User> optUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        if(!optUser.isPresent()){
            throw new UsernameNotFoundException("User not found!");
        }
        return optUser.get();
    }

    @Timed
    @Transactional(readOnly = true)
    public UserViewDTO getUserById(Long friendId) throws Exception{
        User user = userRepository.findOne(friendId);
        UserViewDTO userViewDTO = new UserViewDTO();
        if (user != null) {
            userViewDTO.setId(user.getId());
            userViewDTO.setFirstname(user.getFirstname());
            userViewDTO.setLastname(user.getLastname());
            userViewDTO.setNickname(user.getNickname());


            // friend
            User userLogin = getCurrentUser();

            return userViewDTO;
        }

        throw new UserNotExistException("User does not exist!");
    }

    @Timed
    @Transactional(readOnly = true)
    public User get(Long id) throws  Exception {
        return userRepository.findOne(id);
    }
    /**
     * get all the users.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return userRepository.findAll(pageable).map(UserDTO::toDTO);

    }

    @Timed
    @Transactional(readOnly = true)
    public Page<User> getAllSearch(UserCO userCO, Pageable pageable) throws  Exception {

        Page<User> pages = null;

        if(StringUtils.isEmpty(userCO.getEmail())  && StringUtils.isEmpty(userCO.getLastname()) && StringUtils.isEmpty(userCO.getFirstname())){
            pages = userRepository.findAll(pageable);
        }else if(!StringUtils.isEmpty(userCO.getEmail())  && StringUtils.isEmpty(userCO.getLastname()) && StringUtils.isEmpty(userCO.getFirstname())){
            pages=userRepository.findUsersByEmailContaining(userCO.getEmail(),  pageable);
        } else if(StringUtils.isEmpty(userCO.getEmail())  && StringUtils.isEmpty(userCO.getLastname())  && !StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByFirstnameContaining(userCO.getFirstname(),  pageable);
        } else if(StringUtils.isEmpty(userCO.getEmail())  && !StringUtils.isEmpty(userCO.getLastname())  && StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByLastnameContaining(userCO.getLastname(), pageable);
        } else if(!StringUtils.isEmpty(userCO.getEmail())  && !StringUtils.isEmpty(userCO.getLastname())  && StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByLastnameContainingEmailContaining(userCO.getLastname(), userCO.getEmail(),  pageable);
        }else if(StringUtils.isEmpty(userCO.getEmail())  && !StringUtils.isEmpty(userCO.getLastname())  && !StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByFirstnameContainingLastnameContaining(userCO.getFirstname(),userCO.getLastname(),  pageable);
        }else if(!StringUtils.isEmpty(userCO.getEmail())  && StringUtils.isEmpty(userCO.getLastname())  && !StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByFirstnameContainingEmailContaining(userCO.getFirstname(),userCO.getEmail(), pageable);
        }else if(!StringUtils.isEmpty(userCO.getEmail())  && !StringUtils.isEmpty(userCO.getLastname())  && !StringUtils.isEmpty(userCO.getFirstname()) ){
            pages=userRepository.findUsersByFirstnameContainingLastnameContainingEmailContaining(userCO.getFirstname(),userCO.getLastname(), userCO.getEmail(), pageable);
        }


        return pages;
    }


    @Timed
    public User register(RegisterDTO registerDTO) throws  Exception  {

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(registerDTO.getPassword());
        newUser.setUsername(registerDTO.getUsername());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);

        newUser.setEmail(registerDTO.getEmail());
        newUser.setLanguage(registerDTO.getLanguage());
        newUser.setIphoneRegId(registerDTO.getIphoneRegId());
        newUser.setAndroidRegId(registerDTO.getAndroidRegId());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }




}
