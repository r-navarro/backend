package com.rna.shutter

import com.rna.shutter.dto.MessageDTO
import io.micronaut.http.HttpResponse
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ShutterService(private val shutterDao: ShutterDAO) {

    fun open(id: Int): Single<MessageDTO> {
        return callDAO(shutterDao.open(id))
    }

    fun close(id: Int): Single<MessageDTO> {
        return callDAO(shutterDao.close(id))

    }

    private fun callDAO(single: Single<HttpResponse<*>>): Single<MessageDTO> {
        return single.onErrorReturn { t ->
            HttpResponse.badRequest(t.message)
        }.map {
            MessageDTO(it.status.code, it.body().toString())
        }
    }
}
