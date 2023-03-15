package com.swp.g3.repository;

import com.swp.g3.entity.Customer;
import com.swp.g3.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    public Staff findOneByUsernameAndPassword(String username, String password);
    public Staff findOneByUsername(String username);
    @Query(value = "SELECT * FROM staff", nativeQuery = true)
    Page<Staff> findStaffs(Pageable pageable);
    public Staff save(Staff staff);
    public int deleteById(int id);
    public Staff findOneById(int id);
}
