package com.example.demo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        // Arrange (Preparar)
        Long id = 1L;
        Usuario usuarioEsperado = new Usuario(id, "João Silva", "joao.silva@email.com");
        
        // Simular o comportamento do repositório
        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuarioEsperado));
        
        // Act (Agir)
        Usuario usuarioRetornado = usuarioService.buscarUsuarioPorId(id);
        
        // Assert (Verificar)
        assertNotNull(usuarioRetornado);
        assertEquals(id, usuarioRetornado.getId());
        assertEquals("João Silva", usuarioRetornado.getNome());
        assertEquals("joao.silva@email.com", usuarioRetornado.getEmail());
        
        // Verificar que o método findById foi chamado exatamente uma vez
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        // Arrange (Preparar)
        Long id = 99L;
        
        // Simular que o usuário não existe no repositório
        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());
        
        // Act & Assert (Agir e Verificar)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(id);
        });
        
        // Verificar a mensagem da exceção
        assertEquals("Usuário não encontrado", exception.getMessage());
        
        // Verificar que o método findById foi chamado exatamente uma vez
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        // Arrange (Preparar)
        Usuario usuarioParaSalvar = new Usuario(null, "Maria Santos", "maria.santos@email.com");
        Usuario usuarioSalvo = new Usuario(1L, "Maria Santos", "maria.santos@email.com");
        
        // Simular o comportamento do repositório ao salvar
        when(usuarioRepository.save(usuarioParaSalvar))
                .thenReturn(usuarioSalvo);
        
        // Act (Agir)
        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);
        
        // Assert (Verificar)
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId());
        assertEquals("Maria Santos", resultado.getNome());
        assertEquals("maria.santos@email.com", resultado.getEmail());
        
        // Verificar que o método save foi chamado exatamente uma vez
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }
}
