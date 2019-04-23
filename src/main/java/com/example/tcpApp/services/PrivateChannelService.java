package com.example.tcpApp.services;

import com.example.tcpApp.models.PrivateChannel;
import com.example.tcpApp.models.User;
import com.example.tcpApp.repositories.PrivateChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivateChannelService {

    @Autowired
    private PrivateChannelRepository privateChannelRepository;
    @Autowired
    private UserService userService;

    public PrivateChannel create(PrivateChannel channel){
        return privateChannelRepository.save(channel);
    }

    public PrivateChannel findByPrivateChannelName(String name){
        return privateChannelRepository.findByChannelName(name);
    }

    public PrivateChannel findById(Long id){
        return privateChannelRepository.getOne(id);
    }

    public Iterable<PrivateChannel> findAllByUser(User user) {
        List<PrivateChannel> privateChannels = new ArrayList<>();
        for (PrivateChannel c : findAll()) {
            if (userService.findAllByPrivateChannel(c, null).contains(user)) {
                privateChannels.add(c);
            }
        }
        return privateChannels;
    }

    public Iterable<PrivateChannel> findAll(){
        return privateChannelRepository.findAll();
    }

    public Boolean delete(Long id){
        privateChannelRepository.deleteById(id);
        return true;
    }
}
