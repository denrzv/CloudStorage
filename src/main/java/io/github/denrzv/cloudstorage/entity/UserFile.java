package io.github.denrzv.cloudstorage.entity;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "FILES")
public class UserFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private int size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
