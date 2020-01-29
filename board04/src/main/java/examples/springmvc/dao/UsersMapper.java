package examples.springmvc.dao;

import examples.springmvc.dto.User;
import examples.springmvc.mapper.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {
    public void addUser(User user);
    public User getUser(String userId);
    public List<User> getUsers();
}
