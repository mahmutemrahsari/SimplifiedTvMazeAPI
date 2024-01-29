package com.sari.app.simplifiedtvmaze.Repository;

import com.sari.app.simplifiedtvmaze.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// gir tilgang til mange innebygde metoder som grunnleggende databaseoperasjoner,
// for eksempel å lagre, hente, oppdatere og slette show-objekter.
@Repository
public interface ShowRepository extends JpaRepository<Show,Long> {

}
