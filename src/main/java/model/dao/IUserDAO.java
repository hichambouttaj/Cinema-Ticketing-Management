package model.dao;

import model.entity.User;

public interface IUserDAO extends IDAO<User>{
    User getOneByUserName(String username);
}
