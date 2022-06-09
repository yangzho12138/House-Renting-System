package com.house.service;

import com.house.pojo.Renter;

public interface RenterService {

    Renter getRenterByRenterId(Integer renterId);

    void register(Renter renter);
}
