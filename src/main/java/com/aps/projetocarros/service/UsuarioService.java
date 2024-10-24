package com.aps.projetocarros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aps.projetocarros.model.Usuario;
import com.aps.projetocarros.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private EmailService servicoEmail;

    @Autowired
    private PasswordEncoder codificadorSenha;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setSenha(codificadorSenha.encode(usuario.getSenha()));
        Usuario usuarioSalvo = repositorioUsuario.save(usuario);

        servicoEmail.sendConfirmationEmail(usuario.getEmail(), "Bem-vindo ao Sistema de Carros",
                "Olá " + usuario.getNome() + ",\n\nSeu cadastro foi realizado com sucesso!");

        return usuarioSalvo;
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = repositorioUsuario.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());

        usuarioExistente.setSenha(usuario.getSenha());
        return repositorioUsuario.save(usuarioExistente);
    }

    public void removerUsuario(Long id) {
        repositorioUsuario.deleteById(id);
    }

    public List<Usuario> listarUsuarios() {
        return repositorioUsuario.findAll();
    }

    public Usuario obterUsuarioPorId(Long id) {
        return repositorioUsuario.findById(id).orElseThrow();
    }

    public void alterarSenha(Long usuarioId, String senhaAntiga, String novaSenha) {
        Usuario usuario = repositorioUsuario.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!codificadorSenha.matches(senhaAntiga, usuario.getSenha())) {
            throw new RuntimeException("Senha antiga incorreta");
        }

        usuario.setSenha(codificadorSenha.encode(novaSenha));
        repositorioUsuario.save(usuario);
    }
}
