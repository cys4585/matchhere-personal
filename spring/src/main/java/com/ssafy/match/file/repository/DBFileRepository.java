package com.ssafy.match.file.repository;

import com.ssafy.match.file.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBFileRepository extends JpaRepository<DBFile, String> {

}