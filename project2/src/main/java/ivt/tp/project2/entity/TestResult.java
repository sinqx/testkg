package ivt.tp.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "test_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_mark")
    private Long total_mark;

    @Column(name = "max_mark")
    private Long maxMark;

    @Column(name = "testing_time")
    private LocalDate creationDate;
}
