package com.vetClinic.security;

import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.Owner;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.DoctorRepository;
import com.vetClinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final OwnerRepository ownerRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public CustomUserDetailService(OwnerRepository ownerRepository, DoctorRepository doctorRepository) {
        this.ownerRepository = ownerRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findOwnerByLogin(username).orElse(null);
        Doctor doctor = doctorRepository.findDoctorByLogin(username).orElse(null);
        if (owner != null) {
            UserDetails securityUser = org.springframework.security.core.userdetails.User
                    .builder()
                    .username(owner.getLogin())
                    .password(owner.getPassword())
                    .roles(owner.getRole())
                    .build();
            return securityUser;
        } else if (doctor != null) {
            UserDetails securityUser = org.springframework.security.core.userdetails.User
                    .builder()
                    .username(doctor.getLogin())
                    .password(doctor.getPassword())
                    .roles(doctor.getRole())
                    .build();

            return securityUser;
        } else
            throw new ObjectNotFoundException("User not found");
    }
}
