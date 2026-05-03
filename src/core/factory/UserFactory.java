package core.factory;

import models.users.User;
import java.io.Serializable;
import java.util.Map;

public interface UserFactory extends Serializable {
    User createUser(Map<String, String> data);
}