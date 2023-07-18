package com.joecjw.SpringBootProjectPractice.service.serviceImpl;

import com.joecjw.SpringBootProjectPractice.entity.*;
import com.joecjw.SpringBootProjectPractice.entity.registration.VerificationToken;
import com.joecjw.SpringBootProjectPractice.exception.*;
import com.joecjw.SpringBootProjectPractice.repository.*;
import com.joecjw.SpringBootProjectPractice.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private StudentRepository studentRepository;

    private TeacherRepository teacherRepository;

    private DepartmentRepository departmentRepository;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user, String teacherDepartment) throws UserAlreadyExistException, InvalidUserRoleException, DepartmentNotFoundException {
        Optional<User> _user = userRepository.findByEmail(user.getEmail());
        if(_user.isPresent()){
            throw new UserAlreadyExistException("User with email="+_user.get().getEmail()+" has already been created");
        }else {
            if(user instanceof Student) {
                Student student =  new Student();
                student.setFirstName(user.getFirstName());
                student.setLastName(user.getLastName());
                student.setEmail(user.getEmail());
                System.out.println(passwordEncoder.encode(user.getPassword()));
                student.setPassword(passwordEncoder.encode(user.getPassword()));

                if (user.getRoles() == null || user.getRoles().isEmpty()) {
                    Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                    student.getRoles().add(userRole);
                } else {
                    for (Role role: user.getRoles()) {
                        switch (role.getRoleName()) {
                            case ROLE_ADMIN:
                                Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role ADMIN is not found."));
                                student.getRoles().add(adminRole);

                                break;
                            case ROLE_USER:
                                Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role MODERATOR is not found."));
                                student.getRoles().add(modRole);

                                break;
                            default:
                                Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                                student.getRoles().add(userRole);
                        }
                    }
                }

                student.setProgramme(((Student)user).getProgramme());
                student.setEntryYear(((Student)user).getEntryYear());
                studentRepository.save(student);
                return student;
            }else if(user instanceof Teacher) {
                Department department = departmentRepository.findByNameIgnoreCase(teacherDepartment);
                if (department == null){
                    throw new DepartmentNotFoundException("Department with name= " + teacherDepartment + " is Not Found");
                }
                Teacher teacher = new Teacher();
                teacher.setFirstName(user.getFirstName());
                teacher.setLastName(user.getLastName());
                teacher.setEmail(user.getEmail());
                System.out.println(passwordEncoder.encode(user.getPassword()));
                teacher.setPassword(passwordEncoder.encode(user.getPassword()));

                if (user.getRoles() == null || user.getRoles().isEmpty()) {
                    Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                    teacher.getRoles().add(userRole);
                } else {
                    for (Role role: user.getRoles()) {
                        switch (role.getRoleName()) {
                            case ROLE_ADMIN:
                                Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role ADMIN is not found."));
                                teacher.getRoles().add(adminRole);

                                break;
                            case ROLE_MODERATOR:
                                Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role MODERATOR is not found."));
                                teacher.getRoles().add(modRole);

                                break;
                            default:
                                Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                                teacher.getRoles().add(userRole);
                        }
                    }
                }

                teacher.setOnboardYear(((Teacher)user).getOnboardYear());
                teacher.setDepartment(department);
                department.addTeacher(teacher);
                Teacher _teacher = teacherRepository.save(teacher);
                departmentRepository.save(department);
                return _teacher;
            }else {
                User newUser = new User();
                newUser.setFirstName(user.getFirstName());
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setEmail(user.getEmail());
                System.out.println(passwordEncoder.encode(user.getPassword()));
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));

                if (user.getRoles() == null || user.getRoles().isEmpty()) {
                    Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                            .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                    newUser.getRoles().add(userRole);
                } else {
                    for (Role role: user.getRoles()) {
                        switch (role.getRoleName()) {
                            case ROLE_ADMIN:
                                Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role ADMIN is not found."));
                                newUser.getRoles().add(adminRole);

                                break;
                            case ROLE_MODERATOR:
                                Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role MODERATOR is not found."));
                                newUser.getRoles().add(modRole);

                                break;
                            default:
                                Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new InvalidUserRoleException("Error: Role USER is not found."));
                                newUser.getRoles().add(userRole);
                        }
                    }
                }
                newUser.getRoles().forEach(role -> System.out.println("Roles: "+role.getRoleName()));
                return userRepository.save(newUser);
            }
        }
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        user.setVerificationToken(verificationToken);
        userRepository.save(user);
    }

    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isPresent()){
            User user = verificationToken.get().getUser();
            Calendar calendar = Calendar.getInstance();
            if(verificationToken.get().getExpirationTime().getTime()
                    - calendar.getTime().getTime() <= 0){
                verificationTokenRepository.delete(verificationToken.get());
                return "expired";
            }else {
                user.setActive(true);
                userRepository.save(user);
                return "valid";
            }
        }else{
            return "invalid";
        }
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) throws VerificationTokenNotFoundException {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(oldToken);
        if(verificationToken.isPresent()){
            verificationToken.get().setToken(UUID.randomUUID().toString());
            verificationTokenRepository.save(verificationToken.get());
            return verificationToken.get();
        }else {
            throw new VerificationTokenNotFoundException("VerificationToken entity with old token="+oldToken+" Not Found");
        }
    }

}
