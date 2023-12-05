package com.bndlvsk.spotapp.DTO;

import lombok.*;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BetaUserDTO {
    String fullName;
    String email;
    String date;
    boolean isActive;
}
