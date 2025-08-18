package com.gymapp.api.controller.programa;

import com.gymapp.api.ProgramasApi;
import com.gymapp.model.Programa;
import org.springframework.http.ResponseEntity;

public class ProgramaController implements ProgramasApi {

    @Override
    public ResponseEntity<Void> programasPost(Programa programa) {
        return null;
    }

    @Override
    public ResponseEntity<Void> programasProgramaIdDelete(Long programaId) {
        return null;
    }

    @Override
    public ResponseEntity<Programa> programasProgramaIdGet(Long programaId) {
        return null;
    }

}
