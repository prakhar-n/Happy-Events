package com.project.happyevents.EventManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    @NotBlank(message = "Error: name is mandatory")
    private String name;
    @NotBlank(message = "Error: description is mandatory")
    private String description;
    @NotBlank(message = "Error: location is mandatory")
    private String location;
    @NotBlank(message = "Error: date is mandatory")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Error: Date must be in the format DD-MM-YYYY")
    private String date;
    @NotBlank(message = "time is mandatory")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Error: Time must be in the format HH:mm (24-hour format)")
    private String time;
    private String pamphletUrl;
    private Long hostID;
    // Getters and setters
}