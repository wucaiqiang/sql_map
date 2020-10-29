package com.wu.sqlmap.mapper;

import com.wu.sqlmap.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hebelala
 */
@Repository
public interface UserRepository {

	List<UserModel> selectMatchWithNotFilterDeleted(@Param("name") String name);
}
