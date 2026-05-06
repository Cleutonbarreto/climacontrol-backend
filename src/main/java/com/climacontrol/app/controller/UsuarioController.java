package com.climacontrol.app.controller;

import com.climacontrol.app.dto.LoginDTO;
import com.climacontrol.app.dto.UsuarioCadastroDTO;
import com.climacontrol.app.dto.UsuarioResponseDTO;
import com.climacontrol.app.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid UsuarioCadastroDTO dto) {
        usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @RequestBody @Valid LoginDTO dto) {

        UsuarioResponseDTO response = usuarioService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
