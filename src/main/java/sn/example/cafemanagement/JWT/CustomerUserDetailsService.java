package sn.example.cafemanagement.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sn.example.cafemanagement.dao.UserDao;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private sn.example.cafemanagement.entities.User userDetail;
    
    @Override
    public UserDetails loadUserByUsername(String usernme) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", usernme);
        userDetail = userDao.findByEmailId(usernme);
        if(!Objects.isNull(userDetail))
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not found.");
    }

    public sn.example.cafemanagement.entities.User getUserDetail(){
        return userDetail;
    }
}
