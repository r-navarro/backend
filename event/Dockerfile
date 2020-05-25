FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/event
WORKDIR /home/app/event
RUN gu install native-image
RUN native-image --no-server -cp build/libs/event-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/event .
ENTRYPOINT ["./event"]
