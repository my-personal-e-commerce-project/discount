package microservice.cloud.discount.shared.domain.value_objects;

import java.util.Set;

import microservice.cloud.discount.shared.domain.exception.UnauthorizedException;

public record Me(
    Id id,
    Set<Permission> permissions
) {
    public void IHavePermission(Permission permission) {
        if(permission == null)
            throw new RuntimeException("Permission cannot be null");

        if(!permissions.contains(permission))
            throw new UnauthorizedException("Invalid permissions. The " + permission.value() + " is required.");
    }
}
