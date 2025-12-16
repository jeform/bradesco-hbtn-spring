package com.example.demo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        // Arrange (Preparar)
        Long id = 1L;
        Produto produtoEsperado = new Produto(id, "Notebook", 2500.00);
        
        // Simular o comportamento do repositório
        Mockito.when(produtoRepository.findById(id))
               .thenReturn(Optional.of(produtoEsperado));
        
        // Act (Agir)
        Produto produtoRetornado = produtoService.buscarPorId(id);
        
        // Assert (Verificar)
        assertNotNull(produtoRetornado);
        assertEquals(id, produtoRetornado.getId());
        assertEquals("Notebook", produtoRetornado.getNome());
        assertEquals(2500.00, produtoRetornado.getPreco());
        
        // Verificar que o método findById foi chamado exatamente uma vez
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        // Arrange (Preparar)
        Long id = 99L;
        
        // Simular que o produto não existe no repositório
        Mockito.when(produtoRepository.findById(id))
               .thenReturn(Optional.empty());
        
        // Act & Assert (Agir e Verificar)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(id);
        });
        
        // Verificar a mensagem da exceção
        assertEquals("Produto não encontrado", exception.getMessage());
        
        // Verificar que o método findById foi chamado exatamente uma vez
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(id);
    }
}
