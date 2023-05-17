package com.vetClinic.authorization;

import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.Owner;
import com.vetClinic.exeptions.ForbiddenContentException;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserAccess {

    UserAuthorization userAuthorization;

    DoctorRepository doctorRepository;

    public UserAccess(UserAuthorization userAuthorization, DoctorRepository doctorRepository) {
        this.userAuthorization = userAuthorization;
        this.doctorRepository = doctorRepository;
    }

    public void allUserAuthorization(int id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner user = userAuthorization.findUserOrAdminByLogin(login).orElseThrow(
                () -> new ObjectNotFoundException("Don't find user in secure context"));
        if (!Objects.equals(user.getRole(), "ADMIN")) {
            if (!Objects.equals(user.getRole(), "DOCTOR")) {
                if (!user.getId().equals(id)) {
                    throw new ForbiddenContentException("You are not this user");
                }
            }
        }
    }

    public void doctorAuthorization(int id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorRepository.findDoctorByLogin(login).orElseThrow(
                () -> new ObjectNotFoundException("Don't find user in secure context"));
        if (!Objects.equals(doctor.getRole(), "ADMIN")) {
            if (!doctor.getId().equals(id)) {
                throw new ForbiddenContentException("You are not this user");
            }
        }
    }

    public void adminAuthorization() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorRepository.findDoctorByLogin(login).orElseThrow(
                () -> new ObjectNotFoundException("Don't find user in secure context"));
        if (!Objects.equals(doctor.getRole(), "ADMIN")) {
            throw new ForbiddenContentException("You are not this user");
        }
    }

    public void adminOrDoctorAuthorization() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Doctor doctor = doctorRepository.findDoctorByLogin(login).orElseThrow(
                () -> new ObjectNotFoundException("Don't find user in secure context"));
        if (!Objects.equals(doctor.getRole(), "ADMIN")) {
            if (!Objects.equals(doctor.getRole(), "DOCTOR")) {
                throw new ForbiddenContentException("You are not this user");
            }
        }
    }

    public void adminOrUserAuthorization(int id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Owner user = userAuthorization.findUserOrAdminByLogin(login).orElseThrow(
                () -> new ObjectNotFoundException("Don't find user in secure context"));
        if (!Objects.equals(user.getRole(), "ADMIN")) {
            if (!user.getId().equals(id)) {
                throw new ForbiddenContentException("You are not this user");
            }
        }
    }
}
