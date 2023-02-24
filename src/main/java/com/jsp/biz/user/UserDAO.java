package com.jsp.biz.user;

import com.jsp.biz.DomainDAO;
import com.jsp.database.DBController;
import com.jsp.database.DBExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DomainDAO {
    private static final String USER_LIST = "select * from users";
    private static final String USER_INSERT = "insert into users(email, password, name, role) values(?, ?, ?, ?)";
    private static final String USER_UPDATE = "update users set name = ?, role = ? where id = ?";
    private static final String USER_DELETE = "delete users where id = ?";

    private static final String USER_GET = "select * from users where id = ?";


    @Override
    public List<UserVO> getList(){
        List<UserVO> users = (List<UserVO>) DBController.select(USER_LIST, getAllExtractor());
        return users;
    }

    @Override
    public  void insert(UserVO userVO){
        DBController.insert(USER_INSERT, userVO.getInsertValue());
    }

    @Override
    public  void update(UserVO userVO){
        DBController.update(USER_UPDATE, userVO.getUpdateValue());
    }

    @Override
    public void delete(UserVO userVO){
        DBController.delete(USER_DELETE, userVO.getDeleteValue());
    }

    @Override
    public UserVO findById(UserVO userVO){
        List<UserVO> users = (List<UserVO>) DBController.select(USER_GET, getAllExtractor(), userVO.getFindByIdValue());
        return users.get(0);
    }

    @Override
    public DBExtractor<UserVO> getAllExtractor(){
        return new DBExtractor<UserVO>() {
            @Override
            public List<UserVO> getList(ResultSet rs) throws SQLException {
                List<UserVO> userVOList = new ArrayList<>();

                while (rs.next()) {
                    UserVO userVO = new UserVO();
                    userVO.setId(rs.getLong("id"));
                    userVO.setEmail(rs.getString("email"));
                    userVO.setPassword(rs.getString("password"));
                    userVO.setName(rs.getString("name"));
                    userVO.setRole(rs.getString("role"));
                    userVO.setJoinDate(rs.getDate("joindate").toString());
                    userVOList.add(userVO);
                }

                return userVOList;
            }
        };
    }
}
