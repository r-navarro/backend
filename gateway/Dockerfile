FROM oracle/graalvm-ce:20.2.0-java11 as graalvm

COPY . /home/app/gateway
WORKDIR /home/app/gateway
RUN gu install native-image
RUN native-image --no-server --enable-https --enable-all-security-services -H:+JNI -cp build/libs/gateway-*-all.jar

FROM frolvlad/alpine-glibc

RUN apk add libstdc++

EXPOSE 8080
COPY --from=graalvm /home/app/gateway .
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

#ENTRYPOINT ["./gateway"]
CMD /wait && ./gateway
