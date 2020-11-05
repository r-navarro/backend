package com.rna.shutter

import com.rna.shutter.dto.MessageDTO
import io.micronaut.http.HttpResponse
import io.reactivex.Single
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
open class ShutterService(private val shutterDao: ShutterDAO) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }


    fun open(id: Int): Single<MessageDTO> {
        log.info("call shutter api (open)")
        return callDAO(shutterDao.open(id))
    }

    fun close(id: Int): Single<MessageDTO> {
        log.info("call shutter api (close)")
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
