package ivt.tp.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long  userId;

    @Column(name = "test_id", nullable = false)
    private Long testId;

    @Column(name = "test_status", nullable = false)
    private Boolean testStatus;

    @Column(name = "rating")
    private Long rating;
}
