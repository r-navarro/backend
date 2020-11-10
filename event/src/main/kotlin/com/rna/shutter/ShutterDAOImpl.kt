package com.rna.shutter

import com.rna.shutter.dto.ShutterDTO
import io.micronaut.http.HttpResponse
import io.reactivex.Single
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ShutterDAOImpl(private val shutterClient: ShutterClient) : ShutterDAO {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun open(id: Int): Single<HttpResponse<*>> {
        val shutterDTO = ShutterDTO(id, true)
        log.debug("calling shutter api (open)")
        return shutterClient.call(shutterDTO)
    }

    override fun close(id: Int): Single<HttpResponse<*>> {
        val shutterDTO = ShutterDTO(id, false)
        log.debug("calling shutter api (close)")
        return shutterClient.call(shutterDTO)
    }

}
