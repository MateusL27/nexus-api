package nexus_api.repositoryTeste;

import nexus_api.model.Usuario;
import nexus_api.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    private static final String NOME_PADRAO = "Mateus Silva";
    private static final String EMAIL_PADRAO = "mateus123@gmail.com";
    private static final String CPF_PADRAO = "55555555555";
    private static final String SENHA_PADRAO = "mateus123";

    private static final String EMAIL_INEXISTENTE = "naoexiste@gmail.com";
    private static final String CPF_INEXISTENTE = "99999999999";
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar true quando existir usuário com o CPF informado")
    public void deveRetornarTrueQuandoCPFExistir(){
        usuarioRepository.saveAndFlush(novoUsuario());

        assertTrue(usuarioRepository.existsByCpf(CPF_PADRAO));
    }

    @Test
    @DisplayName("Deve retornar false quando não existir usuário com o CPF informado")
    public void deveRetornarFalseQuandoCPFNaoExistir(){
        assertFalse(usuarioRepository.existsByCpf(CPF_INEXISTENTE));
    }

    @Test
    @DisplayName("Deve retornar true quando existir usuário com email")
    public void deveRetornarTrueQuandoEmailExistir(){
        usuarioRepository.saveAndFlush(novoUsuario());

        assertTrue(usuarioRepository.existsByEmail(EMAIL_PADRAO));
    }

    @Test
    @DisplayName("Deve retornar false quando não existir usuãrio com email")
    public void deveRetornarFalseQuandoEmailNaoExistir(){
        assertFalse(usuarioRepository.existsByEmail(EMAIL_INEXISTENTE));
    }

    @Test
    @DisplayName("Deve retornar usuário quando encontrado por email")
    public void deveRetornarUsuarioQuandoEncontradoPorEmail(){
        usuarioRepository.saveAndFlush(novoUsuario());

        Optional<Usuario> resultado = usuarioRepository.findByEmail(EMAIL_PADRAO);

        assertTrue(resultado.isPresent());
        assertEquals(EMAIL_PADRAO, resultado.get().getEmail());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando não encontrar usuário por e-mail")
    void deveRetornarVazioQuandoNaoEncontrarPorEmail() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail(EMAIL_INEXISTENTE);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar usuário quando encontrado por CPF")
    void deveRetornarUsuarioQuandoEncontradoPorCpf() {
        usuarioRepository.saveAndFlush(novoUsuario());

        Optional<Usuario> resultado = usuarioRepository.findByCpf(CPF_PADRAO);

        assertTrue(resultado.isPresent());
        assertEquals(CPF_PADRAO, resultado.get().getCpf());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando não encontrar usuário por CPF")
    void deveRetornarVazioQuandoNaoEncontrarPorCpf() {
        Optional<Usuario> resultado = usuarioRepository.findByCpf(CPF_INEXISTENTE);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar apenas usuários ativos quando buscar por ativo true")
    void deveRetornarApenasUsuariosAtivos() {
        salvarUsuarioAtivoEInativo();

        List<Usuario> ativos = usuarioRepository.findByAtivo(true);

        assertFalse(ativos.isEmpty());
        assertTrue(ativos.stream().allMatch(Usuario::isAtivo));
    }

    @Test
    @DisplayName("Deve retornar apenas usuários inativos quando buscar por ativo false")
    void deveRetornarApenasUsuariosInativos() {
        salvarUsuarioAtivoEInativo();

        List<Usuario> inativos = usuarioRepository.findByAtivo(false);

        assertFalse(inativos.isEmpty());
        assertTrue(inativos.stream().noneMatch(Usuario::isAtivo));
    }

    @Test
    @DisplayName("Deve retornar usuários ignorando maiúsculas e minúsculas no nome")
    void deveRetornarUsuariosPorNomeIgnorandoCase() {
        usuarioRepository.saveAndFlush(novoUsuario());

        List<Usuario> encontrados = usuarioRepository.findByNomeContainingIgnoreCase("mateus");

        assertFalse(encontrados.isEmpty());
        assertTrue(encontrados.stream().anyMatch(u -> EMAIL_PADRAO.equals(u.getEmail())));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum nome corresponder ao filtro")
    void deveRetornarListaVaziaQuandoNomeNaoCorresponder() {
        List<Usuario> encontrados = usuarioRepository.findByNomeContainingIgnoreCase("NomeQueNaoExisteXYZ");

        assertTrue(encontrados.isEmpty());
    }

    private void salvarUsuarioAtivoEInativo() {
        var ativo = novoUsuario();
        ativo.setAtivo(true);

        var inativo = novoUsuario();
        inativo.setNome("Usuário Inativo");
        inativo.setEmail("inativo@gmail.com");
        inativo.setCpf("11111111111");
        inativo.setAtivo(false);

        usuarioRepository.saveAndFlush(ativo);
        usuarioRepository.saveAndFlush(inativo);
    }

    private Usuario novoUsuario() {
        var usuario = new Usuario();
        usuario.setNome(NOME_PADRAO);
        usuario.setEmail(EMAIL_PADRAO);
        usuario.setCpf(CPF_PADRAO);
        usuario.setSenha(SENHA_PADRAO);
        usuario.setAtivo(true);
        return usuario;
    }
}
