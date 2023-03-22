package root.quanlyktx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdating {
    private String username;
    private String oldPassword;
    private String newPassword;
}
