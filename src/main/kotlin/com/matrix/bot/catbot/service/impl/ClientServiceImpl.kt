package com.matrix.bot.catbot.service.impl

import com.matrix.bot.catbot.model.Client
import com.matrix.bot.catbot.repository.ClientRepository
import com.matrix.bot.catbot.service.ClientService
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(
    private val clientRepository: ClientRepository
) : ClientService {
    override fun saveClient(client: Client) {
        clientRepository.save(client)
    }

    override fun getClient(id: Long) = clientRepository.getReferenceById(id)

    override fun existById(id: Long) = clientRepository.existsById(id)

    override fun getClosestClients(): List<Client> = clientRepository.getClosestClients()
}