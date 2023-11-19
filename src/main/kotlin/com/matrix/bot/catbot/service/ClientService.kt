package com.matrix.bot.catbot.service

import com.matrix.bot.catbot.model.Client

interface ClientService {
    fun saveClient(client: Client)
    fun getClient(id: Long): Client
    fun existById(id: Long): Boolean
    fun getScheduledClients(): List<Client>
}