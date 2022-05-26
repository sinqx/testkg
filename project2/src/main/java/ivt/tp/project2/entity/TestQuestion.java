package ivt.tp.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "question")
    private String question;

    @Column(name = "correct_option")
    private String correctOption;

    @Column(name = "mark")
    private Long mark;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "question_options")
    private List<QuestionOption> questionOptions;
}
