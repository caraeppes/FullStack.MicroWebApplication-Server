package com.example.tcpApp.services;

import com.example.tcpApp.models.Channel;
import com.example.tcpApp.models.User;
import com.example.tcpApp.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private UserService userService;

    public Channel create(Channel channel){
        return channelRepository.save(channel);
    }

    public Channel findByChannelName(String name){
        return channelRepository.findByChannelName(name);
    }

    public Channel findById(Long id){
        return channelRepository.getOne(id);
    }

    public Iterable<Channel> findAll(){
        return channelRepository.findAll();
    }

    public Iterable<Channel> findByIsPrivate(boolean isPrivate) {
        return channelRepository.findByIsPrivate(isPrivate);
    }

    public Iterable<Channel> findAllPMsOfAUser(User user) {
        List<Channel> privateChannels = new ArrayList<>();
        for (Channel c : findByIsPrivate(true)) {
            if (userService.findAllByChannel(c.getId(), null).contains(user)) {
                privateChannels.add(c);
            }
        }
        return privateChannels;
    }

    public Boolean delete(Long id){
        channelRepository.deleteById(id);
        return true;
    }

    public Channel makePrivate(Long id){
        Channel original = this.channelRepository.getOne(id);
        original.setPrivate(true);
        return channelRepository.save(original);
    }
}
