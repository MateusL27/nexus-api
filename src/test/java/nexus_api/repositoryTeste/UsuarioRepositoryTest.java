package nexus_api.repositoryTeste;

import nexus_api.model.Usuario;
import nexus_api.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    private static final String CPF_PADRAO = "55555555555";
    private static final String EMAIL_PADRAO = "mateus123@gmail.com";

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar true quando existir usuário com o CPF informado")
    public void deveRetornarTrueQuandoCPFExistir(){
        usuarioRepository.saveAndFlush(novoUsuario());

        boolean existe = usuarioRepository.existsByCpf(CPF_PADRAO);

        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar false quando não existir usuário com o CPF informado")
    public void deveRetornarFalseQuandoCPFNaoExistir(){
        boolean existe = usuarioRepository.existsByCpf("99999999999");

        assertFalse(existe);
    }

    private Usuario novoUsuario(){
        var usuario = new Usuario();
        usuario.setNome("Mateus");
        usuario.setEmail(EMAIL_PADRAO);
        usuario.setCpf(CPF_PADRAO);
        usuario.setSenha("mateus123");
        return usuario;
    }
}
