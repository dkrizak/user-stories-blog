package hr.project.userstoriesblog.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty(message = "First name can't be empty.")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name can't be empty.")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty(message = "Username can't be empty.")
    @Column(name = "user_name", unique = true)
    private String username;

    @NotNull
    @NotEmpty(message = "Username can't be empty.")
    private String password;
}
