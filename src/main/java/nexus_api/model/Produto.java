package nexus_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "produtos")
@Getter
@Setter
@ToString(exclude = {"itens"})
@NoArgsConstructor
public class Produto {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "estoque")
    private int estoque;

    @Column(name = "ativo", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean ativo;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_ultima_atualizacao", nullable = false)
    @LastModifiedDate
    private LocalDateTime dataUltimaAtualizacao;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Produto(String nome, String descricao, Categoria categoria, BigDecimal preco, String sku, int estoque, boolean ativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.sku = sku;
        this.estoque = estoque;
        this.ativo = ativo;
    }
}
