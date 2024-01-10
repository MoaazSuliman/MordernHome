package com.moaaz.modernhome.Navbar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavbarRepository extends JpaRepository<NavbarImage , Long> {
}
