package com.example.evaluacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @PostConstruct
    public void init(){
        if (clientRepository.findAll().isEmpty()){
            Client client = new Client();
            client.setDni("75489111");
            client.setName("Alfonso");
            client.setLastName("Ugarte");
            create(client);
            client = new Client();
            client.setDni("75489222");
            client.setName("Craig");
            client.setLastName("Castro");
            create(client);
            client = new Client();
            client.setDni("75489333");
            client.setName("Fernando");
            client.setLastName("Cruz");
            create(client);
            client = new Client();
            client.setDni("75489444");
            client.setName("Alexander");
            client.setLastName("Montez");
            create(client);
        }
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    public Client create(Client client){
        return clientRepository.save(client);
    }

    public Client update(Client client, Long id){
        Client clientNow = clientRepository.findById(id).orElse(null);
        clientNow.setDni(client.getDni());
        clientNow.setName(client.getName());
        clientNow.setLastName(client.getLastName());
        if (client.getState() != null) {
            clientNow.setState(client.getState());
        }
        return clientRepository.save(clientNow);
    }
}
