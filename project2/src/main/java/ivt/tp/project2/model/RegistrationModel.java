package ivt.tp.project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationModel {
    String login;
    String password;
    String email;
    String gender;
}
