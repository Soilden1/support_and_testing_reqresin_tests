package dimacm14.modeles;

import lombok.Data;

@Data
public class UserListResponseModel {
    Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
    UserDataResponseModel[] data;
    UserSupportResponseModel support;
}
