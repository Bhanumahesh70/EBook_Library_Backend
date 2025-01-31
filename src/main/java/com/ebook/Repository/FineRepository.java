package com.ebook.Repository;

import com.ebook.domain.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FineRepository  extends JpaRepository<Fine,Long> {
}
