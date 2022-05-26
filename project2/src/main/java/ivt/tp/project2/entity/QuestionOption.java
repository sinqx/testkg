package ivt.tp.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "question_option")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_question_id")
    private TestQuestion testQuestion;

    @Column(name = "option_text")
    private String optionText;
}
