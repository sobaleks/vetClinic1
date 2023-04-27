package service;

import domain.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DoctorRepository;
import java.util.ArrayList;
//hghghghg
@Service
public class DoctorService {

    DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(int id){

        return doctorRepository.findById(id).orElse(null);
    }

    public ArrayList<Doctor> getAllDoctors(){
        return (ArrayList<Doctor>) doctorRepository.findAll();
    }

    public Doctor createDoctor(Doctor doctor){
        return  doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(int id){
        doctorRepository.deleteById(id);
    }


}
