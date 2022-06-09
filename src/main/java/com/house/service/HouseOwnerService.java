package com.house.service;

import com.house.pojo.Owner;

public interface HouseOwnerService {
    void addOwner(Owner owner);

    void updateOwner(Owner owner);

    void deleteOwner(Integer ownerId);

    Owner getByOwnerId(Integer ownerId);
}
