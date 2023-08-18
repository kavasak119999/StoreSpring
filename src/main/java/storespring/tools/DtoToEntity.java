package storespring.tools;


import org.mindrot.jbcrypt.BCrypt;
import storespring.dto.Order;
import storespring.dto.User;
import storespring.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DtoToEntity {

    public static UserEntity userDtoToEntity(User user, RoleEntity roleEntity) {
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        List<RoleEntity> userRoles = new ArrayList<>();
        userRoles.add(roleEntity);

        return UserEntity.builder()
                .username(user.getUsername())
                .password(hash)
                .roles(userRoles)
                .build();
    }

    public static OrderEntity orderDtoToEntity(Order order){
        return OrderEntity.builder()
                .created(LocalDateTime.now())
                .status(order.getStatus())
                .build();
    }

}