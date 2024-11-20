package com.example.web_project.Service.user;

import com.example.web_project.Dto.SignupDTO;
import com.example.web_project.Dto.UserDTO;
import com.example.web_project.Entity.User;
import com.example.web_project.Entity.VerificationToken;
import com.example.web_project.Repository.UserRepository;
import com.example.web_project.Repository.VerificationTokenRepository;
import com.example.web_project.Service.ResetPassword.PasswordResetTokenService;
import com.example.web_project.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final VerificationTokenRepository verificationTokenRepository;

   /* @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setUserRole(UserRole.ADMIN);
            user.setEmail("admin@test.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setTelephone("99887766");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

        }
    }*/

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setFirstname(signupDTO.getFirstname());
        user.setLastname(signupDTO.getLastname());
        user.setEmail(signupDTO.getEmail());
        user.setTelephone(signupDTO.getTelephone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        user.setUserRole(UserRole.USER);
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setFirstname(createdUser.getFirstname());
        userDTO.setLastname(createdUser.getLastname());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setTelephone(createdUser.getTelephone());
        userDTO.setUserRole(createdUser.getUserRole());

        return user;

    }

    @Override
    public User createDeliveryman(SignupDTO signupDTO) {
        User user = new User();
        user.setFirstname(signupDTO.getFirstname());
        user.setLastname(signupDTO.getLastname());
        user.setEmail(signupDTO.getEmail());
        user.setTelephone(signupDTO.getTelephone());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setUserRole(UserRole.DELIVERYMAN);
        user.setEnabled(true);
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setFirstname(createdUser.getFirstname());
        userDTO.setLastname(createdUser.getLastname());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setTelephone(createdUser.getTelephone());
        userDTO.setUserRole(createdUser.getUserRole());

        return user;
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        verificationTokenRepository.save(verificationToken);
    }
    private final VerificationTokenRepository tokenRepository;

    public VerificationToken findVerificationTokenByUser(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        VerificationToken token_user = findVerificationTokenByUser(user);
        tokenRepository.delete(token_user);
        return "valid";
    }

    private final PasswordResetTokenService passwordResetTokenService;

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setTelephone(user.getTelephone());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public User desactiverCompteuser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(false);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Utilisateur avec l'ID " + id + " non trouvé.");
        }
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);

    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public User findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token);
    }

    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        userRepository.delete(user);
    }



}



