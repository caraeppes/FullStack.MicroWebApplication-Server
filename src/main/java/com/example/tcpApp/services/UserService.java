package com.example.tcpApp.services;

import com.example.tcpApp.models.Channel;
import com.example.tcpApp.models.PrivateChannel;
import com.example.tcpApp.models.User;
import com.example.tcpApp.repositories.ChannelRepository;
import com.example.tcpApp.repositories.PrivateChannelRepository;
import com.example.tcpApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PrivateChannelRepository privateChannelRepository;
    @Autowired
    private PrivateChannelService privateChannelService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User create(User user) throws Exception {
            if (userRepository.findByUsername(user.getUsername()) == null) {
                return userRepository.save(user);
            }
            throw new Exception("Username already exists!");
    }

    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User connect(Long id) {
        User original = userRepository.getOne(id);
        original.setConnected(true);
        return userRepository.save(original);
    }

    public User login(String username) {
        User original = userRepository.findByUsername(username);
        original.setConnected(true);
        return userRepository.save(original);
    }

    public User disconnect(Long id) {
        User original = userRepository.getOne(id);
        original.setConnected(false);
        return userRepository.save(original);
    }

    public User logout(String username) {
        User original = userRepository.findByUsername(username);
        original.setConnected(false);
        return userRepository.save(original);
    }

    public User joinChannel(Long userId, Long channelId) {
        User original = userRepository.getOne(userId);
        Channel channel = channelRepository.getOne(channelId);
        original.getChannels().add(channel);
        return userRepository.save(original);
    }

    public User joinPrivateChannel(Long userId, Long channelId) {
        User original = userRepository.getOne(userId);
        PrivateChannel privateChannel = privateChannelRepository.getOne(channelId);
        original.getPrivateChannels().add(privateChannel);
        return userRepository.save(original);
    }

    public User joinPrivateChannelByName(String username, String privateChannelName) {
        User original = userRepository.findByUsername(username);
        PrivateChannel privateChannel = privateChannelRepository.findByChannelName(privateChannelName);
        original.getPrivateChannels().add(privateChannel);
        return userRepository.save(original);
    }

    public User joinChannelByName(String username, String channelName) {
        User original = userRepository.findByUsername(username);
        Channel channel = channelRepository.findByChannelName(channelName);
        original.getChannels().add(channel);
        return userRepository.save(original);
    }

    public User leaveChannel(String username, String channelName) {
        User original = userRepository.findByUsername(username);
        Channel channel = channelRepository.findByChannelName(channelName);
        Channel channelToRemove = null;
        for (Channel c : original.getChannels()) {
            if (c.getId() == channel.getId()) {
                channelToRemove = c;
            }
        }
        original.getChannels().remove(channelToRemove);
        return userRepository.save(original);
    }

    public User leavePrivateChannel(String username, String privateChannelName) {
        User original = userRepository.findByUsername(username);
        PrivateChannel privateChannel = privateChannelRepository.findByChannelName(privateChannelName);
        PrivateChannel channelToRemove = null;
        for (PrivateChannel c : original.getPrivateChannels()) {
            if (c.getId() == privateChannel.getId()) {
                channelToRemove = c;
            }
        }
        original.getPrivateChannels().remove(channelToRemove);
        return userRepository.save(original);
    }

    public Boolean deleteAll() {
        userRepository.deleteAll();
        return true;
    }

    public List<User> findAllByChannel(Long id, Pageable pageable) {
        return userRepository.findAllByChannels(channelService.findById(id), pageable);
    }

    public List<User> findAllByPrivateChannel(PrivateChannel privateChannel, Pageable pageable) {
        return userRepository.findAllByPrivateChannels(privateChannel, pageable);
    }

    public List<User> findAllByPrivateChannel(Long id, Pageable pageable) {
        return userRepository.findAllByPrivateChannels(privateChannelService.findById(id), pageable);
    }
}
