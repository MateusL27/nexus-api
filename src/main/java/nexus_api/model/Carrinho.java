package nexus_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carrinhos")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"itens"})
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", nullable = false)
    @LastModifiedDate
    private LocalDateTime dataUltimaAtualizacao;

    @OneToMany(mappedBy = "carrinho", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho(Usuario usuario) {
        this.usuario = usuario;
    }
}
