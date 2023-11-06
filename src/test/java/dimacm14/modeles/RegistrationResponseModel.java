package dimacm14.modeles;

import lombok.Data;

@Data
public class RegistrationResponseModel {
    String id;
    String token;
    String error;
}
