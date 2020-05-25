FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/gateway
WORKDIR /home/app/gateway
RUN gu install native-image
RUN native-image --no-server -cp build/libs/gateway-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/gateway .
ENTRYPOINT ["./gateway"]
