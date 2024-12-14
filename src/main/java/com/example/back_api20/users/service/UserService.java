package com.example.back_api20.users.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.back_api20.users.model.User;
import com.example.back_api20.users.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.back_api20.users.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(UserRequestDto userRequestDto) {
        // Verificar si ya existe un usuario con la misma cédula
        if (userRepository.findByCedula(userRequestDto.getCedula()).isPresent()) {
            return "La cédula ya está registrada.";
        }

        // Verificar si ya existe un usuario con el mismo correo
        if (userRepository.findByCorreo(userRequestDto.getCorreo()).isPresent()) {
            return "El correo ya está registrado.";
        }

        // Crear un nuevo usuario y mapear los datos del DTO
        User newUser = new User();
        newUser.setCedula(userRequestDto.getCedula());
        newUser.setNombre(userRequestDto.getNombre());
        newUser.setApellido(userRequestDto.getApellido());
        newUser.setCorreo(userRequestDto.getCorreo());
        newUser.setPassword(userRequestDto.getPassword());
        newUser.setCelular(userRequestDto.getCelular());
        newUser.setFechaNacimiento(userRequestDto.getFechaNacimiento());

        // Guardar el usuario en la base de datos
        userRepository.save(newUser);

        return "Usuario registrado exitosamente.";
    }

    public String authenticateUser(String correo, String password) {
        // Buscar el usuario por correo
        Optional<User> existingUser = userRepository.findByCorreo(correo);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Verificar la contraseña
            if (password.equals(user.getPassword())) {
                return "Autenticación exitosa";
            } else {
                return "Contraseña incorrecta";
            }
        } else {
            return "Usuario no encontrado";
        }
    }
}
