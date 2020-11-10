FROM oracle/graalvm-ce:20.2.0-java11 as graalvm

COPY . /home/app/event
WORKDIR /home/app/event
RUN gu install native-image
RUN native-image --no-server --enable-https --enable-all-security-services -H:+JNI -cp build/libs/event-*-all.jar

FROM frolvlad/alpine-glibc

RUN apk add libstdc++

EXPOSE 8080
COPY --from=graalvm /home/app/event .
ENTRYPOINT ["./event"]
