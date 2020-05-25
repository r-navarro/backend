package com.rna.shutter

import com.rna.shutter.dto.ShutterDTO
import io.micronaut.http.HttpResponse
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ShutterDAOImpl(private val shutterClient: ShutterClient) : ShutterDAO {


    override fun open(id: Int): Single<HttpResponse<*>> {
        val shutterDTO = ShutterDTO(id, true)
        return shutterClient.call(shutterDTO)
    }

    override fun close(id: Int): Single<HttpResponse<*>> {
        val shutterDTO = ShutterDTO(id, false)
        return shutterClient.call(shutterDTO)
    }

}
