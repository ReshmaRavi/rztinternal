package com.razorthink.rzt.internal.management.utils;

import java.io.Serializable;

import com.razorthink.rzt.internal.management.domain.AggregateUsers;
import com.razorthink.utils.spring.repo.GenericRepository;

public interface AggregateUserRepo extends GenericRepository<AggregateUsers, Serializable>
{

}
