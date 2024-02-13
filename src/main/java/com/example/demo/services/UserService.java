package com.example.demo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.JWT.CustomerUserDetailsService;
import com.example.demo.JWT.JwtFilter;
import com.example.demo.JWT.JwtUtil;
import com.example.demo.constants.EvaeBackendConstants;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.EmailUtils;
import com.example.demo.utils.BackendUtils;
import com.google.common.base.Strings;
import com.example.demo.DTO.UserDTO;

@Service
public class UserService {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerUserDetailsService customerUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	private UserRepository userrepos;

	public UserService(AuthenticationManager authenticationManager, CustomerUserDetailsService customerUserDetailsService, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.customerUserDetailsService = customerUserDetailsService;
		this.jwtUtil = jwtUtil;
	}

	public ResponseEntity<String> inscrireUtilisateur(Map<String, String> requestMap) {
		System.out.println("Request Map: " + requestMap);
		System.out.println("Validation Result: " + validateSignUpMap(requestMap));
		try {
			if (validateSignUpMap(requestMap)) {

				User user = userrepos.findByEmail(requestMap.get("email"));
				if (Objects.isNull(user)) {
					userrepos.save(getUserFromMap(requestMap));
					return BackendUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
				} else {
					return BackendUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
				}
			} else {
				return BackendUtils.getResponseEntity(EvaeBackendConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactnumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password") && requestMap.containsKey("adresse")
				&& requestMap.containsKey("prenom") && requestMap.containsKey("role")
				&& requestMap.containsKey("prenom")) {
			if (requestMap.get("name") != "" && !TestService.isInteger(requestMap.get("name"))
					&& !TestService.isStartWithNumber(requestMap.get("name")) && requestMap.get("email") != ""
					&& !TestService.isInteger(requestMap.get("email")) && requestMap.get("adresse") != ""
					&& !TestService.isInteger(requestMap.get("adresse")) && requestMap.get("prenom") != ""
					&& !TestService.isInteger(requestMap.get("prenom"))
					&& !TestService.isStartWithNumber(requestMap.get("prenom")) && requestMap.get("role") != ""
					&& !TestService.isInteger(requestMap.get("role"))
					&& !TestService.isStartWithNumber(requestMap.get("role"))) {
				return true;
			} else {
				return false;
			}

		}
		return false;
	}

	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		Role role = Role.valueOf(requestMap.get("role"));
		user.setId(Integer.parseInt(requestMap.get("id")));
		user.setName(requestMap.get("name"));
		user.setPrenom(requestMap.get("prenom"));
		user.setContactnumber(requestMap.get("contactnumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setAdresse(requestMap.get("adresse"));
		user.setDateInscription(LocalDate.now());
		user.setRole(role);
		return user;
	}

	public ResponseEntity<String> login(Map<String, String> requestMap) {
		System.out.println("Inside login");
		try {
			if (!requestMap.containsKey("email") || !requestMap.containsKey("password")) {
				return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}",
						HttpStatus.BAD_REQUEST);
			} else {
				Authentication auth = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));

				if (auth.isAuthenticated()) {
					return new ResponseEntity<String>(
							"{\"token\":\""
									+ jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
											customerUserDetailsService.getUserDetails().getRole().toString())
									+ "\"}",
							HttpStatus.OK);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> checkToken() {
		return BackendUtils.getResponseEntity("true", HttpStatus.OK);
	}

	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			User userObj = userrepos.findByEmail(jwtFilter.getCurrentuser());
			if (!userObj.equals(null)) {
				if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setPassword(requestMap.get("newPassword"));
					userrepos.save(userObj);
					emailUtils.sendSimpleMessage(jwtFilter.getCurrentuser(), "Password changed",
							"Account password is changed.", null);
					return BackendUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
				}
				return BackendUtils.getResponseEntity("Incoreect Old Password", HttpStatus.BAD_REQUEST);
			}
			return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG,
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			User userObj = userrepos.findByEmail(requestMap.get("email"));
			if (!Objects.isNull(userObj) && !Strings.isNullOrEmpty(userObj.getEmail())) {
				emailUtils.forgotPasswordMail(userObj.getEmail(), "Your Login Credentials for Etron Management System",
						userObj.getPassword());
			}
			return BackendUtils.getResponseEntity("Check your mail box...", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<List<UserDTO>> getUsers() {
		try {
			if (jwtFilter.isAdmin()) {
				List<User> users = userrepos.findAll();
				List<UserDTO> userDTOs = new ArrayList<>();

				for (User user : users) {
					UserDTO userDTO = new UserDTO();

					// Map user data to the DTO
					userDTO.setIdUser(user.getId());
					userDTO.setName(user.getName());
					userDTO.setPrenom(user.getPrenom());
					userDTO.setEmail(user.getEmail());
					userDTO.setContactnumber(user.getContactnumber());
					userDTO.setAdresse(user.getAdresse());
					userDTO.setRole(user.getRole());
					userDTO.setDateInscription(user.getDateInscription());
					userDTO.setPassword(user.getPassword());

					userDTOs.add(userDTO);
				}

				return new ResponseEntity<>(userDTOs, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			System.out.println(requestMap);
			if (jwtFilter.isAdmin()) {
				if (requestMap.containsKey("name") && requestMap.containsKey("contactnumber")
						&& requestMap.containsKey("email") && requestMap.containsKey("password")
						&& requestMap.containsKey("adresse") && requestMap.containsKey("prenom")
						&& requestMap.containsKey("role") && requestMap.containsKey("prenom")) {
					if (!requestMap.get("name").isEmpty() && !requestMap.get("prenom").isEmpty()
							&& !requestMap.get("adresse").isEmpty() && !requestMap.get("contactnumber").isEmpty()
							&& !requestMap.get("role").isEmpty() && !requestMap.get("modeleVoiture").isEmpty()
							&& !requestMap.get("email").isEmpty() && !requestMap.get("password").isEmpty()) {
						if (!TestService.isInteger(requestMap.get("name"))
								&& !TestService.isInteger(requestMap.get("prenom"))
								&& !TestService.isInteger(requestMap.get("adresse"))
								&& !TestService.isInteger(requestMap.get("role"))
								&& !TestService.isInteger(requestMap.get("modeleVoiture"))
								&& !TestService.isInteger(requestMap.get("email"))) {
							User user = userrepos.findByEmail(requestMap.get("email"));
							if (user != null) {
								Role role = Role.valueOf(requestMap.get("role"));
								user.setName(requestMap.get("name"));
								user.setPrenom(requestMap.get("prenom"));
								user.setAdresse(requestMap.get("adresse"));
								user.setContactnumber(requestMap.get("contactnumber"));
								user.setRole(role);
								user.setEmail(requestMap.get("email"));
								user.setPassword(requestMap.get("password"));
								// user.setDateInscription(LocalDate.parse(requestMap.get("dateInscritpion")));
								userrepos.save(user);
								return BackendUtils.getResponseEntity(EvaeBackendConstants.USER_STATUS, HttpStatus.OK);
							} else {
								return BackendUtils.getResponseEntity("User id doesn't exist.", HttpStatus.OK);
							}
						} else {
							return BackendUtils.getResponseEntity("Type is Not String , Please give a String Value",
									HttpStatus.BAD_REQUEST);
						}
					} else {
						return BackendUtils.getResponseEntity("Argument Value Missing", HttpStatus.BAD_REQUEST);
					}
				} else {
					return BackendUtils.getResponseEntity("Argument Missing", HttpStatus.BAD_REQUEST);
				}
			} else {
				return BackendUtils.getResponseEntity(EvaeBackendConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> deleteUser(int idUser) {
		try {
			if (jwtFilter.isAdmin()) {
				if (TestService.intPositifNegatif(idUser)) {
					User user = userrepos.findById(idUser);
					userrepos.delete(user);
					return BackendUtils.getResponseEntity("User Deleted Successfully", HttpStatus.OK);
				} else {
					return BackendUtils.getResponseEntity(
							"Negative or Zero idUser Not Supported , Please Give a Positive idUser",
							HttpStatus.BAD_REQUEST);
				}

			} else {
				return BackendUtils.getResponseEntity(EvaeBackendConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BackendUtils.getResponseEntity(EvaeBackendConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<UserDTO> getUserByToken() {
		try {
			User user = userrepos.findByEmail(jwtFilter.getCurrentuser());

			UserDTO userDTO = new UserDTO();

			userDTO.setIdUser(user.getId());
			userDTO.setName(user.getName());
			userDTO.setPrenom(user.getPrenom());
			userDTO.setEmail(user.getEmail());
			userDTO.setContactnumber(user.getContactnumber());
			userDTO.setAdresse(user.getAdresse());
			userDTO.setRole(user.getRole());
			userDTO.setDateInscription(user.getDateInscription());
			userDTO.setPassword(user.getPassword());


			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new UserDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
