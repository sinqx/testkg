package ivt.tp.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creatorUser;

    @OneToOne
    @JoinColumn(name = "main_photo_id")
    private Photo mainPhoto;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @OneToMany
    @JoinColumn(name = "test_question")
    private List<TestQuestion> testQuestion;
}
