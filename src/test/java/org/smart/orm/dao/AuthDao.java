package org.smart.orm.dao;

import org.smart.orm.entities.jpa.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthDao extends JpaRepository<AuthGroup, Integer> {

}
