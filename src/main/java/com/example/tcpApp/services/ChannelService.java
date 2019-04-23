package com.example.tcpApp.services;

import com.example.tcpApp.models.Channel;
import com.example.tcpApp.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    private ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

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

    public Boolean delete(Long id){
        channelRepository.deleteById(id);
        return true;
    }
}
