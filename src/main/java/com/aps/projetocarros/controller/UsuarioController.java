package com.aps.projetocarros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.projetocarros.model.Usuario;
import com.aps.projetocarros.service.QRCodeService;
import com.aps.projetocarros.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService servicoUsuario;
    
    @Autowired
    private QRCodeService servicoQRCode;

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(servicoUsuario.registrarUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(servicoUsuario.atualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        servicoUsuario.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoUsuario.obterUsuarioPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(servicoUsuario.listarUsuarios());
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(@RequestBody Usuario usuario) {
        servicoUsuario.alterarSenha(usuario.getId(), usuario.getSenhaAntiga(), usuario.getNovaSenha());
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> gerarQRCodeUsuario(@PathVariable Long id) {
        Usuario usuario = servicoUsuario.obterUsuarioPorId(id);
        String informacoesUsuario = String.format("Nome: %s, Email: %s", usuario.getNome(), usuario.getEmail());

        byte[] imagemQRCode = servicoQRCode.generateQRCode(informacoesUsuario, 300, 300);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imagemQRCode);
    }
}
