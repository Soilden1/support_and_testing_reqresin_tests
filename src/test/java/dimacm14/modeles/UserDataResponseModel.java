package dimacm14.modeles;

import lombok.Data;

@Data
public class UserDataResponseModel {
    Integer id;
    String email;
    String first_name;
    String last_name;
    String avatar;
}
