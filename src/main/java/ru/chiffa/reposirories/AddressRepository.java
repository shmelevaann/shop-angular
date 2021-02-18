package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chiffa.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
