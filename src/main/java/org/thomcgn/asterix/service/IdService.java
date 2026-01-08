package org.thomcgn.asterix.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdService {

    public String setUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
