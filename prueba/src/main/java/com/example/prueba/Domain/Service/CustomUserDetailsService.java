package com.example.prueba.Domain.Service;

import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.Usuario;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioCrudRepository usuarioCrud;

    public CustomUserDetailsService(UsuarioCrudRepository usuarioCrud) {
        this.usuarioCrud = usuarioCrud;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioCrud.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getPassword())
                .authorities("ROLE_"+usuario.getRol().getNombre())
                .build();
    }
}
