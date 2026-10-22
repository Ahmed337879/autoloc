package tn.esprit.autoloc.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int statut;

    private String message;

    private LocalDateTime horodatage;
}
