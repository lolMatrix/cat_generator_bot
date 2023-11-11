package com.matrix.bot.catbot.repository

import com.matrix.bot.catbot.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClientRepository : JpaRepository<Client, Long> {

    @Query(
        """
            SELECT * FROM client c
            WHERE c.next_invocation <= NOW()
            LIMIT 10
        """,
        nativeQuery = true
    )
    fun getClosestClients(): List<Client>
}