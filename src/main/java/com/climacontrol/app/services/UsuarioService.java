package com.climacontrol.app.services;

import com.climacontrol.app.dto.LoginDTO;
import com.climacontrol.app.dto.UsuarioCadastroDTO;
import com.climacontrol.app.dto.UsuarioResponseDTO;
import com.climacontrol.app.entities.Usuario;
import com.climacontrol.app.enums.Role;
import com.climacontrol.app.exceptions.CredenciaisInvalidasException;
import com.climacontrol.app.exceptions.EmailJaCadastradoException;
import com.climacontrol.app.exceptions.UsuarioInativoException;
import com.climacontrol.app.repositories.UsuarioRepository;
import com.climacontrol.app.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Usuario cadastrar(UsuarioCadastroDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setAtivo(true);

        usuario.setRole(Role.OPERADOR);

        return usuarioRepository.save(usuario);
    }

    public UsuarioResponseDTO login(LoginDTO dto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getSenha()
                    )
            );
        } catch (Exception e) {
            throw new CredenciaisInvalidasException();
        }

        Usuario usuario = usuarioRepository
                .findByEmail(dto.getEmail())
                .orElseThrow();

        if (!usuario.getAtivo()) {
            throw new UsuarioInativoException();
        }

        String token = jwtService.gerarToken(usuario.getEmail());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                token,
                usuario.getRole().name()
        );
    }

    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(false);

        usuarioRepository.save(usuario);
    }
}
