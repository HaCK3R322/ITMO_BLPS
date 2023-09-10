package com.androsov.itmo_blps.entities.resume;

import com.androsov.itmo_blps.annotations.NullOrNotBlank;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "resumes")
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // TODO: fix image - instead of data work around with links to images on file system
    @OneToOne(fetch = FetchType.LAZY) // lazy fetch type to not carry this big ass image everywhere
    @JoinColumn(name = "resume_image_id")
    @Null
    @Getter(AccessLevel.NONE) // need a custom Image getter with Optional return for the null-safety
    private Image resumeImage;

    public Optional<Image> getResumeImage() {
        return Optional.ofNullable(resumeImage);
    }

    // ================================================================================

    @NotBlank(message = "Name is required")
    @Length(min = 2, max = 50, message = "Name should be in range of 2-50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Length(min = 2, max = 50, message = "Surname should be in range of 2-50 characters")
    private String surname;

    @NotBlank(message = "Patronymic is required")
    @Length(min = 2, max = 50, message = "Patronymic should be in range of 2-50 characters")
    private String patronymic;

    @Past(message = "Date of birth should be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "City is required")
    private String city;

    @NullOrNotBlank(message = "Metro station should be null or not blank")
    private String metroStation;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // ================================================================================

    @NullOrNotBlank(message = "Desired must be null or must not be blank")
    private String desiredPosition;

    @DecimalMin(value = "0.0", message = "Salary should not be negative")
    private Double salary;

    @Pattern(regexp = "^(Full|Part-time|Shift|Rotational|Partial Remote)$", message = "Invalid employment value")
    private String employment;

    // =================================================================================

    // work experience attached to resume by FK resume_id

    // =================================================================================

    @Pattern(regexp = "^(\\w*;)*$", message = "Invalid skills format. Use semicolons to separate skills.")
    private String skills;

    // =================================================================================

    // education attached to resume by FK resume_id

    // =================================================================================

    // portfolio attached to resume by FK resume_id

    // =================================================================================
}
